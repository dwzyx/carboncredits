package com.catlovers.carbon_credits.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDelivery {
    @JSONField(name = "delivery_id")
    private int deliveryId;
    @JSONField(name = "user_id")
    private int userId;
    @JSONField(name = "user_name")
    private String userName;
    @JSONField(name = "user_phone_number")
    private String userPhoneNumber;
    @JSONField(name = "user_address")
    private String userAddress;
}
