package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserDTO;
import com.catlovers.carbon_credits.service.UserService;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //获取用户信息
    @GetMapping(value = "/user/getUserInfo", produces = "application/json;charset=UTF-8")
    public String getUserInfo(@RequestParam("user_id") int userId){
        JSONObject jsonObject = userService.getUserInfo(userId);
        return jsonObject.toString();
    }

    //获取排行榜信息
    @GetMapping(value = "/user/getUserRankingList", produces = "application/json;charset=UTF-8")
    public String getRankingList(@RequestParam("user_id") int userId, @RequestParam("city_id") int cityId){
        JSONObject jsonObject = userService.getRankingList(userId, cityId);
        return jsonObject.toString();
    }

    //获取月度报告
    @GetMapping(value = "/user/getMonthlyReport", produces = "application/json;charset=UTF-8")
    public String getMonthlyReport(@RequestParam("user_id") int userId, @RequestParam("city_id") int cityId){
        JSONObject jsonObject = userService.getMonthlyReport(userId);
        return jsonObject.toString();
    }

    @GetMapping(value = "/user/getTeamInfo", produces = "application/json;charset=UTF-8")
    public String getTeamInfo(@RequestParam("team_id") int teamId){
        JSONObject jsonObject = userService.getTeamInfo(teamId);
        return  jsonObject.toString();
    }

    @GetMapping(value = "/user/addUser", produces = "application/json;charset=UTF-8")
    public String addUserToTeam(@RequestParam("team_id")int teamId, @RequestParam("user_id")int userId){
        JSONObject jsonObject = userService.addUserToTeam(teamId, userId);
        return jsonObject.toString();
    }

    @GetMapping(value = "/user/deleteUserFromTeam", produces = "application/json;charset=UTF-8")
    public String deleteUserFromTeam(@RequestParam("user_id")int userId){
        JSONObject jsonObject = userService.deleteUserFromTeam(userId);
        return jsonObject.toString();
    }

    @GetMapping(value = "/user/getUserCoupon", produces = "application/json;charset=UTF-8")
    public String getUserCoupon(@RequestParam("user_id")int userId, @RequestParam("page_no") int pageNo,
                                @RequestParam("page_size")int pageSize){
        JSONObject jsonObject = userService.getUserCoupon(userId, pageNo, pageSize);
        return jsonObject.toString();
    }
}
