package uz.app.clothingstore.payload.resp;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentRespDTO {
    private String s3Key;
    private String originalFileName;
    private String contentType;
    private Long size;
    private Boolean isMain;
    private String downloadUrl;
}
