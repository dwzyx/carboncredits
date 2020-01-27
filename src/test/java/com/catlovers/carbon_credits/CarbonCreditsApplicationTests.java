package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.service.MerchantService;
import com.catlovers.carbon_credits.service.VerificationService;
import com.catlovers.carbon_credits.service.impl.MerchantServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

@SpringBootTest
class CarbonCreditsApplicationTests {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MerchantService merchantService;

    @Autowired
    private VerificationService verificationService;

    @Test
    void test_01(){

        String url = "http://59.110.174.204:7280/v1.0/api/app/user/getUserInfo";
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("user_id", 1);
        map.add("term_type", 0);
        map.add("term_token", "1234");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8");
        httpHeaders.add("userId", "1");
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(map, httpHeaders);
        System.out.println(Objects.requireNonNull(request.getBody()).get("user_id"));
        System.out.println(request.getHeaders());
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url, HttpMethod.POST, request, JSONObject.class);
        System.out.println(Objects.requireNonNull(exchange.getBody()).getString("user"));


    }

    @Test
    void contextLoads() {
    }

    @Test
    void test_02(){
        MerchantDTO merchantDTO = new MerchantDTO(0,1,"123456zyx","11111111111","738667591@qq.com","zyx","某某大街","贩卖啥啥啥","asdfgasfds.jpg");

            JSONObject jsonObject = merchantService.signUp(merchantDTO);
            System.out.println(jsonObject.toString());

            System.out.println("null");

    }

    @Test
    void test_03(){
        JSONObject num = verificationService.emailVerification("1972576148@qq.com","tang");
        System.out.println(num.toString());
    }

}
