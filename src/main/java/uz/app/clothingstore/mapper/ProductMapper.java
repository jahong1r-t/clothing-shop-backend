package uz.app.clothingstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.resp.ProductRespDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "isExistPromotion", constant = "false")
    Product toEntity(ProductReqDTO dto);

    ProductRespDTO toRespDTO(Product product);
}
