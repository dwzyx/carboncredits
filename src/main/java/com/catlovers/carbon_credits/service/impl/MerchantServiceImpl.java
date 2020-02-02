package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.MerchantDao;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.catlovers.carbon_credits.service.MerchantService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {
    JSONObject jsonObject = new JSONObject();
    MerchantDao merchantDao ;
    public MerchantServiceImpl(MerchantDao merchantDao){
        this.merchantDao = merchantDao;
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
    public int firstLogin(MerchantLoginDTO merchantLoginDTO) {
        System.out.println("firstLogin");
        if(merchantDao.login(merchantLoginDTO)!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public JSONObject signUp(MerchantDTO merchantDTO) {
        int result = 0;
        System.out.println(merchantDTO.getMerchantName());

        MerchantVO merchantVO1 =null;
        merchantVO1 = merchantDao.ifNameExist(merchantDTO.getMerchantName());
        if(merchantVO1!=null){
            result += 1;
        }
        if(merchantDao.ifPhoneNumberExist(merchantDTO.getMerchantPhoneNumber())!=null){
            result += 10;
        }
        if(merchantDao.ifEmailExist(merchantDTO.getMerchantEmail())!=null){
            result += 100;
        }
        System.out.println(result);
        MerchantVO merchantVO = merchantDTOTomerchantVO(merchantDTO);
        if(result==0){
            merchantDao.signUp(merchantVO);
        }
        jsonObject.put("MerchantSignUpResult",result);
        return jsonObject;
    }

    @Override
    public MerchantVO findById(String userId) {

        return merchantDao.findById(Integer.valueOf(userId));
    }

    private static MerchantVO merchantDTOTomerchantVO(MerchantDTO merchantDTO){

        String password;
        password = new Md5Hash(merchantDTO.getMerchantPassword(), String.valueOf(merchantDTO.getUserId()), 3).toString();
        MerchantVO merchantVO = new MerchantVO
               (merchantDTO.getMerchantId(),merchantDTO.getUserId(),password,merchantDTO.getMerchantPhoneNumber(),merchantDTO.getMerchantEmail(),merchantDTO.getMerchantName(),merchantDTO.getMerchantAddress(),merchantDTO.getMerchantIntroduce(),merchantDTO.getMerchantImage());
        System.out.println(merchantVO.toString());
        return  merchantVO;
    }
}
