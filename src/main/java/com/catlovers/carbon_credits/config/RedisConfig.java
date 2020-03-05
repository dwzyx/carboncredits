package com.catlovers.carbon_credits.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.cache.*;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

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

//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
//        //初始化一个RedisCacheWriter
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory);
//        //使用fastjson的方式处理缓存
//        FastJson2JsonRedisSerializer<Object> serializer = new FastJson2JsonRedisSerializer<>(Object.class);
//
//        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(serializer);
//        RedisCacheConfiguration defaultCacheConfig=RedisCacheConfiguration.
//                defaultCacheConfig().
//                serializeValuesWith(pair).
//                entryTtl(Duration.ofSeconds(30));
//
//        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig);
//    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                this.getRedisCacheConfigurationWithTtl(30), // 默认策略，未配置的 key 会使用这个
                this.getRedisCacheConfigurationMap() // 指定 key 策略
        );
    }

        private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
             Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
             //emailVeri进行过期时间配置
             redisCacheConfigurationMap.put("emailVeri", this.getRedisCacheConfigurationWithTtl(5*60));
            redisCacheConfigurationMap.put("verification", this.getRedisCacheConfigurationWithTtl(15*60));
            redisCacheConfigurationMap.put("login", this.getRedisCacheConfigurationWithTtl(7*12*60*60));

            return redisCacheConfigurationMap;
     }

         private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
             Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
             ObjectMapper om = new ObjectMapper();
                om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                jackson2JsonRedisSerializer.setObjectMapper(om);

                RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
                redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                                    RedisSerializationContext
                                    .SerializationPair
                                            .fromSerializer(jackson2JsonRedisSerializer)
             ).entryTtl(Duration.ofSeconds(seconds));

             return redisCacheConfiguration;
     }



}
