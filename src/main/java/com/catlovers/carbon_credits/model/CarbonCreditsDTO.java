package com.catlovers.carbon_credits.model;

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

    private int carbonCreditsUnclaimed;
    private int carbonCreditsTotal;
    private int carbonCreditsToday;
    private int carbonCreditsUseful;
    private int mileageSubwayToday;
    private int mileageSubwayTotal;
    private int mileageBusToday;
    private int mileageBusTotal;
    private int mileageWalkToday;
    private int mileageWalkTotal;

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

    private int mileageBikeToday;
    private int mileageBikeTotal;


}
