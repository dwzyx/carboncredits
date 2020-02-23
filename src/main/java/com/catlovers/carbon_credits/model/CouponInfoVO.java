package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter @Getter
@ToString
public class CouponInfoVO {

    private int couponId;
    private String couponName;
    private int userStoreId;
    private int couponType;
    private int userType;
    private int couponCost;
    private int sill;
    private int value;
    private Date expirationTime;
    private int remaining;

}
