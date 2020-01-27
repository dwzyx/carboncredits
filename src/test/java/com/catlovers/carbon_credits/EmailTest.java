package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class EmailTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    @Test
    void ETest(){
        JSONObject jsonObject = emailService.emailVerification("1972576148@qq.com","唐瑄");
        System.out.println(jsonObject.toString());
    }
}
