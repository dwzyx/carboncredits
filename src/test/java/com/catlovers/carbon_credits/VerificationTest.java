package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.service.CarbonCreditsService;
import com.catlovers.carbon_credits.service.CodeService;
import com.catlovers.carbon_credits.service.VerificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class VerificationTest {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private CodeService codeService;

    @Test
    void Vtest(){
        int id = 1;
        JSONObject jsonObject = codeService.getCode(id);
        String string = jsonObject.getString("verification");
        System.out.println(string);
        JSONObject jsonObject1 = verificationService.getImage(id,string);
        System.out.println(jsonObject1.toString());
    }


}
