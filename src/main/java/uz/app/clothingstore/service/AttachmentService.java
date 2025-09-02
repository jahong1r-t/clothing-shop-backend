package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.AttachmentReqDTO;

import java.util.UUID;

public interface AttachmentService {
    ApiResponse<?> upload(AttachmentReqDTO files);

    ApiResponse<?> download(UUID fileId);

    ApiResponse<?> downloadMainImg(Long productId);

    ApiResponse<?>downloadProductAllPhotos(Long productId);
}
