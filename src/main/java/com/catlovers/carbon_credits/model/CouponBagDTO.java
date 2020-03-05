package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Setter @Getter
@ToString @AllArgsConstructor
public class CouponBagDTO {

    @JSONField(name = "coupon_bag_id")
    private int couponBagId;
    @JSONField(name = "user_id")
    private int userId;
    @JSONField(name = "coupon_id")
    private int couponId;
    @JSONField(name = "coupon_name")
    private String couponName;
    @JSONField(name = "coupon_type")
    private int couponType;
    @JSONField(name = "use_type")
    private int useType;
    @JSONField(name = "coupon_cost")
    private int couponCost;
    @JSONField(name = "exchange_time", format="yyyy/MM/dd HH:mm:ss")
    private Date exchangeTime;
    @JSONField(name = "expiration_time")
    private int expirationTime;
    @JSONField(name = "sill")
    private int sill;
    @JSONField(name = "coupon_value")
    private int couponValue;
    @JSONField(name = "coupon_used")
    private int couponUsed;

}
