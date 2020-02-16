package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.CarbonCreditsDao;
import com.catlovers.carbon_credits.enumeration.ServiceIDEnum;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.CarbonCreditsDTO;
import com.catlovers.carbon_credits.model.CarbonCreditsVO;
import com.catlovers.carbon_credits.model.client.BaseTripClientDTO;
import com.catlovers.carbon_credits.model.client.BaseTripListClientDTO;
import com.catlovers.carbon_credits.service.CarbonCreditsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.catlovers.carbon_credits.util.ClientUtil.getRespond;


@Service
@Transactional(rollbackFor=Exception.class)
public class CarBonCreditsServiceImpl implements CarbonCreditsService {

    private final static Logger logger = LoggerFactory.getLogger(CarBonCreditsServiceImpl.class);
    public static final int PAGE_SIZE = 10;
    private final RestTemplate restTemplate;
    private final CarbonCreditsDao carbonCreditsDao;

    public CarBonCreditsServiceImpl(RestTemplate restTemplate, CarbonCreditsDao carbonCreditsDao) {
        this.restTemplate = restTemplate;
        this.carbonCreditsDao = carbonCreditsDao;
    }

    @Cacheable(value = "cardits_info", key = "#root.methodName+':'+#userId")
    @Override
    public JSONObject getCreditsInfo(int userId) {
        JSONObject jsonObject = new JSONObject();
        CarbonCreditsDTO carBonCreditsDTO = new CarbonCreditsDTO();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑

        CarbonCreditsVO carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(userId);

        //编辑请求头
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.add("userId", "1");

        //编辑请求体
//        map.add("service_id", "00");  加入service_id="00"出错，其他无问题
//        map.add("trip_flag", "02");   无法访问数据
        map.add("page_no", carbonCreditsVO.getLastPageNo());
        map.add("page_size", PAGE_SIZE);


        try {
            //获取响应体
            JSONObject respond = getRespond(URLEnum.TRIP_BASE_URL.getUrl(), map, httpHeaders, restTemplate);

            if (respond.get("errcode").equals("0000")) {
                //获取行程信息
                JSONObject resultJson= respond.getJSONObject("result");
                logger.info(jsonObject.toString());
                BaseTripListClientDTO baseTripListClientDTO = resultJson.getObject("page", BaseTripListClientDTO.class);
                //获取并更新碳积分
                carBonCreditsDTO = updateUserCarbonCredits(baseTripListClientDTO, carbonCreditsVO, map, httpHeaders);
            } else {
                throw new ChangeSetPersister.NotFoundException();
            }

            //处理成功
            StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
        } catch (ChangeSetPersister.NotFoundException e){ //异常处理
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            carBonCreditsDTO = null;
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (IndexOutOfBoundsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            carBonCreditsDTO = null;
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            carBonCreditsDTO = null;
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        } finally{
            jsonObject.put("result", carBonCreditsDTO);
        }

        return jsonObject;
    }

    private CarbonCreditsDTO updateUserCarbonCredits(BaseTripListClientDTO baseTripListClientDTO,
                                                     CarbonCreditsVO carbonCreditsVO, MultiValueMap<String, Object> map, HttpHeaders httpHeaders) {

        //获取上次最后记录的行程
        int pageNo = carbonCreditsVO.getLastPageNo();
        int pageTotal = baseTripListClientDTO.getTotalPage();
        int lastTripNo = carbonCreditsVO.getLastTripNo();
        int totalCount = baseTripListClientDTO.getTotalCount();
        //获取剩余行程
        List<BaseTripClientDTO> rows = null;

        //更新数据库有关信息
        int carbonCredits = 0;
        int mileageSubway = 0;
        int mileageBus = 0;
        int mileageBike = 0;
        int mileageWalk = 0;

        //使用二重循环遍历所有未记录的行程，while循环遍历每一页，for循环遍历一页中所有行程
        while(pageNo<=pageTotal){
            int size = pageNo < pageTotal ?
                    PAGE_SIZE:totalCount%PAGE_SIZE;
            rows = baseTripListClientDTO.getRows();
            for(int index=(lastTripNo+1)%PAGE_SIZE;
                index<=size; index++, lastTripNo++){
                BaseTripClientDTO baseTripClientDTO = rows.get(index-1);
                logger.info(String.valueOf(lastTripNo));
                switch (ServiceIDEnum.getByValue(baseTripClientDTO.getServiceId())){
                    case SUBWAY://地铁行程信息处理
                        carbonCredits += (baseTripClientDTO.getTripTotalFee()-200)+200;
                        mileageSubway += baseTripClientDTO.getMileage();
                        break;
                    case BUS:   //公交行程信息处理
                        carbonCredits += baseTripClientDTO.getTripTotalFee();
                        mileageBus += baseTripClientDTO.getMileage();
                        break;
                    case BIKE:
                    case WALK:
                    case HighSpeedRail:
                }


            }
            //如果不是最后一页就往后面继续请求
            if(++pageNo < pageTotal){
                map.set("page_no", pageNo);
                JSONObject respond = getRespond(URLEnum.TRIP_BASE_URL.getUrl(), map, httpHeaders, restTemplate);
                JSONObject resultJson= respond.getJSONObject("result");
                baseTripListClientDTO = resultJson.getObject("page", BaseTripListClientDTO.class);
            }
        }

        //统计从新增的里程和碳积分
        //增加可用碳积分
        carbonCreditsVO.setCarbonCreditsUnclaimed((carbonCreditsVO.getCarbonCreditsUnclaimed()+carbonCredits)/100);
        //增加各个出行方式的里程
        carbonCreditsVO.setMileageBikeToday(carbonCreditsVO.getMileageBikeToday()+mileageBike);
        carbonCreditsVO.setMileageBikeTotal(carbonCreditsVO.getMileageBikeTotal()+mileageBike);
        carbonCreditsVO.setMileageBusToday(carbonCreditsVO.getMileageBusToday()+mileageBus);
        carbonCreditsVO.setMileageBusTotal(carbonCreditsVO.getMileageBusTotal()+mileageBus);
        carbonCreditsVO.setMileageSubwayToday(carbonCreditsVO.getMileageSubwayToday()+mileageSubway);
        carbonCreditsVO.setMileageSubwayTotal(carbonCreditsVO.getMileageSubwayTotal()+mileageSubway);
        carbonCreditsVO.setMileageWalkToday(carbonCreditsVO.getMileageWalkToday()+mileageWalk);
        carbonCreditsVO.setMileageWalkTotal(carbonCreditsVO.getMileageWalkTotal());
        carbonCreditsVO.setLastPageNo(--pageNo);
        carbonCreditsVO.setLastTripNo(lastTripNo);

        carbonCreditsDao.updateCarbonCredits(carbonCreditsVO);

        return new CarbonCreditsDTO(carbonCreditsVO.getCarbonCreditsUnclaimed(), carbonCreditsVO.getCarbonCreditsTotal(), carbonCreditsVO.getCarbonCreditsToday(),
                carbonCreditsVO.getCarbonCreditsUseful(), carbonCreditsVO.getMileageSubwayToday(),
                carbonCreditsVO.getMileageSubwayTotal(), carbonCreditsVO.getMileageBusToday(),
                carbonCreditsVO.getMileageBusTotal(), carbonCreditsVO.getMileageWalkToday(),
                carbonCreditsVO.getMileageWalkTotal(), carbonCreditsVO.getMileageBikeToday(),
                carbonCreditsVO.getMileageBikeTotal());
    }


}
