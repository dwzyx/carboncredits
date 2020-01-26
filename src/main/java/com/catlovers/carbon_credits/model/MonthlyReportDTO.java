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

    private int CO2_reduction_this_month;   //该月二氧化碳减排量
    private int CO2_reduction_last_month;   //上月二氧化碳总排量
    private int user_rank_this_month;       //该月用户碳积分排名
    private int user_rank_last_month;       //上月用户碳积分排名
    private int mileage_total;              //总里程
    private int mileage_bus;                //公交车里程
    private int mileage_subways;            //地铁里程
    private int mileage_bike;               //自行车里程
    private int mileage_walk;               //步行里程


}
