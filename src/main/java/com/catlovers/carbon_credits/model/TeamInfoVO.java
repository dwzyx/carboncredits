package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class TeamInfoVO {
    private int teamId;
    private String teamName;
    private int teamLeaderId;
    private int teamRank;
    private int teamCarbonCredits;
}
