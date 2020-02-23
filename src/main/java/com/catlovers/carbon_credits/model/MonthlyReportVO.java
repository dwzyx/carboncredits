package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class MonthlyReportVO {

    private int userId;
    private int mileageSubway;
    private int mileageWalk;
    private int mileageBike;
    private int mileageBus;
    private Date updateMonth;
    private int userRank;

}
