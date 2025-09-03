package uz.app.clothingstore.service;

import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import org.springframework.web.multipart.MultipartFile;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AttachmentReqDTO;

import java.util.List;
import java.util.UUID;

public interface AttachmentService {
    ApiResponse<?> upload(Long productId, Integer mainFileIndex, List<MultipartFile> attachments);

    ApiResponse<?> download(UUID fileId);

    ApiResponse<?> downloadMainImg(Long productId);

    ApiResponse<?>downloadProductAllPhotos(Long productId);
}
