package com.catlovers.carbon_credits.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * TeamInfoDTO子对象
 */
@Setter @Getter
@ToString
public class UserOfTeam {

    private int userId;
    private String nickname;
    private int userCarbonCredits;
    private int userLevel;
    private int userRank;

}
