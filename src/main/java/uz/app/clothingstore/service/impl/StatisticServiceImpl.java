package uz.app.clothingstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.ProductStatistic;
import uz.app.clothingstore.entity.VariantStats;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.mapper.StatisticMapper;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.resp.StatisticRespDTO;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.ProductStatisticRepository;
import uz.app.clothingstore.repostory.ProductVariantStatsRepository;
import uz.app.clothingstore.service.StatisticsService;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticsService {
    private final StatisticMapper statisticMapper;
    private final ProductRepository productRepository;
    private final ProductStatisticRepository productStatisticRepository;
    private final ProductVariantStatsRepository productVariantStatsRepository;

    @Override
    public ApiResponse<?> getProductStatistic(Long productId) {
        ProductStatistic statistic = productStatisticRepository
                .findProductStatisticByProduct_IdAndProduct_IsActiveTrueAndProduct_IsDeletedFalse(productId)
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        StatisticRespDTO dto = statisticMapper.toDTO(statistic);
        dto.setQuantity(statistic.getProduct().getQuantity());

        return ApiResponse.success("Product statistics", dto);
    }

    @Override
    public ApiResponse<?> getAllProductStatistics(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<ProductStatistic> statisticsPage = productStatisticRepository
                .findAllByProduct_IsActiveTrueAndProduct_IsDeletedFalse(pageable);

        Page<StatisticRespDTO> dtoPage = statisticsPage.map(s -> {
            StatisticRespDTO dto = statisticMapper.toDTO(s);
            dto.setQuantity(s.getProduct().getQuantity());
            return dto;
        });


        return ApiResponse.success("All product statistics", dtoPage);
    }


    @Override
    public ApiResponse<?> getVariantStatistic(Long variantId) {
        VariantStats stats = productVariantStatsRepository
                .findByIdAndVariant_IsActiveTrueAndIsDeletedFalse(variantId)
                .orElseThrow(() -> new ItemNotFoundException("Variant not found"));

        StatisticRespDTO dto = statisticMapper.toDTO(stats);
        dto.setQuantity(stats.getVariant().getQuantity());

        return ApiResponse.success("Variant statistics", dto);
    }

}
