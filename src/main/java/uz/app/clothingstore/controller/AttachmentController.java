package uz.app.clothingstore.controller;

import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.app.clothingstore.payload.req.AttachmentReqDTO;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/attachments")
@MultipartConfig
public interface AttachmentController {

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> upload(
            @RequestParam Long productId,
            @RequestParam Integer mainFileIndex,
            @RequestPart("files") List<MultipartFile> files);

    @GetMapping("/download/{fileId}")
    ResponseEntity<?> download(@PathVariable UUID fileId);

    @GetMapping("/download/main/{productId}")
    ResponseEntity<?> downloadMainImg(@PathVariable Long productId);

    @GetMapping("/download/all/{productId}")
    ResponseEntity<?> downloadProductAllPhotos(@PathVariable Long productId);
}

