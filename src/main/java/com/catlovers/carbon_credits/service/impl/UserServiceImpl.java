package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.enumeration.CityIdEnum;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.*;
import com.catlovers.carbon_credits.model.client.UserClientDTO;
import com.catlovers.carbon_credits.model.client.UserPortraitClientDTO;
import com.catlovers.carbon_credits.service.UserService;
import com.catlovers.carbon_credits.util.ClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(CarBonCreditsServiceImpl.class);

    private final RestTemplate restTemplate;
    private final UserDao userDao;

    JSONObject respond = null;
    JSONObject resultJson = null;

    public UserServiceImpl(RestTemplate restTemplate, UserDao userDao) {
        this.restTemplate = restTemplate;
        this.userDao = userDao;
    }

    @Override
    @Cacheable(value = "user_info", key = "#root.methodName+':'+#userId")
    public JSONObject getUserInfo(int userId) {

        JSONObject jsonObject = new JSONObject();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑
        UserDTO userDTO = new UserDTO();

        //编辑请求头
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.add("userId", "1");

        //编辑请求体
        map.add("user_id", userId);
        map.add("term_type", 0);
        map.add("term_token", "123123");

        try{
            //获取用户画像信息
            respond = ClientUtil.getRespond(URLEnum.USER_PORTRAIT_URL.getUrl(), map, httpHeaders, restTemplate);
            resultJson = respond.getJSONObject("result");
            UserPortraitClientDTO userPortraitClientDTO = resultJson.getObject("portrais", UserPortraitClientDTO.class);

            //获取用户基本信息
            respond = ClientUtil.getRespond(URLEnum.USER_INFO_URL.getUrl(), map, httpHeaders, restTemplate);
            resultJson = respond.getJSONObject("result");
            UserClientDTO userClientDTO = resultJson.getObject("user", UserClientDTO.class);

            //更新数据库并返回userDTO
            userDTO = updateUserInfo(userClientDTO, userPortraitClientDTO);
        } catch (Exception e){
            userDTO = null;
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }
        jsonObject.put("result", userDTO);
        return jsonObject;
    }

    private UserDTO updateUserInfo(UserClientDTO userClientDTO, UserPortraitClientDTO userPortraitClientDTO) {

        //计算用户等级
        //0.1148sin120°（月乘车次数*乘车票价+周乘车次数*乘车票价+月乘车次数*周乘车次数）
        int a = Integer.parseInt(userPortraitClientDTO.getBWT_TH_SUB_ATCM());//月平均乘车次数
        int b = Integer.parseInt(userPortraitClientDTO.getBWT_TH_SUB_ATCW());//周平均乘车次数
        int c = Integer.parseInt(userPortraitClientDTO.getBWT_TH_SUB_ATP());//平均票价
        int level = (int) (0.0687*0.866*(a*b+a*c+b*c)+1);
        //更新user表
        //更新月碳积分、用户等级
        userDao.updateUserCarbonCreditsMonth(userClientDTO.getUserId(), level);
        //获取userVO
        UserVO userVO = userDao.getUserBasicByRank(userClientDTO.getUserId());
        //用户排名
        return new UserDTO(userClientDTO.getNickname(), userClientDTO.getUserImagePath(),
                userClientDTO.getCityId(), CityIdEnum.getCityName(userClientDTO.getCityId()),
                level, userVO.getSignInNumber(), userVO.getCarbonCreditsMonth(),
                userVO.getUserRank(), userVO.getIsStore(), userVO.getSignInToday());
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
