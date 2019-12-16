package com.carboncredits.carbon_integration_system.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Setter @Getter
@ToString
public class UserCarbonCredits implements Serializable {

    private int userID;
    private int signInNumber;
    private int carbonCreditsTotal;
    private Date updateTime;
    private int carbonCreditsLastMonth;

}
