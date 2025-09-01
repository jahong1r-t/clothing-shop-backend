package uz.app.clothingstore.payload.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductImgRespDTO {
    private Long id;
    private String url;
    private boolean isMain;
}
