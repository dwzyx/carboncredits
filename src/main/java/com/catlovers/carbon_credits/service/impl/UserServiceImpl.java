package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.CarbonCreditsDao;
import com.catlovers.carbon_credits.dao.CommodityDao;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.enumeration.CityIdEnum;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.*;
import com.catlovers.carbon_credits.model.client.UserClientDTO;
import com.catlovers.carbon_credits.model.client.UserPortraitClientDTO;
import com.catlovers.carbon_credits.service.UserService;
import com.catlovers.carbon_credits.util.ClientUtil;
import com.sun.org.apache.xpath.internal.objects.XNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(CarBonCreditsServiceImpl.class);

    private final RestTemplate restTemplate;
    private final UserDao userDao;
    private final CommodityDao commodityDao;

    JSONObject respond = null;
    JSONObject resultJson = null;

    public UserServiceImpl(RestTemplate restTemplate, UserDao userDao,CommodityDao commodityDao) {
        this.restTemplate = restTemplate;
        this.userDao = userDao;
        this.commodityDao = commodityDao;
    }

    @Override
    @Cacheable(value = "user_info", key = "#root.methodName+':'+#userId")
    public JSONObject getUserInfo(int userId) {

        JSONObject jsonObject = new JSONObject();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑
        UserDTO userDTO;

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

            //返回成功信息
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            userDTO = null;
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }
        jsonObject.put("result", userDTO);
        return jsonObject;
    }

    @Override
    public JSONObject getRankingList(int userId, int cityId) {
        JSONObject jsonObject = new JSONObject();
        List<RankingDTO> rankingDTOS;

        try{
            rankingDTOS = userDao.getRanks(userId, cityId);
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            rankingDTOS = null;
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }
        Map<String, Object> usersMap = new HashMap<>();
        usersMap.put("user_list", rankingDTOS);
        jsonObject.put("result", usersMap);
        return jsonObject;
    }

    @Override
    public JSONObject getMonthlyReport(int userId) {
        MonthlyReportDTO monthlyReportDTO = new MonthlyReportDTO();
        JSONObject jsonObject = new JSONObject();
        Calendar cal = Calendar.getInstance();
        MonthlyReportVO monthlyReportVO;
        Map<String, Integer> dateMap;//储存用户月排名与二氧化碳减排量

        try{
            //得到当前年月
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
            monthlyReportDTO.setUserRankLastMonth(dateMap.get("userRank"));


            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            monthlyReportDTO = null;
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        jsonObject.put("result", monthlyReportDTO);
        return jsonObject;
    }

    @Override
    public JSONObject getTeamInfo(int teamId) {
        JSONObject jsonObject = new JSONObject();
        TeamInfoDTO teamInfoDTO = new TeamInfoDTO();
        TeamInfoVO teamInfoVO = new TeamInfoVO();

        try{
            teamInfoVO = userDao.getTeamInfoVO(teamId);
            List<UserOfTeam> userOfTeamList = userDao.getTeamUsers(teamId);
            teamInfoDTO = getTeamInfoDTOFromVO(teamInfoVO, userOfTeamList);
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            teamInfoDTO = null;
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            teamInfoDTO = null;
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }
        jsonObject.put("result", teamInfoDTO);
        return jsonObject;
    }

    @Override
    public JSONObject addUserToTeam(int teamId, int userId) {
        JSONObject jsonObject = new JSONObject();

        try{
            userDao.addUserToTeam(teamId, userId);
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JSONObject deleteUserFromTeam(int userId) {
        JSONObject jsonObject = new JSONObject();

        try{
            userDao.deleteUserFromTeam(userId);
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }
        return jsonObject;
    }

    @Override
    public JSONObject getUserCoupon(int userId, int pageNo, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        List<CouponBagDTO> couponBagDTOList = null;
        int pageTotal = -1;

        try{
            couponBagDTOList = userDao.getUserCouponBag(userId, (pageNo-1)*pageSize, pageSize);
            for(CouponBagDTO couponBagDTO: couponBagDTOList){
                //如果没用过
                if(couponBagDTO.getCouponUsed()==0){
                    java.sql.Date exchangeTime = couponBagDTO.getExchangeTime();
                    java.util.Date utildate = new Date(exchangeTime.getTime());
                    int expirationTime = couponBagDTO.getExpirationTime();
                    java.util.Date today = new java.util.Date();

                    Calendar c = Calendar.getInstance();
                    c.setTime(utildate);
                    //兑换的那一天+过期天数
                    c.add(Calendar.DAY_OF_MONTH, expirationTime+1);

                    java.util.Date nextTime = c.getTime();
                    //如果今天超过了过期日期
                    boolean bool = today.after(nextTime);
                    if(bool){
                        couponBagDTO.setCouponUsed(1);
                        commodityDao.useCoupon(couponBagDTO.getCouponBagId(),couponBagDTO.getCouponId(),couponBagDTO.getUserId());
                    }
                }

            }
            pageTotal = (userDao.getUserCouponCountTotal(userId, (pageNo-1)*pageSize, pageSize) + pageSize-1)/pageSize;

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("coupon_bag", couponBagDTOList);
        resultMap.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);

        return jsonObject;
    }

    @Override
    public JSONObject getUserDelivery(int userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            List<UserDelivery> list = userDao.getUserDelivery(userId);

            if(!list.isEmpty()){
                jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
                jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
                HashMap<String, Object> resultMap = new HashMap<>();
                resultMap.put("userDelivery", list);
                jsonObject.put("result",resultMap);
            }else {
                jsonObject.put("msg_code", StatusEnum.DATE_NULL.getCoding());
                jsonObject.put("msg_message", StatusEnum.DATE_NULL.getMessage());
            }

        }catch(Exception e){
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }
        return jsonObject;
    }

    @Override
    public JSONObject updateUserDelivery(UserDelivery userDelivery) {
        JSONObject jsonObject = new JSONObject();
        try {
            userDao.updateUserDelivery(userDelivery);
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        }catch(Exception e){
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }

        return jsonObject;
    }

    @Override
    public JSONObject addUserDelivery(UserDelivery userDelivery) {
        JSONObject jsonObject = new JSONObject();
        try {
            userDao.addUserDelivery(userDelivery);
            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        }catch(Exception e){
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }

        return jsonObject;
    }

    private TeamInfoDTO getTeamInfoDTOFromVO(TeamInfoVO teamInfoVO, List<UserOfTeam> userOfTeamList) {
        return new TeamInfoDTO(teamInfoVO.getTeamId(), teamInfoVO.getTeamName(),
                teamInfoVO.getTeamLeaderId(), teamInfoVO.getTeamRank(),
                teamInfoVO.getTeamCarbonCredits(), userOfTeamList);
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

    private UserDTO updateUserInfo(UserClientDTO userClientDTO, UserPortraitClientDTO userPortraitClientDTO) {

        //计算用户等级
        //0.1148sin120°（月乘车次数*乘车票价+周乘车次数*乘车票价+月乘车次数*周乘车次数）
        int a = Integer.parseInt(userPortraitClientDTO.getBWT_TH_SUB_ATCM());//月平均乘车次数
        int b = Integer.parseInt(userPortraitClientDTO.getBWT_TH_SUB_ATCW());//周平均乘车次数
        int c = Integer.parseInt(userPortraitClientDTO.getBWT_TH_SUB_ATP());//平均票价
        int level = (int) (0.0687*0.866*(a*b+a*c+b*c)+1);
        //更新user表
        //更新月碳积分、用户等级、用户所在城市、用户昵称、用户头像路径、
        userDao.updateUserCarbonCreditsMonth(userClientDTO.getUserId(), level, userClientDTO.getCityId(),
                userClientDTO.getNickname(), userClientDTO.getUserImagePath());
        //获取userVO
        UserVO userVO = userDao.getUserBasicByRank(userClientDTO.getUserId());
        if (userVO.getUserRank()>userVO.getRankHighestThisMonth()){
            userDao.updateUserRankThisMonth(userVO.getUserRank(), userClientDTO.getUserId());
        }
        //用户排名
        return new UserDTO(userClientDTO.getNickname(), userClientDTO.getUserImagePath(),
                userClientDTO.getCityId(), CityIdEnum.getCityName(userClientDTO.getCityId()),
                level, userVO.getSignInNumber(), userVO.getCarbonCreditsMonth(),
                userVO.getUserRank(), userVO.getIsStore(), userVO.getSignInToday());
    }


}
