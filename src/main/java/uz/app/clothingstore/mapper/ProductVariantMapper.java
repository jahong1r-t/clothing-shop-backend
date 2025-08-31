package uz.app.clothingstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.app.clothingstore.entity.ProductVariant;
import uz.app.clothingstore.payload.req.NewVariantReqDTO;
import uz.app.clothingstore.payload.resp.ProductVariantRespDTO;

@Mapper(componentModel = "spring")
public interface ProductVariantMapper {
    @Mapping(source = "product.id", target = "productId")
    ProductVariantRespDTO toDTO(ProductVariant productVariant);

    ProductVariant toEntity(NewVariantReqDTO dto);
}
