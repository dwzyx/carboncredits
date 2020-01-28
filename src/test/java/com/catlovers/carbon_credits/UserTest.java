package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.MonthlyReportDTO;
import com.catlovers.carbon_credits.model.MonthlyReportVO;
import com.catlovers.carbon_credits.model.client.UserClientDTO;
import com.catlovers.carbon_credits.util.ClientUtil;
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
    void test_monthlyReport(){
        int userId = 1;
        MonthlyReportDTO monthlyReportDTO = new MonthlyReportDTO();
        Map<String , Integer> dateMap;
        MonthlyReportVO monthlyReportVO;
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        Map<String, Integer> timeMap = new HashMap<>();
        timeMap.put("month", month);
        timeMap.put("year", year);

        //获取上个月的月度报告
        monthlyReportVO = getMonthlyReportVO(userId, timeMap);
        //处理月度报告
        dateMap = getCO2Reduction(monthlyReportVO, monthlyReportDTO);
        monthlyReportDTO.setCO2ReductionThisMonth(dateMap.get("CO2Reduction"));
        monthlyReportDTO.setUserRankThisMonth(dateMap.get("userRank"));
        //获取上上个月的月度报告
        timeMap.replace("month", timeMap.get("month")-1);
        monthlyReportVO = getMonthlyReportVO(userId, timeMap);
        //处理月度报告
        dateMap = getCO2Reduction(monthlyReportVO, monthlyReportDTO);
        monthlyReportDTO.setCO2ReductionLastMonth(dateMap.get("CO2Reduction"));
        monthlyReportDTO.setUserRankThisMonth(dateMap.get("userRank"));



        monthlyReportVO = getMonthlyReportVO(userId, timeMap);
        //处理月度报告
        dateMap = getCO2Reduction(monthlyReportVO, monthlyReportDTO);
        monthlyReportDTO.setCO2ReductionThisMonth(dateMap.get("CO2Reduction"));
        monthlyReportDTO.setUserRankThisMonth(dateMap.get("userRank"));
        //获取上上个月的月度报告
        monthlyReportVO = getMonthlyReportVO(userId, timeMap);
        //处理月度报告
        dateMap = getCO2Reduction(monthlyReportVO, monthlyReportDTO);
        monthlyReportDTO.setCO2ReductionLastMonth(dateMap.get("CO2Reduction"));
        monthlyReportDTO.setUserRankLastMonth(dateMap.get("userRank"));
        System.out.println(monthlyReportDTO);
    }

    private Map<String, Integer> getCO2Reduction(MonthlyReportVO monthlyReportVO, MonthlyReportDTO monthlyReportDTO) {
        Map<String, Integer> map = new HashMap<>();
        int mileageBike = monthlyReportVO.getMileageBike();
        int mileageBus = monthlyReportVO.getMileageBus();
        int mileageSubway = monthlyReportVO.getMileageSubway();
        int mileageWalk = monthlyReportVO.getMileageWalk();
        int mileageTotal = mileageBike+mileageBus+mileageSubway+mileageWalk;
        int CO2Reduction = mileageTotal*1000-(mileageWalk+mileageBike)*50-mileageBus*200-mileageSubway*100;

        map.put("CO2Reduction", CO2Reduction);
        map.put("userRank", monthlyReportVO.getUserRank());

        monthlyReportDTO.setMileageBike(mileageBike);
        monthlyReportDTO.setMileageBus(mileageBus);
        monthlyReportDTO.setMileageSubway(mileageSubway);
        monthlyReportDTO.setMileageWalk(mileageWalk);
        monthlyReportDTO.setMileageTotal(mileageTotal);
        return map;

    }

    private MonthlyReportVO getMonthlyReportVO(int userId, Map<String, Integer> timeMap) {
        MonthlyReportVO monthlyReportVO;
        if(timeMap.get("month")==0){
            timeMap.replace("year", timeMap.get("year")-1);
            timeMap.replace("month", 12);
        }
        System.out.println(timeMap.get("month"));
        System.out.println(timeMap.get("year"));
        //获取上上个月的月度报告
        monthlyReportVO = userDao.getMonthlyReport(userId, timeMap.get("year"), timeMap.get("month"));
        return  monthlyReportVO;
    }
}
