package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter
@ToString
public class CouponBagDTO {

    @JSONField(name = "coupon_id")
    private int couponId;
    @JSONField(name = "coupon_name")
    private String couponName;
    @JSONField(name = "user_store_id")
    private int userStoreId;
    @JSONField(name = "coupon_type")
    private int couponType;
    @JSONField(name = "user_type")
    private int userType;
    @JSONField(name = "coupon_cost")
    private int couponCost;
    @JSONField(name = "expiration_time")
    private Date expirationTime;
    private int sill;
    private int value;
    private int count;

}
