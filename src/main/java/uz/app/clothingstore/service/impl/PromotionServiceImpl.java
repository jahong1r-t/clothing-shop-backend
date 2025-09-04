package uz.app.clothingstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.Product;
import uz.app.clothingstore.entity.Promotion;
import uz.app.clothingstore.exception.ItemNotFoundException;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.PromotionReqDTO;
import uz.app.clothingstore.payload.resp.PromotionRespDTO;
import uz.app.clothingstore.repostory.ProductRepository;
import uz.app.clothingstore.repostory.PromotionRepository;
import uz.app.clothingstore.service.PromotionService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final ProductRepository productRepository;
    private final PromotionRepository promotionRepository;

    @Override
    @Transactional
    public ApiResponse<?> create(PromotionReqDTO dto) {
        Product product = productRepository.findActiveById(dto.getProductId())
                .orElseThrow(() -> new ItemNotFoundException("Product not found"));

        Double oldPrice = product.getPrice();
        Double newPrice = dto.getNewPrice();

        double discountPercent = ((oldPrice - newPrice) / oldPrice) * 100;

        Promotion promotion = Promotion.builder()
                .product(product)
                .oldPrice(oldPrice)
                .active(true)
                .startDate(LocalDate.now())
                .endDate(dto.getEndDate())
                .discountPercent(discountPercent)
                .build();

        product.setIsExistPromotion(true);

        productRepository.save(product);
        promotionRepository.save(promotion);

        return ApiResponse.success("Promotion created successfully", promotion);
    }


    @Override
    @Transactional
    public ApiResponse<?> update(Long id, PromotionReqDTO dto) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Promotion not found"));

        Product product = promotion.getProduct();
        Double oldPrice = product.getPrice();
        Double newPrice = dto.getNewPrice() != null ? dto.getNewPrice() : oldPrice;

        double discountPercent = ((oldPrice - newPrice) / oldPrice) * 100;

        promotion.setEndDate(dto.getEndDate() != null ? dto.getEndDate() : promotion.getEndDate());
        promotion.setDiscountPercent(discountPercent);

        promotionRepository.save(promotion);

        return ApiResponse.success("Promotion updated successfully", promotion);
    }

    @Override
    @Transactional
    public ApiResponse<?> delete(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Promotion not found"));

        Product product = promotion.getProduct();
        product.setIsExistPromotion(false);

        productRepository.save(product);
        promotionRepository.delete(promotion);

        return ApiResponse.success("Promotion deleted successfully", null);
    }


    @Override
    public ApiResponse<?> getById(Long id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Promotion not found"));

        PromotionRespDTO dto = PromotionRespDTO.builder()
                .productId(promotion.getProduct().getId())
                .newPrice(promotion.getNewPrice())
                .discountPercent(promotion.getDiscountPercent())
                .active(promotion.isActive())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .build();

        return ApiResponse.success("Promotion fetched successfully", dto);
    }


    @Override
    public ApiResponse<?> getAll() {
        List<PromotionRespDTO> list = promotionRepository.findAll().stream()
                .map(p ->
                        PromotionRespDTO.builder()
                                .startDate(p.getStartDate())
                                .endDate(p.getEndDate())
                                .newPrice(p.getNewPrice())
                                .discountPercent(p.getDiscountPercent())
                                .productId(p.getProduct().getId())
                                .active(p.isActive())
                                .build()
                ).toList();

        return ApiResponse.success("Promotion list fetched successfully", list);
    }

}
