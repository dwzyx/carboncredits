package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 传输碳积分对象
 */
@Setter @Getter
@ToString
public class CarBonCreditsDTO {

    private int carbonCreditsTotal;
    private int carbonCreditsToday;
    private int carbonCreditsUseful;
    private int mileageSubwayToday;
    private int mileageSubwayTotal;
    private int mileageBusToday;
    private int mileageBusTotal;
    private int mileageWalkToday;
    private int mileageWalkTotal;
    private int mileageBikeToday;
    private int mileageBikeTotal;


}
