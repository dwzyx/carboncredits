package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.service.CommodityService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommodityController {

    private String answer = null;
    private Gson gson = new Gson();
    private JSONObject jsonObject = new JSONObject();

    final
    CommodityService commodityService;


    public CommodityController(CommodityService commodityService) {
        this.commodityService = commodityService;
        jsonObject.put("status_code", null);
        jsonObject.put("status_msg", null);
        jsonObject.put("result", null);
    }

    @GetMapping(value = "/commodity/getCommodityList", produces = "application/json;charset=UTF-8")
    public String getCommodityInfo(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize){
        List<CommodityDTO> commodityDTO = commodityService.getCommodityList(pageNo, pageSize);

        jsonObject.replace("status_code", StatusEnum.SUCCESS.getCoding());
        jsonObject.replace("status_msg", StatusEnum.SUCCESS.getMessage());
        jsonObject.replace("result", commodityDTO);

        return jsonObject.toString();
    }


}
