package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.service.CodeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CodeServiceImpl implements CodeService {
    private Random r = new Random();

    // 可选字符
    private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";

    @Override
    public char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    @Override
    @Cacheable(value = "verification",key = "#root.methodName+':'+#userId")
    public JSONObject getCode(int userId) {
        JSONObject jsonObject = new JSONObject();
        StringBuilder sb = new StringBuilder();// 用来装载生成的验证码文本
        for (int i = 0; i < 4; i++) {// 循环四次，每次生成一个字符
            String s = randomChar() + "";// 随机生成一个字母
            sb.append(s);
        };
        String str = sb.toString();
        System.out.println(str);
        jsonObject.put("verification",str);
        return jsonObject;
    }
}
