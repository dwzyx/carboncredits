package com.catlovers.carbon_credits.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

public interface EmailService {
    JSONObject emailVerification(String userEmail, String userName);
    JSONObject updateEmailVerification(String userEmail, String userName);

}
