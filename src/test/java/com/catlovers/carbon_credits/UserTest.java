package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.client.UserClientDTO;
import com.catlovers.carbon_credits.util.ClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class UserTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    void test_getUserClientInfo(){
        JSONObject jsonObject = new JSONObject();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑


        //编辑请求头
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.add("userId", "1");

        //编辑请求体
        map.add("user_id", 1);
        map.add("term_type", 0);
        map.add("term_token", "123123");
        JSONObject respond = ClientUtil.getRespond(URLEnum.USER_INFO_URL.getUrl(), map, httpHeaders, restTemplate);
        JSONObject resultJson = respond.getJSONObject("result");
        UserClientDTO user = resultJson.getObject("user", UserClientDTO.class);
        System.out.println(user);
    }

}
