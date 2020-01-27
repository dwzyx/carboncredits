package com.catlovers.carbon_credits.service;

import com.alibaba.fastjson.JSONObject;

public interface CodeService {

    char randomChar();
    JSONObject getCode(int userId);
}
