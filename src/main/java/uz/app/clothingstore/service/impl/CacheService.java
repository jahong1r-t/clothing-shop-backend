package uz.app.clothingstore.service.impl;

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


}
