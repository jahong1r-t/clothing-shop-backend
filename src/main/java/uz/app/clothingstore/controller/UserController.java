package uz.app.clothingstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.ApiResponse;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(ApiResponse.success("It's me", Map.of(
                "id", currentUser.getId(),
                "email", currentUser.getEmail(),
                "fistName", currentUser.getFirstName(),
                "lastName", currentUser.getLastName()
        )));
    }
}
