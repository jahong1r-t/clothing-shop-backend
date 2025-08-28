package uz.app.clothingstore.service;

import org.springframework.web.multipart.MultipartFile;
import uz.app.clothingstore.payload.ApiResponse;

import java.io.IOException;

public interface AttachmentService {
    ApiResponse<?> upload(MultipartFile file) throws IOException;

    ApiResponse<?> download(String attachmentId);
}
