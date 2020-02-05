package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.enumeration.GoodTypeEnum;
import com.catlovers.carbon_credits.model.CodeDTO;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.service.*;
import com.catlovers.carbon_credits.util.JwtUtil;

import jdk.nashorn.internal.parser.Token;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
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

    @GetMapping(value = "/Merchant/signUp", produces = "application/json;charset=UTF-8")
    public String merchantSignUp(@RequestBody MerchantDTO merchantDTO, @RequestBody CodeDTO codeDTO){
        JSONObject jsonObject = new JSONObject();
        if(jsonObject==null){
            System.out.println("json null");
        }
        int userId = merchantDTO.getUserId();
        String merchantName = merchantDTO.getMerchantName();
        String imageCode = codeDTO.getImageCode();
        String emailCode = codeDTO.getEmailCode();
        String email = merchantDTO.getMerchantEmail();
        if(!imageCode.equals(codeService.getCode(userId))){
            jsonObject.put("imageResult","false");
        }
        if(!emailService.emailVerification(email,merchantName).equals(emailCode)){
            jsonObject.put("emailResult","false");
        }
        if(jsonObject==null){
            jsonObject = merchantService.signUp(merchantDTO);
        }
        return jsonObject.toString();
    }

    @GetMapping(value = "/Merchant/emailCode", produces = "application/json;charset=UTF-8")
    public String getEmailCode(String merchantEmail,String merchantName){
        System.out.println("email:"+merchantEmail);
        System.out.println("name:"+merchantName);
        JSONObject jsonObject = new JSONObject();
        emailService.deleteEmailVerification(merchantName);
        String code = emailService.emailVerification(merchantEmail,merchantName);
        if(code!=null){
            System.out.println(code);
            jsonObject.put("emailCode","true");
        }
        else{
            jsonObject.put("emailCode","false");
        }
        return jsonObject.toString();
    }

    @GetMapping(value = "/Merchant/login", produces = "application/json;charset=UTF-8")
    public String merchantLogin(@RequestBody MerchantLoginDTO merchantLoginDTO, @RequestParam("rememberMe") boolean rememberMe) {
        JSONObject jsonObject = new JSONObject();
        JwtUtil jwtUtil = new JwtUtil();
        String password = new Md5Hash(merchantLoginDTO.getMerchantPassword(), String.valueOf(merchantLoginDTO.getUserId()), 3).toString();
        merchantLoginDTO.setMerchantPassword(password);
        int i = merchantService.firstLogin(merchantLoginDTO);
            if(i == 1) {
                System.out.println(1);
                if(codeService.getCode(merchantLoginDTO.getUserId()).equals(merchantLoginDTO.getImageCode())){
                    jsonObject.put("imageResult","true");
                    if(rememberMe) {
                        UUID uuid = UUID.randomUUID();


                        String token = jwtUtil.createJwt(merchantLoginDTO.getUserId(), uuid);
                        System.out.println("create token");
                        jsonObject.put("token", token);
                        jsonObject.put("result","已记住登陆");
                        //还要将token放到redis里面储存
                        System.out.println("uuid:" + uuid.toString());
                        String s = uuid.toString();

                        String uu = merchantService.login(merchantLoginDTO.getUserId(), s);
                        System.out.println("uu:" + uu);
                        //如果已经有登陆信息，更新登录信息
                        if(s != uu) {
                            System.out.println(merchantService.loginAnyway(merchantLoginDTO.getUserId(), s));
                        }

                    }
                    else{
                        jsonObject.put("result","已本次登陆");
                    }
                }
                else {
                    jsonObject.put("imageResult","false");
                }

            }
            else{
                    jsonObject.put("result","登陆失败");
            }
            return jsonObject.toString();

    }


    @GetMapping(value = "/Merchant/homeFalse",produces = "application/json;charset=UTF-8")
    public String homeFalse(@RequestParam("userId") int userId){
        JSONObject jsonObject = new JSONObject();
        if(merchantService.ifExist(userId)){
            jsonObject.put("result","已注册");
        }
        else {
            jsonObject.put("result","未注册");
        }
        String code = codeService.getCode(userId);
        System.out.println("code:"+code);
        String image = verificationService.getImage(userId,code);
        System.out.println("image:"+image);
        jsonObject.put("image",image);
        return jsonObject.toString();
    }


    @GetMapping(value = "/Merchant/home", produces = "application/json;charset=UTF-8")
    public String getCommodityInfo(@RequestParam("page_no") int pageNo , @RequestParam("page_size") int pageSize, @RequestParam("good_type") int goodTypes,@RequestParam("userId" )int userId){
        int merchantId = merchantService.findMerchantIdByUserId(userId);
        JSONObject jsonObject;
        jsonObject = commodityService.getCouponInfoById(pageNo,pageSize,goodTypes,merchantId);
        jsonObject.put("loginResult","自动登录成功");
        return jsonObject.toString();

    }

}
