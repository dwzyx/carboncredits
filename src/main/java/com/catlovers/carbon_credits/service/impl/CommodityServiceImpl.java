package com.catlovers.carbon_credits.service.impl;

import com.catlovers.carbon_credits.dao.CommodityDao;
import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {

    private final CommodityDao commodityDao;

    public CommodityServiceImpl(CommodityDao commodityDao) {
        this.commodityDao = commodityDao;
    }

    @Override
    public List<CommodityDTO> getCommodityList(int pageNo, int pageSize) {
        List<CommodityDTO> result = new ArrayList<>();
        List<Map<String, Object>> discountList = new ArrayList<>();

        CommodityDTO commodityDTO = new CommodityDTO();

        Map<String, Object> disCountMap = new HashMap<>();

        disCountMap.put("type", 1);
        disCountMap.put("sill", 100);
        disCountMap.put("value", 100);

        discountList.add(disCountMap);

        commodityDTO.setCommodity_id(1);
        commodityDTO.setCommodity_introduce("green home");
        commodityDTO.setCommodity_picture("https://i.loli.net/2020/01/16/q65HkVWYveuXQzP.jpg");
        commodityDTO.setCommodity_price(1);
        commodityDTO.setCommodity_name("守护地球");
        commodityDTO.setCommodity_price_original(1000000);
        commodityDTO.setDiscount_useful(1);
        commodityDTO.setDiscount(discountList);
        result.add(commodityDTO);


        return result;
    }
}
