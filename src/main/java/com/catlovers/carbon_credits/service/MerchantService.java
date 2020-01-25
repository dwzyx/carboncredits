package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;


public interface MerchantService {
    JSONObject login(MerchantLoginDTO merchantLoginDTO);
    JSONObject signUp(MerchantDTO merchantDTO);
}
