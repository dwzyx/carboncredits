package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class CarbonCreditsVO {
    private int userId;
    private int carbonCreditsTotal;
    private int carbonCreditsMonth;
    private int carbonCreditsToday;
    private int carbonCreditsUnclaimed;
    private int carbonCreditsUseful;
    private int mileageSubwayToday;
    private int mileageSubwayTotal;
    private int mileageBusToday;
    private int mileageBusTotal;
    private int mileageWalkToday;
    private int mileageWalkTotal;
    private int mileageBikeToday;
    private int mileageBikeTotal;
    @JSONField(format="yyyy/MM/dd HH:mm:ss")
    private Date updateTime;
    private int lastTripNo;
    private int lastPageNo;
    private int mileageSubwayLastMonth;
    private int mileageBusLastMonth;
    private int mileageWalkLastMonth;
    private int mileageBikeLastMonth;

}
