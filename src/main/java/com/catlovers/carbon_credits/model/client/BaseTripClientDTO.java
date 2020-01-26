package com.catlovers.carbon_credits.model.client;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class BaseTripClientDTO {

    private String tripNo;
    private Long consumerId;
    private int cityId;
    private String serviceId;
    private String inStationId;
    private String inStationName;
    private String inLineId;
    private String inLineName;
    private String inTime;
    private String outStationId;
    private String outStationName;
    private String outLineId;
    private String outLineName;
    private String outTime;
    private int mileage;
    private String status;
    private int tripTotalFee;
    private int tripTotalActualFee;
    private int amount;
    private int baseFee;
    private int payResult;
    private int payType;
    private int payTypeH5;
    private String payTime;
    private String billId;



}
