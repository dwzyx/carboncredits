package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter
@ToString
public class CouponInfoDTO {

    @JSONField(name = "coupon_id")
    private int couponId;
    @JSONField(name = "coupon_name")
    private String couponName;
    @JSONField(name = "user_store_id")
    private int userStoreId;
    @JSONField(name = "coupon__type")
    private int couponType;
    @JSONField(name = "user_type")
    private int userType;
    @JSONField(name = "coupon_cost")
    private int couponCost;
    private int sill;
    private int value;
    @JSONField(name = "expiration_time")
    private int expirationTime;
    @JSONField(name = "remaining")
    private int remaining;

}
