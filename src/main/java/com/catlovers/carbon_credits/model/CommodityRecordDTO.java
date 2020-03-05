package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommodityRecordDTO {
    @JSONField(name = "record_id")
    private int recordId;
    @JSONField(name = "commodity_id")
    private int commodityId;
    @JSONField(name = "user_id")
    private int userId;
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
    @JSONField(name = "delivery_schedule")
    private int deliverySchedule;
    @JSONField(name = "express_number")
    private String expressNumber;
    @JSONField(name = "delivery_id")
    private int deliveryId;
}
