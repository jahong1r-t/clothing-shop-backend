package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AttachmentReqDTO {
    private Long productId;
    private List<MultipartFile> files;
    private Integer mainFileIndex;

}
