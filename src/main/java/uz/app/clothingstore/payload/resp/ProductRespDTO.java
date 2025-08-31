package uz.app.clothingstore.payload.resp;

import uz.app.clothingstore.payload.req.ProductVariantReqDTO;

import java.util.List;

public class ProductRespDTO {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Boolean isExistVariant;
    private Long categoryId;
    private List<ProductVariantReqDTO> variants;
}
