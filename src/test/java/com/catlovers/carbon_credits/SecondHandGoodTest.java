package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.SecondHandGoodDTO;
import com.catlovers.carbon_credits.model.UserDelivery;
import com.catlovers.carbon_credits.service.CommodityService;
import com.catlovers.carbon_credits.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class SecondHandGoodTest {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UserService userService;

    @Test
    void test1(){
        JSONObject jsonObject ;
//        SecondHandGoodDTO secondHandGoodDTO = new SecondHandGoodDTO(0,0,2,"疯狂钻石",11,5,"长沙","修复",0);
//        jsonObject = commodityService.addSecondHandGood(secondHandGoodDTO);
//        System.out.println(jsonObject.get("msg_message"));
//        jsonObject = commodityService.buySecondHandGood(1,1,2,1);
//        System.out.println(jsonObject.get("msg_message"));
        UserDelivery userDelivery = new UserDelivery(1,1,"jojo","11111111111","杜王町");
        jsonObject = userService.updateUserDelivery(userDelivery);
        System.out.println(jsonObject.get("msg_message"));
    }
}
