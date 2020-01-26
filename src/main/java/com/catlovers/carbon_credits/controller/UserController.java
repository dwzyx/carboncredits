package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserDTO;
import com.catlovers.carbon_credits.service.UserService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private Gson gson = new Gson();

    private final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/user/getUserInfo", produces = "application/json;charset=UTF-8")
    public String getUserInfo(@RequestParam("user_id") int userId){

        JSONObject jsonObject = userService.getUserInfo(userId);


        return jsonObject.toString();



    }

    @GetMapping(value = "/user/getUserRankingList", produces = "application/json;charset=UTF-8")
    public String getRankingList(@RequestParam("user_id") int userId, @RequestParam("city_id") int cityId){

        JSONObject jsonObject = userService.getRankingList(userId, cityId);

        return jsonObject.toString();

    }

}
