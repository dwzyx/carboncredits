package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@ToString
public class UserVO {

    private int userId;
    private int userLevel;
    private int signInNumber;
    private int carbonCreditsMonth;
    private Date updateTime;
    private int signInToday;
    private int isStore;
    private int userRank;
    private String nickname;
    private int cityId;
    private String userImagePath;
    private int rankHighestLastMonth;
    private int rankHighestThisMonth;

}
