package com.catlovers.carbon_credits.model;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDTO {
    private int merchantId;
    private int userId;
    private String merchantPassword;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private String merchantName;
    private String merchantAddress;
    private String merchantIntroduce;
    private String merchantImage;


}
