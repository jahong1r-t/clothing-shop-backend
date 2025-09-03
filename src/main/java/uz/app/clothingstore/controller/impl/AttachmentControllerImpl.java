package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.app.clothingstore.controller.AttachmentController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.service.AttachmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AttachmentControllerImpl implements AttachmentController {
    private final AttachmentService attachmentService;

    @Override
    public ResponseEntity<?> upload(Long productId, Integer mainFileIndex, List<MultipartFile> files) {
        ApiResponse<?> response = attachmentService.upload(productId, mainFileIndex, files);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> download(UUID fileId) {
        ApiResponse<?> response = attachmentService.download(fileId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> downloadMainImg(Long productId) {
        ApiResponse<?> response = attachmentService.downloadMainImg(productId);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> downloadProductAllPhotos(Long productId) {
        ApiResponse<?> response = attachmentService.downloadProductAllPhotos(productId);
        return ResponseEntity.ok(response);
    }
}