package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandGoodDTO {
    @JSONField(name = "good_id")
    int goodId;
    @JSONField(name = "buyer_id")
    int buyerId;
    @JSONField(name = "seller_id")
    int sellerId;
    @JSONField(name = "good_name")
    String goodName;
    @JSONField(name = "good_cash")
    int goodCash;
    @JSONField(name = "good_carbon_credits")
    int goodCarbonCredits;
    @JSONField(name = "good_city")
    String goodCity;
    @JSONField(name = "good_introduce")
    String goodIntroduce;
    @JSONField(name = "delivery_id")
    int deliveryId;
}
