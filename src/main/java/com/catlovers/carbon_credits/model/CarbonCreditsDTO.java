package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 传输碳积分对象
 */
@Setter @Getter
@ToString
public class CarbonCreditsDTO {

    @JSONField(name = "carbon_credits_unclaimed")
    private int carbonCreditsUnclaimed;
    @JSONField(name = "carbon_credits_Total")
    private int carbonCreditsTotal;
    @JSONField(name = "carbon_credits_today")
    private int carbonCreditsToday;
    @JSONField(name = "carbon_credits_useful")
    private int carbonCreditsUseful;
    @JSONField(name = "mileage_subway_today")
    private int mileageSubwayToday;
    @JSONField(name = "mileage_subway_total")
    private int mileageSubwayTotal;
    @JSONField(name = "mileage_bus_today")
    private int mileageBusToday;
    @JSONField(name = "mileage_bus_total")
    private int mileageBusTotal;
    @JSONField(name = "mileage_walk_today")
    private int mileageWalkToday;
    @JSONField(name = "mileage_walk_total")
    private int mileageWalkTotal;
    @JSONField(name = "mileage_bike_today")
    private int mileageBikeToday;
    @JSONField(name = "mileage_bike_total")
    private int mileageBikeTotal;

    public CarbonCreditsDTO() {
    }

    public CarbonCreditsDTO(int carbonCreditsUnclaimed, int carbonCreditsTotal, int carbonCreditsToday, int carbonCreditsUseful, int mileageSubwayToday, int mileageSubwayTotal, int mileageBusToday, int mileageBusTotal, int mileageWalkToday, int mileageWalkTotal, int mileageBikeToday, int mileageBikeTotal) {
        this.carbonCreditsUnclaimed = carbonCreditsUnclaimed;
        this.carbonCreditsTotal = carbonCreditsTotal;
        this.carbonCreditsToday = carbonCreditsToday;
        this.carbonCreditsUseful = carbonCreditsUseful;
        this.mileageSubwayToday = mileageSubwayToday;
        this.mileageSubwayTotal = mileageSubwayTotal;
        this.mileageBusToday = mileageBusToday;
        this.mileageBusTotal = mileageBusTotal;
        this.mileageWalkToday = mileageWalkToday;
        this.mileageWalkTotal = mileageWalkTotal;
        this.mileageBikeToday = mileageBikeToday;
        this.mileageBikeTotal = mileageBikeTotal;
    }



}
