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
        String s= emailService.emailVerification("1455127695@qq.com","鸭鸭","*lalallatest");
        System.out.println(s);
    }

//    @Test
//    void ETest2(){
//        String
//    }
}
