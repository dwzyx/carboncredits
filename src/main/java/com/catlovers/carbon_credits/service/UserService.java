package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserDTO;

import java.util.List;

public interface UserService {
    JSONObject getUserInfo(int userId);

    JSONObject getRankingList(int userId, int cityId);

}
