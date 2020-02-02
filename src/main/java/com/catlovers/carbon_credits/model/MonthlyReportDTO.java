package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 月度出行报告所需数据
 */
@Setter @Getter
@ToString
public class MonthlyReportDTO {

    @JSONField(name = "CO2_reduction_this_month")
    private int CO2ReductionThisMonth;     //该月二氧化碳减排量
    @JSONField(name = "CO2_reduction_last_month")
    private int CO2ReductionLastMonth;     //上月二氧化碳减排量
    @JSONField(name = "user_rank_this_month")
    private int userRankThisMonth;         //该月用户碳积分排名
    @JSONField(name = "user_rank_last_month")
    private int userRankLastMonth;         //上月用户碳积分排名
    @JSONField(name = "mileage_total")
    private int mileageTotal;              //总里程
    @JSONField(name = "mileage_bus")
    private int mileageBus;                //公交车里程
    @JSONField(name = "mileage_subway")
    private int mileageSubway;            //地铁里程
    @JSONField(name = "mileage_bike")
    private int mileageBike;               //自行车里程
    @JSONField(name = "mileage_walk")
    private int mileageWalk;               //步行里程

}
