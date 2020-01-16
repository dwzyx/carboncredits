package com.catlovers.carbon_credits.service;

import com.catlovers.carbon_credits.model.CommodityDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommodityService {
    List<CommodityDTO> getCommodityList(int pageNo, int pageSize);
}
