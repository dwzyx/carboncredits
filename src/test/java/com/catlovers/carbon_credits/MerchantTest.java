package com.catlovers.carbon_credits;

import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.service.MerchantService;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class MerchantTest {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MerchantService merchantService;

    @Test
    void test(){
        MerchantDTO merchantDTO = new MerchantDTO("a","19977771111","la","zyx","alla","19977771111",2);
        JSONObject jsonObject= merchantService.modify(merchantDTO);
        System.out.println(jsonObject.get("modifyResult"));
    }
}
