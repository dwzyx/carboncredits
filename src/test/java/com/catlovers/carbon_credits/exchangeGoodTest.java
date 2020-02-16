package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.model.UserDelivery;
import com.catlovers.carbon_credits.service.CommodityService;
import com.catlovers.carbon_credits.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class exchangeGoodTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CommodityService commodityService;

    @Autowired
    private UserService userService;

    @Test
    void test1(){
        JSONObject jsonObject;

        jsonObject = userService.getUserDelivery(1);
        String s = (String) jsonObject.get("msg_code");
        if(s.equals("0000")){
            HashMap<String, Object> resultMap = (HashMap<String, Object>) jsonObject.get("result");

            List<UserDelivery> list = (List<UserDelivery>) resultMap.get("userDelivery");

            for( UserDelivery userDelivery: list){
                System.out.println("user:");
                System.out.println(userDelivery.getDeliveryId());
                System.out.println(userDelivery.getUserId());
                System.out.println(userDelivery.getUserName());
                System.out.println(userDelivery.getUserPhoneNumber());
                System.out.println(userDelivery.getUserAddress());
            }
        }
        else {
            UserDelivery userDelivery = new UserDelivery(0,1,"zyx","11111111111","杜王町");
            userService.addUserDelivery(userDelivery);
            System.out.println("addDelivery");
        }

        jsonObject = commodityService.exchangeCommodity(1,1,1);
        System.out.println(jsonObject.get("msg_message"));

        jsonObject = commodityService.exchangeCoupon(1,2);
        System.out.println(jsonObject.get("msg_message"));

//        jsonObject = commodityService.exchangeCommodity(6,1);
//        System.out.println(jsonObject.get("exchangeResult"));
    }

}
