package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.GoodTypeEnum;
import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.model.CouponInfoDTO;
import com.catlovers.carbon_credits.model.SecondHandGoodDTO;
import com.catlovers.carbon_credits.service.CommodityService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommodityController {

    private final CommodityService commodityService;


    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping(value = "/good/getGoods", produces = "application/json;charset=UTF-8")
    public String getCommodityInfo(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize,
                                   @RequestParam("good_type") int goodTypes){
        JSONObject jsonObject;
        //
        if(goodTypes == GoodTypeEnum.COMMODITY){
            jsonObject = commodityService.getCommodityInfo(pageNo, pageSize);}
        else{
            jsonObject = commodityService.getCouponInfo(pageNo, pageSize, goodTypes);}
        return jsonObject.toString();
    }

    @PostMapping(value = "/good/searchGood", produces = "application/json;charset=UTF-8")
    public String searchGood(@RequestBody String request){
        JSONObject jsonObject = JSONObject.parseObject(request);
        int pageNo = (int) jsonObject.get("page_no");
        int pageSize = (int) jsonObject.get("page_size");
        int goodType = (int) jsonObject.get("good_type");

        if (goodType==GoodTypeEnum.COMMODITY){
            JSONObject commodity = jsonObject.getJSONObject("commodity");
            jsonObject = commodityService.searchCommodity(commodity, pageNo, pageSize);
        } else {
            JSONObject coupon = jsonObject.getJSONObject("coupon");
            jsonObject = commodityService.searchCoupon(coupon, pageNo, pageSize, goodType);
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/good/addGood", produces = "application/json;charset=UTF-8")
    public String addGood(@RequestBody String request){
        JSONObject jsonObject = JSONObject.parseObject(request);
        int goodType = (int) jsonObject.get("good_type");

        if (goodType==GoodTypeEnum.COMMODITY){
            CommodityDTO commodity = jsonObject.getObject("commodity", CommodityDTO.class);
            jsonObject = commodityService.addCommodity(commodity);
        } else {
            CouponInfoDTO coupon = jsonObject.getObject("coupon", CouponInfoDTO.class);
            jsonObject = commodityService.addCoupon(coupon);
        }

        return jsonObject.toString();
    }

    @GetMapping(value = "/good/deleteGood", produces = "application/json;charset=UTF-8")
    public String deleteGood(@RequestParam("commodity_id") int commodityId, @RequestParam("coupon_id") int couponId,
                             @RequestParam("good_type") int goodType){
        JSONObject jsonObject;

        if(goodType==GoodTypeEnum.COMMODITY){
            jsonObject = commodityService.deleteCommodity(commodityId);
        } else {
            jsonObject = commodityService.deleteCoupon(couponId);
        }

        return jsonObject.toString();
    }

    @PostMapping(value = "/good/updateGood", produces = "application/json;charset=UTF-8")
    public String updateGood(@RequestBody String request){

        JSONObject jsonObject = JSONObject.parseObject(request);
        int goodType = (int) jsonObject.get("good_type");

        if (goodType==GoodTypeEnum.COMMODITY){
            CommodityDTO commodity = jsonObject.getObject("commodity", CommodityDTO.class);
            jsonObject = commodityService.updateCommodity(commodity);
        } else {
            CouponInfoDTO coupon = jsonObject.getObject("coupon", CouponInfoDTO.class);
            jsonObject = commodityService.updateCoupon(coupon);
        }

        return jsonObject.toString();

    }


    //兑换券或商品
    @GetMapping(value = "/good/exchangeGood", produces = "application/json;charset=UTF-8")
    public String exchangeGood(@RequestParam("userId") int userId,@RequestParam("goodType")int goodType, @RequestParam("commodityId") int commodityId, @RequestParam("couponId")int couponId, @RequestParam("deliveryId")int deliveryId){
        JSONObject jsonObject;
        if(goodType==GoodTypeEnum.COMMODITY){
            jsonObject = commodityService.exchangeCommodity(commodityId,userId,deliveryId);
        }
        else {
            jsonObject = commodityService.exchangeCoupon(couponId,userId);
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/good/getSecondHandGoods", produces = "application/json;charset=UTF-8")
    public String getSecondHandGoods(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize){
        JSONObject jsonObject;
        jsonObject = commodityService.getSecondHandGood(pageNo,pageSize);
        return jsonObject.toString();
    }

    @PostMapping(value = "/good/getSecondHandGoodsFrom", produces = "application/json;charset=UTF-8")
    public String getSecondHandGoodsFrom(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize, @RequestParam("sellerId")int sellerId, @RequestParam("buyerId")int buyerId, @RequestParam("userType")int userType){
        JSONObject jsonObject;
        if(userType==1){
            jsonObject = commodityService.getSecondHandGoodFromSeller(pageNo,pageSize,sellerId);
        }
        else {
            jsonObject = commodityService.getSecondHandGoodFromBuyer(pageNo,pageSize,buyerId);
        }

        return jsonObject.toString();
    }

    @PostMapping(value = "/good/deleteSecondHandGood", produces = "application/json;charset=UTF-8")
    public String deleteSecondHandGood(@RequestParam("goodId") int goodId ){
        JSONObject jsonObject;
        jsonObject = commodityService.deleteSecondHandGood(goodId);
        return jsonObject.toString();
    }


    @PostMapping(value = "/good/addSecondHandGood", produces = "application/json;charset=UTF-8")
    public String addSecondHandGood(@RequestBody SecondHandGoodDTO secondHandGoodDTO){
        JSONObject jsonObject;
        jsonObject = commodityService.addSecondHandGood(secondHandGoodDTO);
        return jsonObject.toString();
    }

    @PostMapping(value = "/good/buySecondHandGood", produces = "application/json;charset=UTF-8")
    public String buySecondHandGood(@RequestParam("buyerId")int buyerId,@RequestParam("goodId")int goodId,@RequestParam("sellerId")int sellerId,@RequestParam("deliveryId")int deliveryId){
        JSONObject jsonObject;
        jsonObject = commodityService.buySecondHandGood(buyerId,goodId,sellerId,deliveryId);
        return jsonObject.toString();
    }



}