package uz.app.clothingstore.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.exception.ConfirmCodeExpiredException;
import uz.app.clothingstore.exception.EmailAlreadyExistsException;
import uz.app.clothingstore.exception.InvalidConfirmCodeException;
import uz.app.clothingstore.exception.SignUpSessionExpiredException;
import uz.app.clothingstore.mapper.UserMapper;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.payload.req.ConfirmEmailReqDTO;
import uz.app.clothingstore.payload.req.ResendCodeReqDTO;
import uz.app.clothingstore.payload.req.SignInReqDTO;
import uz.app.clothingstore.payload.req.SignUpReqDTO;
import uz.app.clothingstore.repostory.UserRepository;
import uz.app.clothingstore.security.JwtService;
import uz.app.clothingstore.service.AuthService;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final CacheService cacheService;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public ApiResponse<?> signUp(SignUpReqDTO signUpReqDTO) {
        if (userRepository.existsByEmail(signUpReqDTO.getEmail())) {
            throw new EmailAlreadyExistsException("This email already exist");
        }

        User user = userMapper.toUser(signUpReqDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        cacheService.setUserToCache(user);
        String code = sendConfirmCodeToEmail(signUpReqDTO.getEmail());
        cacheService.setEmailConfirmCodeToCache(signUpReqDTO.getEmail(), code);

        return ApiResponse.success(
                "Verification code sent to your email",
                Map.of("email", signUpReqDTO.getEmail()));
    }

    @Override
    public ApiResponse<?> signIn(SignInReqDTO signInReqDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInReqDTO.getEmail(),
                        signInReqDTO.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = (User) userDetails;

        String accessToken = jwtService.generateJwtAccessToken(user);
        String refreshToken = jwtService.generateJwtRefreshToken(user);
        cacheService.setRefreshTokenToCache(user.getId(), refreshToken);

        return ApiResponse.success(
                "You have signed in successfully",
                Map.of("accessToken", accessToken,
                        "refreshToken", refreshToken));
    }

    @Override
    public ApiResponse<?> logout(Long userId, String header) {
        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtException("Invalid token");
        }

        cacheService.addTokenToBlacklist(header.substring(7));
        cacheService.deleteRefreshToken(userId);
        return ApiResponse.success("Logged out successfully", null);
    }

    @Override
    public ApiResponse<?> confirmEmail(ConfirmEmailReqDTO confirmEmailReqDTO) {
        User user = Optional.ofNullable(cacheService.getUserFromCache(confirmEmailReqDTO.getEmail()))
                .orElseThrow(() -> new SignUpSessionExpiredException("Sign-up session expired. Please sign up again."));

        String code = Optional.ofNullable(cacheService.getConfirmCodeFromCache(confirmEmailReqDTO.getEmail()))
                .orElseThrow(() -> new ConfirmCodeExpiredException("Confirmation code has expired. Please request a new one."));

        if (!code.equals(confirmEmailReqDTO.getCode())) {
            throw new InvalidConfirmCodeException("Invalid confirmation code");
        }

        user.setIsEnabled(true);
        user.setIsVerified(true);
        userRepository.save(user);

        cacheService.removeUserFromCache(user.getEmail());
        cacheService.removeConfirmCodeFromCache(user.getEmail());

        String accessToken = jwtService.generateJwtAccessToken(user);
        String refreshToken = jwtService.generateJwtRefreshToken(user);
        cacheService.setRefreshTokenToCache(user.getId(), refreshToken);

        return ApiResponse.success(
                "Email confirmed successfully",
                Map.of("accessToken", accessToken,
                        "refreshToken", refreshToken)
        );
    }

    @Override
    public ApiResponse<?> resendConfirmCode(ResendCodeReqDTO resendCodeReqDTO) {
        User user = Optional.ofNullable(cacheService.getUserFromCache(resendCodeReqDTO.getEmail()))
                .orElseThrow(() -> new SignUpSessionExpiredException("Sign-up session expired. Please try again."));

        String code = sendConfirmCodeToEmail(user.getEmail());
        cacheService.setEmailConfirmCodeToCache(user.getEmail(), code);

        return ApiResponse.success(
                "Verification code sent to your email",
                Map.of("email", user.getEmail()));
    }

    @Override
    public ApiResponse<?> refreshAccessToken(Long userId, String header) {
        String token = cacheService.getRefreshTokenFromCache(userId);

        if (header == null || !header.startsWith("Bearer ")) {
            throw new JwtException("Invalid token");
        }

        if (token == null || !token.equals(header.substring(7))) {
            return ApiResponse.error("Refresh token is invalid or expired");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        String jwtAccessToken = jwtService.generateJwtAccessToken(user);

        return ApiResponse.success(
                "New access token generated",
                Map.of("accessToken", jwtAccessToken));
    }

    private String sendConfirmCodeToEmail(String email) {
        String confirmCode = generateConfirmCode();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("jahongirtorayev1507@gmail.com");
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Shop.co – Verify Your Email");
        simpleMailMessage.setText("""
                Hello,
                Thank you for joining Shop.co 👗🛍️
                Your verification code:
                
                🔑 %s
                
                This code is valid for 2 minutes.
                – Shop.co Team
                """.formatted(confirmCode));

        javaMailSender.send(simpleMailMessage);

        return confirmCode;
    }

    private String generateConfirmCode() {
        Random random = new Random();
        int code = random.nextInt(1_000_000);
        return String.format("%06d", code);
    }
}