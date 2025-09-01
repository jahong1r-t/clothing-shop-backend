package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.app.clothingstore.payload.req.FilterReqDTO;

@RequestMapping("/api/v1/search/")
public interface SearchController {

    @GetMapping("/products")
    ResponseEntity<?> searchProductsByName(@RequestParam(value = "q", required = false) String query,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size
    );

    @GetMapping("/products/filter")
    ResponseEntity<?> filterProducts(@RequestBody FilterReqDTO filterReqDTO,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size);

    @GetMapping("/products/top-sold")
    ResponseEntity<?> getTopSoldProducts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size
    );

    @GetMapping("/products/new-arrivals")
    ResponseEntity<?> getNewArrivals(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size
    );
}
