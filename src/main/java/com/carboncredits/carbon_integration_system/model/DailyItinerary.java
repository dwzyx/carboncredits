package com.carboncredits.carbon_integration_system.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter
@ToString
@JsonFormat(timezone = "GMT+8", pattern = "yyyyMMddHHmmss")
public class DailyItinerary {

    private int id;
    private int userId;
    @JSONField(format="yyyy/MM/dd HH:mm:ss")
    private Date updateTime;
    private int subwayMilesDaily;
    private int busMilesDaily;
    private int bikeMilesDaily;
    private int stepMilesDaily;


}
