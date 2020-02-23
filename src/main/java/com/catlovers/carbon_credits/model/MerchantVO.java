package com.catlovers.carbon_credits.model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MerchantVO {

    private int merchantId;
    private int userId;
    private String merchantPassword;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private String merchantName;
    private String merchantAddress;
    private String merchantIntroduce;
    private String merchantImage;
 //   private Date updateTime;
}
