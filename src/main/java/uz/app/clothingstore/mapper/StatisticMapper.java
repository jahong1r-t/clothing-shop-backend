package uz.app.clothingstore.mapper;


import org.mapstruct.Mapper;
import uz.app.clothingstore.entity.ProductStatistic;
import uz.app.clothingstore.entity.VariantStats;
import uz.app.clothingstore.payload.resp.StatisticRespDTO;

@Mapper(componentModel = "spring")
public interface StatisticMapper {
    StatisticRespDTO toDTO(ProductStatistic productStatistic);

    StatisticRespDTO toDTO(VariantStats variantStats);
}
