package uz.app.clothingstore.payload.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentReqDTO {
    private Long productId;
    private Integer mainFileIndex;

}
