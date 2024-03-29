package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.service.CarbonCreditsService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class CarbonCreditsController {

    private String answer = null;
    private Gson gson = new Gson();
    private JSONObject jsonObject = null;
    public static Logger logger = Logger.getLogger(String.valueOf(UserController.class));

    private final
    CarbonCreditsService carbonCreditsService;

    public CarbonCreditsController(CarbonCreditsService carbonCreditsService) {
        this.carbonCreditsService = carbonCreditsService;
    }


    @GetMapping(value = "/carbonCredits/getCreditsInfo", produces = "application/json;charset=UTF-8")
    public String getCreditsInfo(@RequestParam("user_id") int userId){

       JSONObject jsonObject = carbonCreditsService.getCreditsInfo(userId);
        return jsonObject.toString();

    }





}
