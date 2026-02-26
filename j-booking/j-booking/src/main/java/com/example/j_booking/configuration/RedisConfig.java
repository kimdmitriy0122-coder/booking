package com.example.j_booking.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisConfig {
    RedisProps props;

    @Bean
    RedisSerializer<Object> redisJsonSerializer() {
        return RedisSerializer.json();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        var configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(props.getHost());
        configuration.setPort(props.getPort());
        configuration.setPassword(props.getPassword());
        configuration.setDatabase(props.getDatabase());

        var lettuceClientConfiguration = LettuceClientConfiguration
            .builder()
            .commandTimeout(Duration.ofMillis(props.getTimeout()))
            .shutdownTimeout(Duration.ofMillis(props.getShutdownTimeout()))
            .build();

        return new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory, RedisSerializer<Object> redisJsonSerializer) {
        var defaultConfiguration = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMillis(props.getDefaultTtl()))
            .disableCachingNullValues()
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair
                    .fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair
                    .fromSerializer(redisJsonSerializer));

//        Map<String, RedisCacheConfiguration> perCacheTtl = Map.of(
//            ORDER_REDIS_KEYS, defaultConfiguration.entryTtl(Duration.ofMillis(props.getOrderTtl())),
//            MERCHANT_REDIS_KEYS, defaultConfiguration.entryTtl(Duration.ofMillis(props.getMerchantTtl()))
//        );

        return RedisCacheManager
            .builder(factory)
            .cacheDefaults(defaultConfiguration)
//            .withInitialCacheConfigurations(perCacheTtl)
            .transactionAware()
            .build();
    }
}
