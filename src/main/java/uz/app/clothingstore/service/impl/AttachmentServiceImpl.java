package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.ProductImage;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AttachmentReqDTO;
import uz.app.clothingstore.payload.resp.ProductImgRespDTO;
import uz.app.clothingstore.repostory.ProductImageRepository;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.service.AttachmentService;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    @Value("${aws.s3.bucket-name}")
    private String bucket;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImagesRepository;

    @Override
    public ApiResponse<?> upload(Long productId, Integer mainFileIndex, List<MultipartFile> files) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        List<ProductImage> list = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            MultipartFile f = files.get(i);
            String fileId = UUID.randomUUID().toString();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileId)
                    .contentType(f.getContentType())
                    .build();

            try {
                s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(f.getInputStream(), f.getSize()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            boolean isMain = mainFileIndex != null && mainFileIndex == i;

            ProductImage image = ProductImage.builder()
                    .s3key(fileId)
                    .size(f.getSize())
                    .product(product)
                    .isMain(isMain)
                    .contentType(f.getContentType())
                    .originalFileName(f.getOriginalFilename())
                    .build();

            list.add(image);
        }

        productImagesRepository.saveAll(list);

        List<ProductImgRespDTO> resp = list.stream().map(i ->
                ProductImgRespDTO.builder()
                        .id(i.getId())
                        .url(generateDownloadUrl(i.getS3key()))
                        .isMain(i.getIsMain())
                        .build()).toList();


        return ApiResponse.success("Files uploaded successfully", resp);
    }

    @Override
    public ApiResponse<?> download(UUID imageId) {
        List<ProductImage> list =
                productImagesRepository.findAllByIdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(imageId);

        List<ProductImgRespDTO> dto = list.stream().map(i ->
                ProductImgRespDTO.builder()
                        .url(generateDownloadUrl(i.getS3key()))
                        .isMain(i.getIsMain())
                        .id(i.getId())
                        .build()).toList();

        return ApiResponse.success("Product images", dto);
    }

    @Override
    public ApiResponse<?> downloadMainImg(Long productId) {
        ProductImage image = productImagesRepository.findMainActiveProductImage(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        String url = generateDownloadUrl(image.getS3key());

        ProductImgRespDTO build = ProductImgRespDTO.builder()
                .id(image.getId())
                .url(url)
                .isMain(image.getIsMain())
                .build();

        return ApiResponse.success("Product image", build);
    }

    @Override
    public ApiResponse<?> downloadProductAllPhotos(Long productId) {
        Product product = productRepository.findActiveById(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        List<ProductImage> list = productImagesRepository.findAllProductImageByProductId(product.getId());
        List<ProductImgRespDTO> dto = list.stream().map(i ->
                ProductImgRespDTO.builder()
                        .url(generateDownloadUrl(i.getS3key()))
                        .isMain(i.getIsMain())
                        .id(i.getId())
                        .build()).toList();

        return ApiResponse.success("Product images", dto);
    }

    private String generateDownloadUrl(String s3Key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(s3Key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(getObjectRequest)
                .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }
}
