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

    private String answer = null;
    private Gson gson = new Gson();
    private JSONObject jsonObject = new JSONObject();

    private final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        jsonObject.put("status_code", null);
        jsonObject.put("status_msg", null);
        jsonObject.put("result", null);
    }


    @GetMapping(value = "/user/getUserInfo", produces = "application/json;charset=UTF-8")
    public String getUserInfo(@RequestParam("user_id") int userId){

        UserDTO userInfo = userService.getUserInfo(userId);

        jsonObject.replace("status_code", StatusEnum.SUCCESS.getCoding());
        jsonObject.replace("status_msg", StatusEnum.SUCCESS.getMessage());
        jsonObject.replace("result", userInfo);


        return jsonObject.toString();



    }

    @GetMapping(value = "/user/getUserRankingList", produces = "application/json;charset=UTF-8")
    public String getRankingList(@RequestParam("user_id") int userId, @RequestParam("city_id") int cityId){

        List<RankingDTO> userInfo = userService.getRankingList(userId, cityId);

        jsonObject.replace("status_code", StatusEnum.SUCCESS.getCoding());
        jsonObject.replace("status_msg", StatusEnum.SUCCESS.getMessage());
        jsonObject.replace("result", userInfo);


        return jsonObject.toString();

    }

}
