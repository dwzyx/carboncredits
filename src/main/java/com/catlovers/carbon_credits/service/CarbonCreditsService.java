package com.catlovers.carbon_credits.service;


import com.alibaba.fastjson.JSONObject;

public interface CarbonCreditsService {
    JSONObject getCreditsInfo(int userId);

}
