package uz.app.clothingstore.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisConfig {
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

        Map<String, RedisCacheConfiguration> configurations = new HashMap<>();
        configurations.put("user_sign_up_cache", defaultConfig.entryTtl(Duration.ofMinutes(15)));
        configurations.put("email_confirm_code", defaultConfig.entryTtl(Duration.ofMinutes(2)));
        configurations.put("refresh_token", defaultConfig.entryTtl(Duration.ofDays(7)));
        configurations.put("blacklist_token", defaultConfig.entryTtl(Duration.ofMinutes(15)));
        configurations.put("top_sold_products", defaultConfig.entryTtl(Duration.ofDays(1)));
        configurations.put("new_products", defaultConfig.entryTtl(Duration.ofDays(1)));
        configurations.put("user", defaultConfig.entryTtl(Duration.ofHours(1)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(configurations)
                .build();
    }
}