package com.catlovers.carbon_credits.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommodityRecordDTO {
    private int recordId;
    private int commodityId;
    private int userId;
    private String commodityPicture;
    private String commodityName;
    private int commodityType;
    private String commodityIntroduce;
    private int commodityPriceOriginal;
    private int commodityPrice;
    private int carbonCreditsNeed;
    private int deliverySchedule;
    private String expressNumber;
    private int deliveryId;
}
