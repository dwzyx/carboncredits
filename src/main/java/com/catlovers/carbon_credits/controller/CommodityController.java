package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.GoodTypeEnum;
import com.catlovers.carbon_credits.service.CommodityService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommodityController {

    final
    CommodityService commodityService;


    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    @GetMapping(value = "/good/getGoods", produces = "application/json;charset=UTF-8")
    public String getCommodityInfo(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize,
                                   @RequestParam("good_type") int goodTypes){
        JSONObject jsonObject;
        if(goodTypes == GoodTypeEnum.COMMODITY)
            jsonObject = commodityService.getCommodityInfo(pageNo, pageSize);
        else
            jsonObject = commodityService.getCouponInfo(pageNo, pageSize, goodTypes);
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

}
