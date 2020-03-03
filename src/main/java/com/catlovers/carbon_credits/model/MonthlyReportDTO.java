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

    @JSONField(name = "CO2_reduction")
    private int CO2Reduction;     //该月二氧化碳减排量
    @JSONField(name = "user_rank")
    private int userRank;         //用户碳积分排名
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
    private int month;                     //月份
    private int year;                      //年份

    public MonthlyReportDTO() {
    }

    public MonthlyReportDTO(int CO2Reduction, int userRank, int mileageTotal, int mileageBus,
                            int mileageSubway, int mileageBike, int mileageWalk, int month, int year) {
        this.CO2Reduction = CO2Reduction;
        this.userRank = userRank;
        this.mileageTotal = mileageTotal;
        this.mileageBus = mileageBus;
        this.mileageSubway = mileageSubway;
        this.mileageBike = mileageBike;
        this.mileageWalk = mileageWalk;
        this.month = month;
        this.year = year;
    }
}
