package uz.app.clothingstore.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import uz.app.clothingstore.entity.User;

@Component
public class CacheService {
    @CachePut(value = "user_sign_up_cache", key = "#user.email")
    public User setUserToCache(User user) {
        return user;
    }

    @Cacheable(value = "user_sign_up_cache", key = "#email")
    public User getUserFromCache(String email) {
        return null;
    }

    @CacheEvict(value = "user_sign_up_cache", key = "#email")
    public void removeUserFromCache(String email) {
    }

    @CachePut(value = "email_confirm_code", key = "#email")
    public String setEmailConfirmCodeToCache(String email, String code) {
        return code;
    }

    @Cacheable(value = "email_confirm_code", key = "#email")
    public String getConfirmCodeFromCache(String email) {
        return null;
    }

    @CacheEvict(value = "email_confirm_code", key = "#email")
    public void removeConfirmCodeFromCache(String email) {
    }

    @CachePut(value = "refresh_token", key = "#userId")
    public String setRefreshTokenToCache(Long userId, String refreshToken) {
        return refreshToken;
    }

    @Cacheable(value = "refresh_token", key = "#userId")
    public String getRefreshTokenFromCache(Long userId) {
        return null;
    }

    @CacheEvict(value = "refresh_token", key = "#userId")
    public void deleteRefreshToken(Long userId) {
    }

    public String hashToken(String token) {
        return DigestUtils.sha256Hex(token);
    }

    @CachePut(value = "blacklist_token", key = "#root.target.hashToken(#accessToken)")
    public Boolean addTokenToBlacklist(String accessToken) {
        return true;
    }

    @Cacheable(value = "blacklist_token", key = "#root.target.hashToken(#accessToken)")
    public boolean isBlacklisted(String accessToken) {
        return false;
    }

}
