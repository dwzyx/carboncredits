package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;


public interface MerchantService {
    String login(int id,String UUID);
    String loginAnyway(int id,String uuid);
    boolean ifExist(int id);
    int firstLogin(MerchantLoginDTO merchantLoginDTO);
    JSONObject signUp(MerchantDTO merchantDTO);
    int findMerchantIdByUserId(int userId);
}
