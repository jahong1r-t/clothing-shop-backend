package uz.app.clothingstore.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.app.clothingstore.service.impl.CacheService;

import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CacheService cacheService;
    private final UserDetailsService userDetailsService;

    @Lazy
    @Autowired
    public JwtFilter(JwtService jwtService, CacheService cacheService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.cacheService = cacheService;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        log.info("Authorization: {} URL: {}, Method: {}",
                authorization != null ? authorization.substring(0, 6) + "..." : "No-Auth-Header",
                request.getRequestURI(), request.getMethod());

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorization.substring(7);
        String username = jwtService.extractUserFromJwtToken(token);

        if (cacheService.isBlacklisted(token)) {
            throw new JwtException("Token has been invalidated (logout)");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.debug("{}:  JWT token is valid", username);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
