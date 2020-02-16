package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.model.CouponInfoDTO;
import com.catlovers.carbon_credits.model.SecondHandGoodDTO;

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

    JSONObject exchangeCoupon(int couponId, int userId);

    JSONObject exchangeCommodity(int commodityId,int userId,int deliveryId);

    JSONObject getSecondHandGood(int pageNo, int pageSize);

    JSONObject addSecondHandGood(SecondHandGoodDTO secondHandGoodDTO);

    JSONObject deleteSecondHandGood(int goodId,int sellerId);

    JSONObject buySecondHandGood(int buyerId,int goodId,int sellerId,int deliveryId);
}
