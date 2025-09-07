package uz.app.clothingstore.payload.resp;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImgRespDTO {
    private UUID id;
    private String url;
    private boolean isMain;
}
