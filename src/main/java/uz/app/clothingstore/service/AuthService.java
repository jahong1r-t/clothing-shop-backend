package uz.app.clothingstore.service;

import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ConfirmEmailReqDTO;
import uz.app.clothingstore.payload.req.ResendCodeReqDTO;
import uz.app.clothingstore.payload.req.SignInReqDTO;
import uz.app.clothingstore.payload.req.SignUpReqDTO;

public interface AuthService {
    ApiResponse<?> signUp(SignUpReqDTO signUpReqDTO);

    ApiResponse<?> signIn(SignInReqDTO signInReqDTO);

    ApiResponse<?> logout(Long userId, String header);

    ApiResponse<?> confirmEmail(ConfirmEmailReqDTO confirmEmailReqDTO);

    ApiResponse<?> resendConfirmCode(ResendCodeReqDTO resendCodeReqDTO);

    ApiResponse<?> refreshAccessToken(Long userId, String header);
}
