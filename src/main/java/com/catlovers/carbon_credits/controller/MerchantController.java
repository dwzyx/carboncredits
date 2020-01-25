package com.catlovers.carbon_credits.controller;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.service.MerchantService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    public String merchantLogin(@RequestBody MerchantLoginDTO merchantLoginDTO){
        jsonObject = merchantService.login(merchantLoginDTO);
        return jsonObject.toString();
    }

}
