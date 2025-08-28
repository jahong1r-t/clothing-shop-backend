package uz.app.clothingstore.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uz.app.clothingstore.controller.AuthController;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ConfirmEmailReqDTO;
import uz.app.clothingstore.payload.req.SignInReqDTO;
import uz.app.clothingstore.payload.req.SignUpReqDTO;
import uz.app.clothingstore.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public ResponseEntity<?> signUp(SignUpReqDTO signUpReqDTO) {
        ApiResponse<?> apiResponse = authService.signUp(signUpReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    public ResponseEntity<?> signIn(SignInReqDTO signInReqDTO) {
        return null;
    }

    @Override
    public ResponseEntity<?> logout() {
        return null;
    }

    @Override
    public ResponseEntity<?> confirmEmail(ConfirmEmailReqDTO confirmEmailReqDTO) {
        ApiResponse<?> apiResponse = authService.confirmEmail(confirmEmailReqDTO);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    public ResponseEntity<?> resendConfirmCode() {
        return null;
    }

    @Override
    public ResponseEntity<?> refreshAccessToken() {
        return null;
    }

    @Override
    public ResponseEntity<?> loginWithGoogle() {
        return null;
    }

    @Override
    public ResponseEntity<?> loginWithGitHub() {
        return null;
    }
}
