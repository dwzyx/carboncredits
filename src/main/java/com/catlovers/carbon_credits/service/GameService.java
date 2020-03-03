package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;

public interface GameService {
    JSONObject getHomePage();

    JSONObject gameEvent(int userId, int carbonCredits);
}
