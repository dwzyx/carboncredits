package com.catlovers.carbon_credits.service;


import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
import org.springframework.stereotype.Service;

public interface CarbonCreditsService {
    JSONObject getCreditsInfo(int userId);
}
