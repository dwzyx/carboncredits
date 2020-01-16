package com.catlovers.carbon_credits.service;

import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO getUserInfo(int userId);

    List<RankingDTO> getRankingList(int userId, int cityId);

}
