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
    private int userType;
    private int couponCost;
    private Date exchangeTime;
    private int expirationTime;
    private int sill;
    private int couponValue;
    private int couponUsed;

}
