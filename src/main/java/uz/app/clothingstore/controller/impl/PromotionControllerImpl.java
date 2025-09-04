package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.PromotionController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.PromotionReqDTO;
import uz.app.clothingstore.service.PromotionService;

@RestController
@RequiredArgsConstructor
public class PromotionControllerImpl implements PromotionController {
    private final PromotionService promotionService;

    @Override
    public ResponseEntity<?> create(PromotionReqDTO dto) {
        ApiResponse<?> response = promotionService.create(dto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> update(Long id, PromotionReqDTO dto) {
        ApiResponse<?> response = promotionService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        ApiResponse<?> response = promotionService.delete(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        ApiResponse<?> response = promotionService.getById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getAll() {
        ApiResponse<?> response = promotionService.getAll();
        return ResponseEntity.ok(response);
    }
}
