package com.catlovers.carbon_credits.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MerchantLoginDTO {

    private int userId;
    private String merchantPassword;

}
