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

    private int CO2ReductionThisMonth;   //该月二氧化碳减排量
    private int CO2ReductionLastMonth;   //上月二氧化碳总排量
    private int userRankThisMonth;       //该月用户碳积分排名
    private int userRankLastMonth;       //上月用户碳积分排名
    private int mileageTotal;              //总里程
    private int mileageBus;                //公交车里程
    private int mileageSubways;            //地铁里程
    private int mileageBike;               //自行车里程
    private int mileageWalk;               //步行里程


}
