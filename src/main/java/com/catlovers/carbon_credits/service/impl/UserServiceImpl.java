package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserDTO;
import com.catlovers.carbon_credits.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    final
    UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public JSONObject getUserInfo(int userId) {

        UserDTO userDTO = new UserDTO();
        userDTO.setCity_id(3302);
        userDTO.setCity_name("杭州");
        userDTO.setNickname("小明");
        userDTO.setUser_image_path("https://i.loli.net/2020/01/16/q9HUiEuzDrCJLOZ.jpgs");
        userDTO.setCarbon_credits_month(1);
        userDTO.setSign_in_number(1);
        userDTO.setUser_level(1);
        userDTO.setUser_rank(1);

        return null;
    }

    @Override
    public JSONObject getRankingList(int userId, int cityId) {
        JSONObject jsonObject = new JSONObject();

        RankingDTO rankingDTO = new RankingDTO();
        rankingDTO.setNickname("小明");
        rankingDTO.setCarbonCredits(1);
        rankingDTO.setUserImagePath("https://i.loli.net/2020/01/16/q9HUiEuzDrCJLOZ.jpgs");
        rankingDTO.setUserRank(1);

        return jsonObject;
    }
}
