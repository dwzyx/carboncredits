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

    private int couponBagId;
    private int userId;
    private int couponId;
    private String couponName;
    private int couponType;
    @JSONField(name = "use_type")
    private int useType;
    @JSONField(name = "coupon_cost")
    private int couponCost;
    @JSONField(name = "exchange_time", format="yyyy/MM/dd HH:mm:ss")
    private Date exchangeTime;
    private int expirationTime;
    private int sill;
    private int couponValue;
    private int couponUsed;

}
