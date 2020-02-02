package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class CommodityVO {

    private int commodityId;
    private String commodityPicture;
    private String commodityName;
    private int commodityType;
    private String commodityIntroduce;
    private int commodityPriceOriginal;
    private int commodityPrice;
    private int carbonCreditsNeed;
    private int remaining;

}
