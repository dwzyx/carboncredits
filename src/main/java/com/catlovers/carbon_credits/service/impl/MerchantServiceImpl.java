package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.MerchantDao;
import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;
import org.springframework.stereotype.Service;
import com.catlovers.carbon_credits.service.MerchantService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Service
public class MerchantServiceImpl implements MerchantService {
    JSONObject jsonObject = new JSONObject();
    MerchantDao merchantDao;
    @Override
    public JSONObject login(MerchantLoginDTO merchantLoginDTO) {
        System.out.println(merchantLoginDTO);
        MerchantVO merchantVO;
        merchantVO = merchantDao.login(merchantLoginDTO);
        if(merchantVO!=null){
            jsonObject.put("merchantLoginResult","0");
        }

        return jsonObject;
    }

    @Override
    public JSONObject signUp(MerchantDTO merchantDTO) {
        int result = 0;
        System.out.println(merchantDTO.getMerchantName());

            if(merchantDao.ifNameExist(merchantDTO.getMerchantName())!=null){
                result += 1;
            }

        if(merchantDao.ifNameExist(merchantDTO.getMerchantName())!=null){
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
        merchantDao.signUp(merchantVO);
        jsonObject.put("MerchantSignUpResult",result);
        return jsonObject;
    }

    private static MerchantVO merchantDTOTomerchantVO(MerchantDTO merchantDTO){
        Date date = new Date();
        System.out.println("时间"+date);
        MerchantVO merchantVO = new MerchantVO
               (merchantDTO.getMerchantId(),merchantDTO.getUserId(),merchantDTO.getMerchantPassword(),merchantDTO.getMerchantPhoneNumber(),merchantDTO.getMerchantEmail(),merchantDTO.getMerchantName(),merchantDTO.getMerchantAddress(),merchantDTO.getMerchantIntroduce(),merchantDTO.getMerchantImage());
        System.out.println(merchantVO.toString());
        return  merchantVO;
    }
}
