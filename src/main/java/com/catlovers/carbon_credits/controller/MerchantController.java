package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.service.MerchantService;
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
    private JSONObject jsonObject = new JSONObject();
    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping(value = "/Merchant/signUp", produces = "application/json;charset=UTF-8")
    public String merchantSignUp(@RequestBody MerchantDTO merchantDTO){
        jsonObject = merchantService.signUp(merchantDTO);
        return jsonObject.toString();
    }

    @GetMapping(value = "/Merchant/login", produces = "application/json;charset=UTF-8")
    public String merchantLogin(@RequestBody MerchantLoginDTO merchantLoginDTO, boolean rememberMe, HttpServletRequest request) {
        JwtUtil jwtUtil = new JwtUtil();

        String password = new Md5Hash(merchantLoginDTO.getMerchantPassword(), String.valueOf(merchantLoginDTO.getUserId()), 3).toString();
        merchantLoginDTO.setMerchantPassword(password);
        int i = merchantService.firstLogin(merchantLoginDTO);
            if(i == 1) {
                System.out.println(1);
                if(rememberMe) {
                    UUID uuid = UUID.randomUUID();


                    String token = jwtUtil.createJwt(merchantLoginDTO.getUserId(), uuid);
                    System.out.println("create token");
                    jsonObject.put("token", token);
                    //还要将token放到redis里面储存
                    System.out.println("uuid:"+uuid.toString());
                    String s = uuid.toString();

                    String uu = merchantService.login(5,s);
                    System.out.println("uu:"+uu);
                    if(s!=uu){
                        System.out.println(merchantService.loginAnyway(5,s));
                    }
                }
            }
            else{
                if(merchantService.ifExist(merchantLoginDTO.getUserId())){
                    jsonObject.put("result","已注册");
                }
                else {
                    jsonObject.put("result","未注册");
                }
            }
            return jsonObject.toString();

    }

    //个人主页
    @RequestMapping(value = "/Merchant/home")
    public String home() { return "访问个人主页成功"; }


}
