package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class CommodityDTO {

    @JSONField(name = "commodity_id")
    private int commodityId;
    @JSONField(name = "commodity_picture")
    private String commodityPicture;
    @JSONField(name = "commodity_name")
    private String commodityName;
    @JSONField(name = "commodity_type")
    private int commodityType;
    @JSONField(name = "commodity_introduce")
    private String commodityIntroduce;
    @JSONField(name = "commodity_price_original")
    private int commodityPriceOriginal;
    @JSONField(name = "commodity_price")
    private int commodityPrice;
    @JSONField(name = "carbon_credits_need")
    private int carbonCreditsNeed;
    private int remaining;

}
