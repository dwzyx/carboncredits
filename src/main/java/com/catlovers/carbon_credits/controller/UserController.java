package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.SecondHandGoodDTO;
import com.catlovers.carbon_credits.model.UserDTO;
import com.catlovers.carbon_credits.model.UserDelivery;
import com.catlovers.carbon_credits.service.CommodityService;
import com.catlovers.carbon_credits.service.UserService;
import com.google.gson.Gson;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController

public class UserController {

    private final UserService userService;
    private final CommodityService commodityService;

    public UserController(UserService userService,CommodityService commodityService) {
        this.userService = userService;
        this.commodityService = commodityService;
    }

    /**
     * 获取用户信息
     * @param userId 用户id
     * @return
     */
    @GetMapping(value = "/user/getUserInfo", produces = "application/json;charset=UTF-8")
    public String getUserInfo(@RequestParam("user_id") int userId){
        JSONObject jsonObject = userService.getUserInfo(userId);
        return jsonObject.toString();
    }

    /**
     * 获取排行榜信息
     * @param userId
     * @param cityId
     * @return
     */
    @GetMapping(value = "/user/getUserRankingList", produces = "application/json;charset=UTF-8")
    public String getRankingList(@RequestParam("user_id") int userId, @RequestParam("city_id") int cityId,
                                 @RequestParam("rank_type") int rankType){
        JSONObject jsonObject = null;
        if (rankType == 0) {
            jsonObject = userService.getMonthRankingList(userId, cityId);
        } else {
            jsonObject = userService.getTotalRankingList(userId, cityId);
        }
        return jsonObject.toString();
    }

    //获取月度报告
    @GetMapping(value = "/user/getMonthlyReport", produces = "application/json;charset=UTF-8")
    public String getMonthlyReport(@RequestParam("user_id") int userId, @RequestParam("city_id") int cityId,
                                   @RequestParam("start_month") String startMonth, @RequestParam("end_month") String endMonth){
        JSONObject jsonObject = userService.getMonthlyReport(userId, cityId, startMonth, endMonth);
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

    @GetMapping(value = "/user/getUserCommodityRecord", produces = "application/json;charset=UTF-8")
    public String getUserCommodityRecord(@RequestParam("user_id")int userId, @RequestParam("page_no") int pageNo,
                                         @RequestParam("page_size")int pageSize){
        JSONObject jsonObject = userService.getUserCommodityRecord(userId, pageNo, pageSize);
        return jsonObject.toString();
    }


    @PostMapping(value = "/user/getUserDelivery", produces = "application/json;charset=UTF-8")
    public String getUserDelivery(@RequestParam("userId")int userId){
        JSONObject jsonObject;
        jsonObject = userService.getUserDelivery(userId);
        return jsonObject.toString();
    }

    @PostMapping(value = "/user/addUserDelivery", produces = "application/json;charset=UTF-8")
    public String addUserDelivery(@RequestBody UserDelivery userDelivery){
        JSONObject jsonObject;
        jsonObject = userService.addUserDelivery(userDelivery);
        return jsonObject.toString();
    }

    @PostMapping(value = "/user/updateUserDelivery", produces = "application/json;charset=UTF-8")
    public String updateUserDelivery(@RequestBody UserDelivery userDelivery){
        JSONObject jsonObject;
        jsonObject = userService.updateUserDelivery(userDelivery);
        return jsonObject.toString();
    }

    @GetMapping(value = "/user/signIn", produces = "application/json;charset=UTF-8")
    public String signIn(@RequestParam("user_id")int userId){
        JSONObject jsonObject = userService.signIn(userId);
        return jsonObject.toString();
    }

    @GetMapping(value = "/user/giveAway", produces = "application/json;charset=UTF-8")
    public String giveAway(@Param("user_id")int userId, @Param("grantee_id")int granteeId, @Param("carbon_credits")int carbonCredits){
        JSONObject jsonObject = userService.giveAway(userId, granteeId, carbonCredits);
        return jsonObject.toString();
    }


}
