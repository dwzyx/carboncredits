package com.catlovers.carbon_credits.dao;

import com.catlovers.carbon_credits.model.MerchantDTO;
import com.catlovers.carbon_credits.model.MerchantLoginDTO;
import com.catlovers.carbon_credits.model.MerchantVO;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantDao {
    MerchantVO ifExist(int userId);
    MerchantVO ifNameExist(String merchantName);
    MerchantVO ifPhoneNumberExist(String merchantPhoneNumber);
    MerchantVO ifEmailExist(String merchantEmail);
    MerchantVO login(MerchantLoginDTO merchantLoginDTO);
    int findMerchantIdByUserId(int userId);
    void signUp(MerchantVO merchantVO);

}
