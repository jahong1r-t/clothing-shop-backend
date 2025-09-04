package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.app.clothingstore.payload.req.PromotionReqDTO;

@RequestMapping("/api/v1/promotion")
public interface PromotionController {
    @PostMapping
    ResponseEntity<?> create(@RequestBody PromotionReqDTO dto);

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable Long id, @RequestBody PromotionReqDTO dto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable Long id);

    @GetMapping
    ResponseEntity<?> getAll();
}

