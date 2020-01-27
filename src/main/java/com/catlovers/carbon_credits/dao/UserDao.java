package com.catlovers.carbon_credits.dao;

import com.catlovers.carbon_credits.model.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {

    void updateUserCarbonCreditsMonth(@Param("userId") int userId, @Param("level") int level);

    UserVO getUserBasicById(int userId);

    UserVO getUserBasicByRank(int userId);
}
