package com.carboncredits.carbon_integration_system;

import com.carboncredits.carbon_integration_system.enumeration.ErrorEnum;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class CarbonIntegrationSystemApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate;//操作k-v都是字符串的




    @Test
    void test_01(){

    }

    @Test
    void contextLoads() {
        ErrorEnum errorEnum = ErrorEnum.APPID_IS_NULL;

    }

}
