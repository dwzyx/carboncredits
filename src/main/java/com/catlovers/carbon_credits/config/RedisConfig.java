package com.catlovers.carbon_credits.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * reids配置类
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**使用jackson
    @Bean
    public RedisTemplate<Object, User> userRedisTemplate(
        RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<User> ser = new Jackson2JsonRedisSerializer<>(User.class);
        template.setDefaultSerializer(ser);
        return template;
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //使用jackson的方式处理缓存
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);

        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.
                defaultCacheConfig().
                serializeValuesWith(pair).
                entryTtl(Duration.ofSeconds(30));

        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }
    */

    /**
     * 使用fastjson
     */
//    @Bean
//    public RedisTemplate<Object, User> redisTemplate(RedisConnectionFactory connectionFactory) {
//
//        RedisTemplate<Object, User> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        FastJson2JsonRedisSerializer<User> ser = new FastJson2JsonRedisSerializer<>(User.class);
//        template.setDefaultSerializer(ser);
//
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        //初始化一个RedisCacheWriter
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
        //使用fastjson的方式处理缓存
        FastJson2JsonRedisSerializer<Object> serializer = new FastJson2JsonRedisSerializer<>(Object.class);

        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.
                defaultCacheConfig().
                serializeValuesWith(pair).
                entryTtl(Duration.ofSeconds(30));

        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
    }

}
