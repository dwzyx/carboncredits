package com.catlovers.carbon_credits.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SecondHandGoodDTO {
    int goodId;
    int buyerId;
    int sellerId;
    String goodName;
    int goodCash;
    int goodCarbonCredits;
    String goodCity;
    String goodIntroduce;
    int deliveryId;
}
