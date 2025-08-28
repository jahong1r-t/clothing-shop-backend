package uz.app.clothingstore.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.app.clothingstore.payload.req.ConfirmEmailReqDTO;
import uz.app.clothingstore.payload.req.SignInReqDTO;
import uz.app.clothingstore.payload.req.SignUpReqDTO;

@RequestMapping("/api/v1/auth")
public interface AuthController {
    @PostMapping("/sign-up")
    ResponseEntity<?> signUp(@RequestBody @Valid SignUpReqDTO signUpReqDTO);

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody @Valid SignInReqDTO signInReqDTO);

    @PostMapping("/logout")
    ResponseEntity<?> logout();

    @PostMapping("/confirm-email")
    ResponseEntity<?> confirmEmail(@RequestBody @Valid ConfirmEmailReqDTO confirmEmailReqDTO);

    @PostMapping("/resend-confirm-code")
    ResponseEntity<?> resendConfirmCode();

    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshAccessToken();

    @PostMapping("/login/google")
    ResponseEntity<?> loginWithGoogle();

    @PostMapping("/login/github")
    ResponseEntity<?> loginWithGitHub();

}
