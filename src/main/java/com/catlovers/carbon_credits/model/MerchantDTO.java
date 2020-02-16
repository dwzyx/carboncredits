package com.catlovers.carbon_credits.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MerchantDTO {
    private int userId;
    private String merchantPhoneNumber;
    private String merchantEmail;
    private String merchantName;
    private String merchantAddress;
    private String merchantIntroduce;
    private String merchantImage;
}
