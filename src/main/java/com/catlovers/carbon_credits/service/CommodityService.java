package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.model.CouponInfoDTO;

public interface CommodityService {

    JSONObject getCommodityInfo(int pageNo, int pageSize);

    JSONObject getCouponInfo(int pageNo, int pageSize, int goodType);

    JSONObject getCouponInfoById(int pageNo, int pageSize, int goodType, int merchantId);

    JSONObject searchCommodity(JSONObject commodity, int pageNo, int pageSize);

    JSONObject searchCoupon(JSONObject coupon, int pageNo, int pageSize, int goodType);

    JSONObject addCommodity(CommodityDTO commodity);

    JSONObject addCoupon(CouponInfoDTO coupon);

    JSONObject deleteCommodity(int commodityId);

    JSONObject deleteCoupon(int couponId);

    JSONObject updateCommodity(CommodityDTO commodity);

    JSONObject updateCoupon(CouponInfoDTO coupon);
}
