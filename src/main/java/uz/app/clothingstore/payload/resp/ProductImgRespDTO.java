package uz.app.clothingstore.payload.resp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ProductImgRespDTO {
    private UUID id;
    private String url;
    private boolean isMain;
}
