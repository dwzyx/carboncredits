package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
import com.catlovers.carbon_credits.service.CarbonCreditsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class CarbonCreditsController {

    private String answer = null;
    private Gson gson = new Gson();
    private JSONObject jsonObject = new JSONObject();

    private final
    CarbonCreditsService carbonCreditsService;

    public CarbonCreditsController(CarbonCreditsService carbonCreditsService) {
        this.carbonCreditsService = carbonCreditsService;
        jsonObject.put("status_code", null);
        jsonObject.put("status_msg", null);
        jsonObject.put("result", null);
    }


    @GetMapping(value = "/carbonCredits/getCreditsInfo", produces = "application/json;charset=UTF-8")
    public String getCreditsInfo(@RequestParam("user_id") int userId){
        CarBonCreditsDTO carBonCreditsDTO = carbonCreditsService.getCreditsInfo(userId);

        jsonObject.replace("status_code", StatusEnum.SUCCESS.getCoding());
        jsonObject.replace("status_msg", StatusEnum.SUCCESS.getMessage());
        jsonObject.replace("result", carBonCreditsDTO);

        return jsonObject.toString();
    }





}
