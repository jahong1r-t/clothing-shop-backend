package uz.app.clothingstore.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.payload.req.ConfirmEmailReqDTO;
import uz.app.clothingstore.payload.req.ResendCodeReqDTO;
import uz.app.clothingstore.payload.req.SignInReqDTO;
import uz.app.clothingstore.payload.req.SignUpReqDTO;

@RequestMapping("/api/v1/auth")
public interface AuthController {
    @PostMapping("/sign-up")
    ResponseEntity<?> signUp(@RequestBody @Valid SignUpReqDTO signUpReqDTO);

    @PostMapping("/sign-in")
    ResponseEntity<?> signIn(@RequestBody @Valid SignInReqDTO signInReqDTO);

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> logout(@AuthenticationPrincipal User user, @RequestHeader("Authorization") String header);

    @PostMapping("/confirm-email")
    ResponseEntity<?> confirmEmail(@RequestBody @Valid ConfirmEmailReqDTO confirmEmailReqDTO);

    @PostMapping("/resend-confirm-code")
    ResponseEntity<?> resendConfirmCode(@RequestBody @Valid ResendCodeReqDTO resendCodeReqDTO);

    @PostMapping("/refresh-token")
    ResponseEntity<?> refreshAccessToken(@AuthenticationPrincipal User user, @RequestHeader("Authorization") String header);
}
