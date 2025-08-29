package uz.app.clothingstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import uz.app.clothingstore.entity.User;

import java.util.Optional;

@Configuration
public class AuditConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
                return Optional.of("SYSTEM");
            }

            Object principal = auth.getPrincipal();
            if (principal instanceof User user) {
                return Optional.of(user.getId().toString());
            }
            if (principal instanceof UserDetails userDetails) {
                return Optional.of(userDetails.getUsername());
            }

            return Optional.of("SYSTEM");
        };
    }


}
