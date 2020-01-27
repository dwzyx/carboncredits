package com.catlovers.carbon_credits.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ClientUtil {

    public static JSONObject getRespond(String url, MultiValueMap<String, Object> map, HttpHeaders httpHeaders, RestTemplate restTemplate){
        //发送请求
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(url,
                HttpMethod.POST, new HttpEntity<>(map, httpHeaders), JSONObject.class);
        return exchange.getBody();
    }

}
