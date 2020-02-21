package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.CommodityDao;
import com.catlovers.carbon_credits.dao.MerchantDao;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.catlovers.carbon_credits.service.MerchantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantDao merchantDao ;
    private final CommodityDao commodityDao;
    public MerchantServiceImpl(MerchantDao merchantDao,CommodityDao commodityDao){
        this.merchantDao = merchantDao;
        this.commodityDao = commodityDao;
    }

    /**
     * 创建缓存：当UUID!=NULL时， 缓存id和UUID
     * 核实缓存：当UUID==NULL时， 如果key==id存在，则返回缓存里的UUID，否则返回NULL；
     * @param id
     * @param uuid
     * @return
     */
    @Override
    @Cacheable(value = "login",key = "#root.methodName+':'+#id",condition = "#uuid!=NULL")
    public String login(int id,String uuid) {
        System.out.println("cach:"+id+"uuid:"+uuid);
        return uuid;

    }


    @Override
    @CachePut(value = "login",key = "'login:'+#id")
    public String loginAnyway(int id,String uuid){
        System.out.println("cachAnyway:"+id+"uuid:"+uuid);
        return uuid;
    }

    @Override
    public boolean ifExist(int id) {
        if(merchantDao.ifExist(id)!=null){
            return true;
        }
        return false;
    }

    @Override
    public int firstLogin(int userId,String merchantPassword) {
        System.out.println("firstLogin");
        String password = new Md5Hash(merchantPassword, String.valueOf(userId), 3).toString();
        if(merchantDao.login(userId,password)!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public JSONObject signUp(MerchantVO merchantVO) {
        JSONObject jsonObject = new JSONObject();
        int result = 0;
        System.out.println(merchantVO.getMerchantName());

        MerchantVO merchantVO1 =null;
        merchantVO1 = merchantDao.ifNameExist(merchantVO.getMerchantName());
        if(merchantVO1!=null){
            result += 1;
        }
        if(merchantDao.ifPhoneNumberExist(merchantVO.getMerchantPhoneNumber())!=null){
            result += 10;
        }
        if(merchantDao.ifEmailExist(merchantVO.getMerchantEmail())!=null){
            result += 100;
        }
        System.out.println(result);
        String password = new Md5Hash(merchantVO.getMerchantPassword(), String.valueOf(merchantVO.getUserId()), 3).toString();
        merchantVO.setMerchantPassword(password);
        if(result==0){
            merchantDao.signUp(merchantVO);
        }
        jsonObject.put("MerchantSignUpResult",result);
        return jsonObject;
    }

    @Override
    public int findMerchantIdByUserId(int userId) {

        return merchantDao.findMerchantIdByUserId(userId);
    }

    @Override
    public JSONObject getAll(int userId){
        JSONObject jsonObject = new JSONObject();
        MerchantVO merchantVO;
        merchantVO = merchantDao.ifExist(userId);
        jsonObject.put("merchantPhoneNumber",merchantVO.getMerchantPhoneNumber());
        jsonObject.put("merchantEmail",merchantVO.getMerchantEmail());
        jsonObject.put("merchantName",merchantVO.getMerchantName());
        jsonObject.put("merchantAddress",merchantVO.getMerchantAddress());
        jsonObject.put("merchantIntroduce",merchantVO.getMerchantIntroduce());
        jsonObject.put("merchantImage",merchantVO.getMerchantImage());
        return jsonObject;
    }

    @Override
    public JSONObject modify(MerchantDTO merchantDTO) {
        JSONObject jsonObject = new JSONObject();
        int i = merchantDao.modify(merchantDTO);
        if(i!=0){
            jsonObject.put("modifyResult", "true");
        }
        else{
            jsonObject.put("modifyResult", "false");
        }
        return jsonObject;
    }

    @Override
    public JSONObject modifyPassword(int userId, String merchantPassword) {
        JSONObject jsonObject = new JSONObject();
        String password = new Md5Hash(merchantPassword, String.valueOf(userId), 3).toString();
        int i = merchantDao.modifyPassword(userId,password);
        if(i!=0){
            jsonObject.put("modifyResult","true");
        }
        else{
            jsonObject.put("modifyResult","false");
        }
        return jsonObject;
    }

    @Override
    public String getName(int userId) {
        String name = merchantDao.getName(userId);
        return name;
    }

    @Override
    public JSONObject useCoupon(int couponBagId,int couponId, int userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            commodityDao.useCoupon(couponBagId,couponId,userId);
            StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
        }catch (NullPointerException e){
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (IndexOutOfBoundsException e) {
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        }catch(Exception e){
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }
        return jsonObject;
    }

    //    private static MerchantVO merchantDTOTomerchantVO(MerchantDTO merchantDTO){
    ////
    ////        String password;
    ////                MerchantVO merchantVO = new MerchantVO
    ////               (merchantDTO.getMerchantId(),merchantDTO.getUserId(),password,merchantDTO.getMerchantPhoneNumber(),merchantDTO.getMerchantEmail(),merchantDTO.getMerchantName(),merchantDTO.getMerchantAddress(),merchantDTO.getMerchantIntroduce(),merchantDTO.getMerchantImage());
    ////        System.out.println(merchantVO.toString());
    ////        return  merchantVO;
    ////    }
}
