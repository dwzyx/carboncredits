package com.catlovers.carbon_credits;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.CarbonCreditsDao;
import com.catlovers.carbon_credits.enumeration.ServiceIDEnum;
import com.catlovers.carbon_credits.enumeration.URLEnum;
import com.catlovers.carbon_credits.model.CarbonCreditsDTO;
import com.catlovers.carbon_credits.model.CarbonCreditsVO;
import com.catlovers.carbon_credits.model.client.BaseTripClientDTO;
import com.catlovers.carbon_credits.model.client.BaseTripListClientDTO;
import com.catlovers.carbon_credits.service.CarbonCreditsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

import static com.catlovers.carbon_credits.util.ClientUtil.getRespond;


@SpringBootTest
class CarbonCreditsApplicationTests {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CarbonCreditsService carbonCreditsService;

    @Test
    void test_restTemplate() {
        JSONObject jsonObject = new JSONObject();
        CarbonCreditsDTO carBonCreditsDTO = new CarbonCreditsDTO();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑

        //编辑请求头
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
        httpHeaders.add("userId", "1");

        //编辑请求体
        map.add("user_id", 1);
        map.add("term_type", 0);
        map.add("term_token", "1234");

        //发现请求
        ResponseEntity<JSONObject> exchange = restTemplate.exchange(URLEnum.TRIP_BASE_URL.getUrl(),
                HttpMethod.POST, new HttpEntity<>(map, httpHeaders), JSONObject.class);
        //获取响应体
        JSONObject result = exchange.getBody();

        if (result != null) {
            //jSONObject转HashMap
            JSONObject resultJson= result.getJSONObject("result");
            BaseTripListClientDTO baseTripListClientDTO = resultJson.getObject("page", BaseTripListClientDTO.class);

        }


    }

    @Autowired
    CarbonCreditsDao carbonCreditsDao;

    @Rollback
    @Test
    void test_01() {
        int PAGE_SIZE = 10;

        JSONObject jsonObject = new JSONObject();
        CarbonCreditsDTO carBonCreditsDTO = new CarbonCreditsDTO();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();   //请求体信息
        HttpHeaders httpHeaders = new HttpHeaders();                       //请求头信息的编辑

        CarbonCreditsVO carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(1);

        //编辑请求头
        httpHeaders.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httpHeaders.add("userId", "1");

        //编辑请求体
//        map.add("service_id", "00");
//        map.add("trip_flag", "02");
        map.add("page_no", carbonCreditsVO.getLastPageNo());
        map.add("page_size", PAGE_SIZE);
        int lastTripNo = carbonCreditsVO.getLastTripNo();

            //获取响应体
        JSONObject respond = getRespond(URLEnum.TRIP_BASE_URL.getUrl(), map, httpHeaders, restTemplate);
        System.out.println(respond.get("errcode"));
            if (respond.get("errcode").equals("0000")){
                //获取行程信息
                JSONObject resultJson = respond.getJSONObject("result");
                BaseTripListClientDTO baseTripListClientDTO = resultJson.getObject("page", BaseTripListClientDTO.class);
                System.out.println(resultJson);
                System.out.println(carbonCreditsVO);
                System.out.println(baseTripListClientDTO);
                //获取并更新碳积分
                //获取上次最后记录的行程
                int pageNo = carbonCreditsVO.getLastPageNo();
                //获取剩余行程
                List<BaseTripClientDTO> rows = null;

                //更新数据库有关信息
                int carbonCredits = 0;
                int mileageSubway = 0;
                int mileageBus = 0;
                int mileageBike = 0;
                int mileageWalk = 0;

                //使用二重循环遍历所有未记录的行程，while循环遍历每一页，for循环遍历一页中所有行程
                int pageTotal = baseTripListClientDTO.getTotalPage();
                while(pageNo<=pageTotal){
                    int size = pageNo < pageTotal ?
                            PAGE_SIZE:pageTotal%PAGE_SIZE;
                    rows = baseTripListClientDTO.getRows();
                    for(int index=(lastTripNo+1)%PAGE_SIZE;
                        index<=size; index++, lastTripNo++){
                        BaseTripClientDTO baseTripClientDTO = rows.get(index);
                        switch (ServiceIDEnum.getByValue(baseTripClientDTO.getServiceId())){
                            case SUBWAY://地铁行程信息处理
                                carbonCredits += (baseTripClientDTO.getTripTotalFee()-2)+2;
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
                        respond = getRespond(URLEnum.TRIP_BASE_URL.getUrl(), map, httpHeaders, restTemplate);
                        resultJson= respond.getJSONObject("result");
                        baseTripListClientDTO = resultJson.getObject("page", BaseTripListClientDTO.class);
                    }
                }
//                while (pageNo <= pageTotal) {
//                    int size = baseTripListClientDTO.getPageNo() < baseTripListClientDTO.getTotalPage() ?
//                            PAGE_SIZE : baseTripListClientDTO.getTotalCount() % PAGE_SIZE;
//                    rows = baseTripListClientDTO.getRows();
//                    System.out.println(pageNo);
//                    System.out.println(pageTotal);
//                    for (int index = carbonCreditsVO.getLastTripNo() + 1;
//                         index <= size; index++) {
//                        BaseTripClientDTO baseTripClientDTO = rows.get(index-1);
//                        System.out.println(index);
//                        switch (ServiceIDEnum.getByValue(baseTripClientDTO.getServiceId())) {
//                            case SUBWAY://地铁行程信息处理
//                                carbonCredits += (baseTripClientDTO.getTripTotalFee() - 2) + 2;
//                                mileageSubway += baseTripClientDTO.getMileage();
//                                break;
//                            case BUS:   //公交行程信息处理
//                                carbonCredits += baseTripClientDTO.getTripTotalFee();
//                                mileageBus += baseTripClientDTO.getMileage();
//                                break;
//                            case BIKE:
//                            case WALK:
//                            case HighSpeedRail:
//                        }
//
//
//                    }
//                    //如果不是最后一页就往后面继续遍历
//                    if (pageNo++ <= pageTotal) {
//                        map.set("page_no", pageNo);
//                        respond = getRespond(URLEnum.TRIP_BASE_URL.getUrl(), map, httpHeaders, restTemplate);
//                        resultJson = respond.getJSONObject("result");
//                        baseTripListClientDTO = resultJson.getObject("page", BaseTripListClientDTO.class);
//
//                    }
//                }

                //统计从新增的里程和碳积分
                //增加可用碳积分
                carbonCreditsVO.setCarbonCreditsUnclaimed(carbonCreditsVO.getCarbonCreditsUnclaimed() + carbonCredits);
                //增加各个出行方式的里程
                carbonCreditsVO.setMileageBikeToday(carbonCreditsVO.getMileageBikeToday() + mileageBike);
                carbonCreditsVO.setMileageBikeTotal(carbonCreditsVO.getMileageBikeTotal() + mileageBike);
                carbonCreditsVO.setMileageBusToday(carbonCreditsVO.getMileageBusToday() + mileageBus);
                carbonCreditsVO.setMileageBusTotal(carbonCreditsVO.getMileageBusTotal() + mileageBus);
                carbonCreditsVO.setMileageSubwayToday(carbonCreditsVO.getMileageSubwayToday() + mileageSubway);
                carbonCreditsVO.setMileageSubwayTotal(carbonCreditsVO.getMileageSubwayTotal() + mileageSubway);
                carbonCreditsVO.setMileageWalkToday(carbonCreditsVO.getMileageWalkToday() + mileageWalk);
                carbonCreditsVO.setMileageWalkTotal(carbonCreditsVO.getMileageWalkTotal());

                System.out.println(carbonCreditsVO);
                carbonCreditsDao.updateCarbonCredits(carbonCreditsVO);

                CarbonCreditsDTO carbonCreditsDTO = new CarbonCreditsDTO(carbonCreditsVO.getCarbonCreditsUnclaimed(), carbonCreditsVO.getCarbonCreditsTotal(), carbonCreditsVO.getCarbonCreditsToday(),
                        carbonCreditsVO.getCarbonCreditsUseful(), carbonCreditsVO.getMileageSubwayToday(),
                        carbonCreditsVO.getMileageSubwayTotal(), carbonCreditsVO.getMileageBusToday(),
                        carbonCreditsVO.getMileageBusTotal(), carbonCreditsVO.getMileageWalkToday(),
                        carbonCreditsVO.getMileageWalkTotal(), carbonCreditsVO.getMileageBikeToday(),
                        carbonCreditsVO.getMileageBikeTotal());

                System.out.println(carbonCreditsDTO);
            }
    }


    @Test
    void contextLoads() {

    }

}
