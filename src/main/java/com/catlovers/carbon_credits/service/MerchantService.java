package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;


public interface MerchantService {
    String login(int id,String UUID);
    String loginAnyway(int id,String uuid);
    boolean ifExist(int id);
    int firstLogin(int userId,String merchantPassword);
    JSONObject signUp(MerchantVO merchantVO);
    int findMerchantIdByUserId(int userId);
    JSONObject getAll(int userId);
    JSONObject modify(MerchantDTO merchantDTO);
    JSONObject modifyPassword(int userId,String merchantPassword);
    String getName(int userId);
    //商家扫描二维码使用优惠券
    JSONObject useCoupon(int couponBagId, int couponId,int userId);
}
