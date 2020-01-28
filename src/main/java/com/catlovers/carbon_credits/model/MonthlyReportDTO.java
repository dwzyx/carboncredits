package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 月度出行报告所需数据
 */
@Setter @Getter
@ToString
public class MonthlyReportDTO {

    private int CO2ReductionThisMonth;     //该月二氧化碳减排量
    private int CO2ReductionLastMonth;     //上月二氧化碳减排量
    private int userRankThisMonth;         //该月用户碳积分排名
    private int userRankLastMonth;         //上月用户碳积分排名
    private int mileageTotal;              //总里程
    private int mileageBus;                //公交车里程
    private int mileageSubway;            //地铁里程
    private int mileageBike;               //自行车里程
    private int mileageWalk;               //步行里程

    public MonthlyReportDTO(int CO2ReductionThisMonth, int CO2ReductionLastMonth,
                            int userRankThisMonth, int userRankLastMonth, int mileageTotal,
                            int mileageBus, int mileageSubway, int mileageBike, int mileageWalk) {
        this.CO2ReductionThisMonth = CO2ReductionThisMonth;
        this.CO2ReductionLastMonth = CO2ReductionLastMonth;
        this.userRankThisMonth = userRankThisMonth;
        this.userRankLastMonth = userRankLastMonth;
        this.mileageTotal = mileageTotal;
        this.mileageBus = mileageBus;
        this.mileageSubway = mileageSubway;
        this.mileageBike = mileageBike;
        this.mileageWalk = mileageWalk;
    }

    public MonthlyReportDTO() {
    }

    public MonthlyReportDTO(int mileageBike, int mileageBus, int mileageSubway,
                            int mileageWalk, int mileageTotal, int co2Reduction) {
        this.mileageTotal = mileageTotal;
        this.mileageBus = mileageBus;
        this.mileageSubway = mileageSubway;
        this.mileageBike = mileageBike;
        this.mileageWalk = mileageWalk;
    }
}
