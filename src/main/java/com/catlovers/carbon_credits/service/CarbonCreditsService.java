package com.catlovers.carbon_credits.service;


import com.catlovers.carbon_credits.model.CarBonCreditsDTO;
import org.springframework.stereotype.Service;

public interface CarbonCreditsService {
    CarBonCreditsDTO getCreditsInfo(int userId);
}
