package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;

public interface CommodityService {

    JSONObject getCommodityInfo(int pageNo, int pageSize);

    JSONObject getCouponInfo(int pageNo, int pageSize, int goodType);

    JSONObject searchCommodity(JSONObject commodity, int pageNo, int pageSize);

    JSONObject searchCoupon(JSONObject coupon, int pageNo, int pageSize, int goodType);
}
