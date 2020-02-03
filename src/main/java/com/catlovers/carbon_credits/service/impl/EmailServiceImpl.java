package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Random;

@Service
@CacheConfig(cacheNames = "emaiVeri")
public class EmailServiceImpl implements EmailService {

    private final RestTemplate restTemplate;
    @Autowired
    private JavaMailSender mailSender;

    public EmailServiceImpl(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Override
    @Cacheable(value = "emailVeri",key = "#root.methodName+':'+#merchantName")
    public String emailVerification(String merchantEmail,String merchantName) {

        Random random=new Random();
        int ran = (int)(random.nextDouble()*(99999-10000 + 1))+ 10000;
        String ranNum = String.valueOf(ran);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("‘低碳出行小程序’需要验证您的注册邮箱");
        mailMessage.setFrom("738667591@qq.com");
        mailMessage.setTo(merchantEmail);
        mailMessage.setText("您的邮箱验证码为"+ranNum+",用于用户："+merchantName.charAt(0)+"*的注册验证,15分钟有效。");

        try{
            mailSender.send(mailMessage);
            System.out.println("已发送");
        }catch (Exception e){
            return null;
        }
        return ranNum.toString();
    }

    @Override
    @CacheEvict(value = "emailVeri",key = "'emailVerification:'+#merchantName")
    public void deleteEmailVerification(String merchantName) {
    }


}
