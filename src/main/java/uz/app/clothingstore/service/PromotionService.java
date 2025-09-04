package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.PromotionReqDTO;

public interface PromotionService {
    ApiResponse<?> create(PromotionReqDTO dto);

    ApiResponse<?> update(Long id, PromotionReqDTO dto);

    ApiResponse<?> delete(Long id);

    ApiResponse<?> getById(Long id);

    ApiResponse<?> getAll();
}

