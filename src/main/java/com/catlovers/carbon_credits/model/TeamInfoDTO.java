package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class TeamInfoDTO {

    private int teamId;
    private String teamName;
    private int teamLeaderId;
    private int teamRank;
    private int teamCarbonCredits;
    private List<UserOfTeam> userList;

    public TeamInfoDTO() {
    }

    public TeamInfoDTO(int teamId, String teamName, int teamLeaderId,
                       int teamRank, int teamCarbonCredits,
                       List<UserOfTeam> userList) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamLeaderId = teamLeaderId;
        this.teamRank = teamRank;
        this.teamCarbonCredits = teamCarbonCredits;
        this.userList = userList;
    }

}
