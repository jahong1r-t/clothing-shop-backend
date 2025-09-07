package uz.app.clothingstore.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
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

        try {

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
        } catch (ExpiredJwtException ex) {
            handleException(response, HttpStatus.UNAUTHORIZED, "JWT token has expired");
        } catch (SignatureException ex) {
            handleException(response, HttpStatus.UNAUTHORIZED, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            handleException(response, HttpStatus.BAD_REQUEST, "Invalid JWT token format");
        } catch (JwtException ex) {
            handleException(response, HttpStatus.UNAUTHORIZED, "JWT error: " + ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private void handleException(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"success\":false,\"message\":\"" + message + "\"}");
    }
}
