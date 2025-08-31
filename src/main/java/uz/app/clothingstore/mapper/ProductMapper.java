package uz.app.clothingstore.mapper;

import org.mapstruct.Mapper;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.payload.req.ProductReqDTO;
import uz.app.clothingstore.payload.resp.ProductRespDTO;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductReqDTO dto);

    ProductRespDTO toRespDTO(Product product);
}
