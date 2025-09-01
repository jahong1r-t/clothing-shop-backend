package uz.app.clothingstore.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import uz.app.clothingstore.entity.Cart;
import uz.app.clothingstore.entity.User;
import uz.app.clothingstore.entity.enums.Provider;
import uz.app.clothingstore.entity.enums.Role;
import uz.app.clothingstore.payload.ApiResponse;
import uz.app.clothingstore.repostory.CartRepository;
import uz.app.clothingstore.repostory.UserRepository;
import uz.app.clothingstore.service.impl.CacheService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CacheService cacheService;
    private final JwtService jwtService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String firstName = oAuth2User.getAttribute("given_name");
        String lastName = oAuth2User.getAttribute("family_name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User build = User.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .role(Role.USER)
                            .provider(Provider.GOOGLE)
                            .isVerified(true)
                            .build();

                    build.setDeleted(false);
                    build.setActive(true);
                    cartRepository.save(new Cart(build));
                    return userRepository.save(build);
                });

        String accessToken = jwtService.generateJwtAccessToken(user);
        String refreshToken = jwtService.generateJwtRefreshToken(user);
        cacheService.setRefreshTokenToCache(user.getId(), refreshToken);

        ApiResponse<Map<String, String>> resp = ApiResponse.success(
                "You have signed in successfully",
                Map.of("accessToken", accessToken,
                        "refreshToken", refreshToken));

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        new ObjectMapper().writeValue(response.getWriter(), resp);
    }
}

