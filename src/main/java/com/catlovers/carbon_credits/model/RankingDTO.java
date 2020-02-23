package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

@Setter @Getter
@ToString
public class RankingDTO {

    private String nickname;
    private String userImagePath;
    private int carbonCredits;
    private int userRank;

}
