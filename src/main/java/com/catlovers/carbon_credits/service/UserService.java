package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserDTO;
import com.catlovers.carbon_credits.model.UserDelivery;

import java.util.List;

public interface UserService {
    JSONObject getUserInfo(int userId);

    JSONObject getRankingList(int userId, int cityId);

    JSONObject getMonthlyReport(int userId);

    JSONObject getTeamInfo(int teamId);

    JSONObject addUserToTeam(int teamId, int userId);

    JSONObject deleteUserFromTeam(int userId);

    JSONObject getUserCoupon(int userId, int pageNo, int pageSize);

    JSONObject getUserCommodityRecord(int userId, int pageNo, int pageSize);

    JSONObject getUserDelivery(int userId);

    JSONObject updateUserDelivery(UserDelivery userDelivery);

    JSONObject addUserDelivery(UserDelivery userDelivery);

}
