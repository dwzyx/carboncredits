package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.catlovers.carbon_credits.dao.CarbonCreditsDao;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
import com.catlovers.carbon_credits.model.CarbonCreditsVO;
import com.catlovers.carbon_credits.model.UserDTO;
import com.catlovers.carbon_credits.model.UserMessageDTO;
import com.catlovers.carbon_credits.service.CarbonCreditsService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Objects;



@Service
@Transactional
public class CarBonCreditsServiceImpl implements CarbonCreditsService {


    private final RestTemplate restTemplate;
    private final CarbonCreditsDao carbonCreditsDao;
    private ApplicationContext context = new AnnotationConfigApplicationContext();

    public CarBonCreditsServiceImpl(RestTemplate restTemplate, CarbonCreditsDao carbonCreditsDao) {
        this.restTemplate = restTemplate;
        this.carbonCreditsDao = carbonCreditsDao;
    }

    @Cacheable(value = "cardits_info", key = "#root.methodName+':'+#userId")
    @Override
    public JSONObject getCreditsInfo(int userId) {

        JSONObject jsonObject = new JSONObject();
        CarBonCreditsDTO carBonCreditsDTO = new CarBonCreditsDTO();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        HttpHeaders httpHeaders = new HttpHeaders();


        map.add("user_id", userId);
        map.add("term_type", 0);
        map.add("term_token", "1234");

        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=UTF-8");
        httpHeaders.add("userId", "1");

        try {
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(URLEnum.TRIP_BASE_URL.getUrl(),
                HttpMethod.POST, new HttpEntity<>(map, httpHeaders), JSONObject.class);

        JSONObject result = exchange.getBody();
        if (result != null) {
            HashMap<String, Object> jsonMap = JSONObject.parseObject(result.toString(),
                    new TypeReference<HashMap<String, Object>>() {
                    });
            UserMessageDTO userMessage = (UserMessageDTO) jsonMap.get("user");//获得用户基本信息

            updateUserCarbonCredits();

            CarbonCreditsVO carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(userId);

            carBonCreditsDTOToCarBonCreditsVO(carBonCreditsDTO, carbonCreditsVO);
        } else {
            carBonCreditsDTO = null;
        }

        jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
        jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());

        }catch (Exception e){
            carBonCreditsDTO = null;
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        jsonObject.put("result", carBonCreditsDTO);
        return jsonObject;
    }

    private void updateUserCarbonCredits() {

    }


    private static void carBonCreditsDTOToCarBonCreditsVO(CarBonCreditsDTO carBonCreditsDTO, CarbonCreditsVO carbonCreditsVO) {
        carBonCreditsDTO.setCarbonCreditsToday(carbonCreditsVO.getCarbonCreditsToday());
        carBonCreditsDTO.setCarbonCreditsTotal(carbonCreditsVO.getCarbonCreditsTotal());
        carBonCreditsDTO.setCarbonCreditsUseful(carbonCreditsVO.getCarbonCreditsUseful());
        carBonCreditsDTO.setMileageBikeToday(carbonCreditsVO.getMileageBikeToday());
        carBonCreditsDTO.setMileageBikeTotal(carbonCreditsVO.getMileageBikeTotal());
        carBonCreditsDTO.setMileageBusToday(carbonCreditsVO.getMileageBusToday());
        carBonCreditsDTO.setMileageBusTotal(carbonCreditsVO.getMileageBusToday());
        carBonCreditsDTO.setMileageSubwayToday(carbonCreditsVO.getMileageSubwayToday());
        carBonCreditsDTO.setMileageSubwayTotal(carbonCreditsVO.getMileageSubwayTotal());
        carBonCreditsDTO.setMileageWalkToday(carbonCreditsVO.getMileageWalkToday());
        carBonCreditsDTO.setMileageWalkTotal(carbonCreditsVO.getMileageWalkTotal());
    }

}
