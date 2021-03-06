package cn.devifish.cloud.common.redis.config;

import cn.devifish.cloud.common.redis.constant.RedisConstant;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * RedisConfiguration
 * Redis配置
 *
 * @author Devifish
 * @date 2020/6/30 15:58
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisConnectionFactory redisConnectionFactory;
    private final ObjectMapper objectMapper;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        log.info("Initializing Redis Template");

        var redisTemplate = new RedisTemplate<String, Object>();
        var jsonRedisSerializer = jsonRedisSerializer();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer() {
        var objectMapper = this.objectMapper.copy();
        var validator = objectMapper.getPolymorphicTypeValidator();
        objectMapper.activateDefaultTyping(validator, DefaultTyping.NON_FINAL, As.PROPERTY);
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    /**
     * 注册 Redis分布式锁 到 Spring Context
     *
     * @return RedisLockRegistry
     */
    @Bean
    public RedisLockRegistry redisLockRegistry() {
        log.info("Initializing Redis Lock");

        return new RedisLockRegistry(redisConnectionFactory, RedisConstant.REDIS_LOCK_KEY_PREFIX);
    }

}
