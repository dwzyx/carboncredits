package com.catlovers.carbon_credits.dao;

import com.catlovers.carbon_credits.model.MonthlyReportVO;
import com.catlovers.carbon_credits.model.RankingDTO;
import com.catlovers.carbon_credits.model.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {

    void updateUserCarbonCreditsMonth(@Param("userId") int userId, @Param("level") int level,
                                      @Param("cityId") int cityId, @Param("nickname") String nickname,
                                      @Param("userImagePath") String userImagePath);

    UserVO getUserBasicById(int userId);

    UserVO getUserBasicByRank(int userId);

    List<RankingDTO> getRanks(@Param("userId") int userId, @Param("cityId") int cityId);

    void updateUserRankThisMonth(@Param("userRank") int userRank, @Param("userId") int userId);

    MonthlyReportVO getMonthlyReport(@Param("userId") int userId, @Param("year") int year, @Param("month") int month);
}
