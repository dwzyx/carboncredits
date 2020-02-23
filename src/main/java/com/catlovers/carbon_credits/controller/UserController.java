package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.SecondHandGoodDTO;
import com.catlovers.carbon_credits.model.UserDTO;
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

    //兑换券或商品
    @GetMapping(value = "/user/exchangeGood", produces = "application/json;charset=UTF-8")
    public String exchangeGood(@RequestParam("userId") int userId, @RequestParam("commodityId") int commodityId, @RequestParam("couponId")int couponId, @RequestParam("deliveryId")int deliveryId){
        JSONObject jsonObject;
        if(commodityId==-1){
            jsonObject = commodityService.exchangeCoupon(couponId,userId);
        }
        else {
            jsonObject = commodityService.exchangeCommodity(commodityId,userId,deliveryId);
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "user/addSecondHandGood", produces = "application/json;charset=UTF-8")
    public String addSecondHandGood(@RequestBody SecondHandGoodDTO secondHandGoodDTO){
        JSONObject jsonObject = commodityService.addSecondHandGood(secondHandGoodDTO);
        return jsonObject.toString();
    }

    @PostMapping(value = "user/buySecondHandGood", produces = "application/json;charset=UTF-8")
    public String buySecondHandGood(@RequestParam("buyerId")int buyerId,@RequestParam("goodId")int goodId,@RequestParam("sellerId")int seller_id,@RequestParam("deliveryId")int deliveryId){
        JSONObject jsonObject = commodityService.buySecondHandGood(buyerId,goodId,seller_id,deliveryId);
        return jsonObject.toString();
    }

    @PostMapping(value = "user/getUserDelivery", produces = "application/json;charset=UTF-8")
    public String getUserDelivery(@RequestParam("userId")int userId){
        JSONObject jsonObject = userService.getUserDelivery(userId);
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
