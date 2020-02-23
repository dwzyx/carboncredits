package com.catlovers.carbon_credits.service;


import com.alibaba.fastjson.JSONObject;

public interface CarbonCreditsService {

    /**
     * 获取用户碳积分信息， 并将今日步数添加至数据库
     * @param userId
     * @param mileageWalkToday
     * @return
     */
    JSONObject getCreditsInfo(int userId, int mileageWalkToday);

}
