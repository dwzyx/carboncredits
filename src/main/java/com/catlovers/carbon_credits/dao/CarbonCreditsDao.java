package com.catlovers.carbon_credits.dao;

import com.catlovers.carbon_credits.model.CarbonCreditsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarbonCreditsDao {


    CarbonCreditsVO getUserAllCarbonCredits(int userId);

    void updateCarbonCredits(@Param("carbonCreditsVO")CarbonCreditsVO carbonCreditsVO);


}
