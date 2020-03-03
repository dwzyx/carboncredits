package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.UserDao;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private final UserDao userDao;

    public GameServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public JSONObject getHomePage() {
        JSONObject jsonObject = new JSONObject();

        try{
            jsonObject.put("cat_image", "http://n.sinaimg.cn/sinacn15/275/w640h435/20181010/caba-hkrzvkw4936632.jpg");
            StatusEnum.getMessageJson(StatusEnum.SUCCESS, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.FAILED, jsonObject);
        }

        return jsonObject;
    }

    @Override
    public JSONObject gameEvent(int userId, int carbonCredits) {
        JSONObject jsonObject = new JSONObject();

        try{
            int carbonCreditsUseful = userDao.searchUserCarbonCreditsUseful(userId);
            userDao.updateUserCarbonCreditsUseful(userId, carbonCredits+carbonCreditsUseful);
            jsonObject.put("cat_image", "http://tyunfile.71360.com/UpLoadFile/2019/12/19/8/637123401175027935_weifangqiye_5642576.jpg");
            StatusEnum.getMessageJson(StatusEnum.SUCCESS, jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.FAILED, jsonObject);
        }

        return jsonObject;
    }
}
