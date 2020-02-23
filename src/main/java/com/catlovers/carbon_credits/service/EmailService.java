package com.catlovers.carbon_credits.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

public interface EmailService {
    String emailVerification(String merchantEmail,String merchantName,String context);
    void deleteEmailVerification(String merchantName);

}
