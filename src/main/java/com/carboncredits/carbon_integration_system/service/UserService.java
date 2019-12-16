package com.carboncredits.carbon_integration_system.service;


import com.alibaba.fastjson.annotation.JSONField;
import com.carboncredits.carbon_integration_system.enumeration.ErrorEnum;
import com.carboncredits.carbon_integration_system.mapper.UserMapper;
import com.carboncredits.carbon_integration_system.model.DailyItinerary;
import com.carboncredits.carbon_integration_system.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class UserService {

    HashMap<String, Object> map = new HashMap<>();

    final
    UserMapper userMapper;
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Cacheable(value = "user_info")
    public HashMap<String, Object> getUserInfo(int id){
        User user = userMapper.findUserById(id);
        map.put("result", user);
        return map;
    }

    @Cacheable(value = "user_home_message", key = "#root.methodName+#userId")
    public HashMap<String, Object> getUserHomeMessage(int userId) {
        try {
            DailyItinerary userDailyItinerary = userMapper.getUserDailyItinerary(userId);
            int carbonCredits = userMapper.getUserCarbonCredits(userId);

            map.put("daily_itinerary", userDailyItinerary);
            map.put("user_carbon_credits", carbonCredits);
        }catch (Exception e){
            map.put("error", ErrorEnum.valueOf("PARAMETER_ERROR").getCoding());
        }
        return map;
    }
}
