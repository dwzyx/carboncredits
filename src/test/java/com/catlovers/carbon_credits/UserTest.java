package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.CouponBagDTO;
import com.catlovers.carbon_credits.model.MonthlyReportDTO;
import com.catlovers.carbon_credits.model.MonthlyReportVO;
import com.catlovers.carbon_credits.model.UserOfTeam;
import com.catlovers.carbon_credits.model.client.UserClientDTO;
import com.catlovers.carbon_credits.util.ClientUtil;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDao userDao;

    @Test
    void test_getUserClientInfo(){
        JSONObject jsonObject = new JSONObject();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑


        //编辑请求头
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.add("userId", "1");

        //编辑请求体
        map.add("user_id", 1);
        map.add("term_type", 0);
        map.add("term_token", "123123");
        JSONObject respond = ClientUtil.getRespond(URLEnum.USER_INFO_URL.getUrl(), map, httpHeaders, restTemplate);
        JSONObject resultJson = respond.getJSONObject("result");
        UserClientDTO user = resultJson.getObject("user", UserClientDTO.class);
        System.out.println(user);
    }
    @Test
    void test_02(){
//        List<MonthlyReportVO> monthlyReport = userDao.getMonthlyReport(1, "2019-10", "2020-10");
//        System.out.println(monthlyReport);
        List<CouponBagDTO> userCouponBag = userDao.getUserCouponBag(1, 1, 5);
        System.out.println(userCouponBag);
    }

    @Test
    void test_teamInfo(){
        int teamId = 1;
        List<UserOfTeam> teamUsers = userDao.getTeamUsers(1);
        System.out.println(teamUsers.toString());
    }


}
