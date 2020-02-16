package com.catlovers.carbon_credits.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDelivery {
    private int deliveryId;
    private int userId;
    private String userName;
    private String userPhoneNumber;
    private String userAddress;
}
