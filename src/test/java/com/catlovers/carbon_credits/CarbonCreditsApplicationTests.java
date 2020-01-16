package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
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

}
