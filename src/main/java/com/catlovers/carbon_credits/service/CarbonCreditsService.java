package com.catlovers.carbon_credits.service;


import com.alibaba.fastjson.JSONObject;

public interface CarbonCreditsService {

    /**
     * 获取用户碳积分信息， 并将今日步数添加至数据库
     * @param userId 用户id
     * @param mileageWalkToday 步行里程
     * @return
     */
    JSONObject getCreditsInfo(int userId, int mileageWalkToday);

    /**
     * 领取碳积分
     * @param userId 用户id
     * @return
     */
    JSONObject receiveCarbonCredits(int userId);
}
