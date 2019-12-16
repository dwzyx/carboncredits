package com.carboncredits.carbon_integration_system.mapper;

import com.carboncredits.carbon_integration_system.model.DailyItinerary;
import com.carboncredits.carbon_integration_system.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    User findUserById(int id);

    DailyItinerary getUserDailyItinerary(int userId);

    int getUserCarbonCredits(int userId);
}
