package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.UserDelivery;

public interface UserService {
    JSONObject getUserInfo(int userId);

    JSONObject getMonthlyReport(int userId, int cityId, String startMonth, String endMonth);

    JSONObject getTeamInfo(int teamId);

    JSONObject addUserToTeam(int teamId, int userId);

    JSONObject deleteUserFromTeam(int userId);

    JSONObject getUserCoupon(int userId, int pageNo, int pageSize);

    JSONObject getUserDelivery(int userId);

    JSONObject updateUserDelivery(UserDelivery userDelivery);

    JSONObject addUserDelivery(UserDelivery userDelivery);

    /**
     * 签到功能
     * @param userId
     * @return
     */
    JSONObject signIn(int userId);

    /**
     * 赠送功能
     * @param userId 用户id
     * @param granteeId 受赠者id
     * @param carbonCredits 赠送碳积分的数量
     * @return
     */
    JSONObject giveAway(int userId, int granteeId, int carbonCredits);

    JSONObject getMonthRankingList(int userId, int cityId);

    JSONObject getTotalRankingList(int userId, int cityId);
}
