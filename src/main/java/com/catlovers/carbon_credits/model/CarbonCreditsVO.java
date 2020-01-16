package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class CarbonCreditsVO {

    private int userId;
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
    private Date updatedTime;

}
