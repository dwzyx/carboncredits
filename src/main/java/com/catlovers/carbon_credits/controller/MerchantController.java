package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.CouponInfoDTO;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;
import com.catlovers.carbon_credits.service.*;
import com.catlovers.carbon_credits.util.JwtUtil;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
public class MerchantController {

    private final MerchantService merchantService;
    private final VerificationService verificationService;
    private final CodeService codeService;
    private final EmailService emailService;
    private final CommodityService commodityService;

    public MerchantController(MerchantService merchantService,VerificationService verificationService,CodeService codeService,EmailService emailService,CommodityService commodityService) {
        this.merchantService = merchantService;
        this.verificationService = verificationService;
        this.codeService = codeService;
        this.emailService = emailService;
        this.commodityService = commodityService;
    }

    @PostMapping(value = "/Merchant/signUp", produces = "application/json;charset=UTF-8")
    public String merchantSignUp(@RequestBody MerchantVO merchantVO, @RequestParam("imageCode") String imageCode, @RequestParam("emailCode") String emailCode){
        JSONObject jsonObject = new JSONObject();
        boolean imageResult = false;
        boolean emailResult = false;
        int userId = merchantVO.getUserId();
        String merchantName = merchantVO.getMerchantName();
        String email = merchantVO.getMerchantEmail();
        if(imageCode.equals(codeService.getCode(userId))){
            imageResult = true;
        }
        if(emailService.emailVerification(email,merchantName,null).equals(emailCode)){
            emailResult = true;
        }
        if(imageResult&&emailResult){
            jsonObject = merchantService.signUp(merchantVO);
        }
        else {
            jsonObject.put("MerchantSignUpResult","1111");
        }
        jsonObject.put("imageResult",imageResult);
        jsonObject.put("emailResult",emailResult);

        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/emailCode", produces = "application/json;charset=UTF-8")
    public String getEmailCode(@RequestParam("merchantEmail") String merchantEmail,@RequestParam("merchantName") String merchantName){
        //        System.out.println("email:"+merchantEmail);
        //        System.out.println("name:"+merchantName);
        String code;
        JSONObject jsonObject = new JSONObject();
        emailService.deleteEmailVerification(merchantName);
        code = emailService.emailVerification(merchantEmail,merchantName,"*的注册验证,15分钟有效。");
        if(code!=null){
            //            System.out.println(code);
            jsonObject.put("emailCode", "true");
        }
        else{
            jsonObject.put("emailCode", "false");
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/login", produces = "application/json;charset=UTF-8")
    public String merchantLogin(@RequestBody MerchantLoginDTO merchantLoginDTO, @RequestParam("rememberMe") boolean rememberMe,@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize, @RequestParam("good_type") int goodTypes) {
        JSONObject jsonObject = new JSONObject();
        JwtUtil jwtUtil = new JwtUtil();
        int userId = merchantLoginDTO.getUserId();
        int i = merchantService.firstLogin(merchantLoginDTO.getUserId(),merchantLoginDTO.getMerchantPassword());
        if(i == 1) {
            //                System.out.println(1);
            if(codeService.getCode(merchantLoginDTO.getUserId()).equals(merchantLoginDTO.getImageCode())){
                jsonObject.put("imageResult","true");
                int merchantId = merchantService.findMerchantIdByUserId(userId);
                jsonObject = commodityService.getCouponInfoById(pageNo,pageSize,goodTypes,merchantId);
                jsonObject.put("merchantResult","自动登录成功");
                if(rememberMe) {
                    UUID uuid = UUID.randomUUID();
                    String token = jwtUtil.createJwt(merchantLoginDTO.getUserId(), uuid);
                    //                        System.out.println("create token");
                    jsonObject.put("token", token);
                    jsonObject.put("merchantResult","已记住登陆");
                    //还要将token放到redis里面储存
                    //                        System.out.println("uuid:" + uuid.toString());
                    String s = uuid.toString();
                    String uu = merchantService.login(merchantLoginDTO.getUserId(), s);
                    //                        System.out.println("uu:" + uu);
                    //如果已经有登陆信息，更新登录信息
                    if(s != uu) {
                        System.out.println(merchantService.loginAnyway(merchantLoginDTO.getUserId(), s));
                    }
                }
                else{
                    jsonObject.put("merchantResult","已本次登陆");
                }
            }
            else {
                jsonObject.put("merchantResult","登陆失败，验证码错误");
                jsonObject.put("imageResult","false");
            }
        }
        else{
            jsonObject.put("merchantResult","登陆失败，密码错误");
        }
        return jsonObject.toString();

    }


    @PostMapping(value = "/Merchant/homeFalse",produces = "application/json;charset=UTF-8")
    public String homeFalse(@RequestParam("userId") int userId){
        JSONObject jsonObject = new JSONObject();
        if(merchantService.ifExist(userId)){
            jsonObject.put("merchantResult","已注册");
        }
        else {
            jsonObject.put("merchantResult","未注册");
        }
        String code = codeService.getCode(userId);
        System.out.println("code:"+code);
        String image = verificationService.getImage(userId,code);
        System.out.println("image:"+image);
        jsonObject.put("image",image);
        return jsonObject.toString();
    }


    @PostMapping(value = "/Merchant/home", produces = "application/json;charset=UTF-8")
    public String getCommodityInfo(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize, @RequestParam("good_type") int goodTypes,@RequestParam("userId" )int userId){
        int merchantId = merchantService.findMerchantIdByUserId(userId);
        JSONObject jsonObject;
        jsonObject = commodityService.getCouponInfoById(pageNo,pageSize,goodTypes,merchantId);
        jsonObject.put("merchantResult","自动登录成功");
        return jsonObject.toString();

    }

    @PostMapping(value = "/Merchant/getInfo", produces = "application/json;charset=UTF-8")
    public String getInfo(@RequestParam("userId") int userId){
        JSONObject jsonObject;
        jsonObject = merchantService.getAll(userId);
        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/modify",produces = "application/json;charset=UTF-8")
    public String modify(@RequestBody MerchantDTO merchantDTO){
        JSONObject jsonObject;
        jsonObject = merchantService.modify(merchantDTO);
        return jsonObject.toString();
    }


    @PostMapping(value = "/Merchant/modifyPassword",produces = "application/json;charset=UTF-8")
    public String modifyPassword(@RequestParam("userId") int userId ,@RequestParam("oldPassword") String oldPassword,@RequestParam("merchantPassword")String merchantPassword){
        JSONObject jsonObject = new JSONObject();
        int i = merchantService.firstLogin(userId,oldPassword);
        if(i==1){

            jsonObject = merchantService.modifyPassword(userId,merchantPassword);
            jsonObject.put("verifyResult", "true");
        }
        else {
            jsonObject.put("modifyResult", "false");
            jsonObject.put("verifyResult", "false");
        }

        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/emailForPassword",produces = "application/json;charset=UTF-8")
    public String emailForPassword(@RequestParam("userId") int userId ,@RequestParam("email") String email){
        JSONObject jsonObject = new JSONObject();
        String name = merchantService.getName(userId);
        String code = emailService.emailVerification(email,name,"*的密码修改验证,15分钟有效。");
        if(code!=null){
            //            System.out.println(code);
            jsonObject.put("emailCode", "true");
        }
        else{
            jsonObject.put("emailCode", "false");
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/modifyPasswordByEmail",produces = "application/json;charset=UTF-8")
    public String modifyPasswordByEmail(@RequestParam("userId") int userId ,@RequestParam("email") String email,@RequestParam("code") String code,@RequestParam("merchantPassword")String merchantPassword){
        JSONObject jsonObject = new JSONObject();
        String name = merchantService.getName(userId);
        if(emailService.emailVerification(email,name,null).equals(code)){
            jsonObject.put("emailCode", "true");
            jsonObject = merchantService.modifyPassword(userId,merchantPassword);
        }
        else {
            jsonObject.put("modifyResult","false");
            jsonObject.put("emailCode", "false");
        }
        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/addCoupon",produces = "application/json;charset=UTF-8")
    public String addCoupon(@RequestBody CouponInfoDTO couponInfoDTO){
        JSONObject jsonObject;
        jsonObject = commodityService.addCoupon(couponInfoDTO);
        return jsonObject.toString();
    }

    @PostMapping(value = "/Merchant/useCoupon",produces = "application/json;charset=UTF-8")
    public String useCoupon(@RequestParam("couponBagId") int couponBagId,@RequestParam("couponId") int couponId ,@RequestParam("userId") int userId){
        JSONObject jsonObject;
        jsonObject = merchantService.useCoupon(couponBagId,couponId,userId);
        return jsonObject.toString();
    }

}