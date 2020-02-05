package com.catlovers.carbon_credits.dao;

import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.model.CouponInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommodityDao {
    List<CommodityDTO> getCommodityInfo(@Param("i") int i, @Param("pageSize") int pageSize);

    int getCommodityCountTotal(@Param("i") int i, @Param("pageSize") int pageSize);

    List<CouponInfoDTO> getCouponInfo(@Param("i") int i, @Param("pageSize") int pageSize, @Param("goodType") int goodType);

    List<CouponInfoDTO> getCouponInfoById(@Param("i") int i, @Param("pageSize") int pageSize, @Param("goodType") int goodType,int merchantId);

    int getCouponCountTotal(@Param("i") int i, @Param("pageSize") int pageSize, @Param("goodType") int goodType);

    int getCouponCountTotalById(@Param("i") int i, @Param("pageSize") int pageSize, @Param("goodType") int goodType,int merchantId);

    List<CommodityDTO> searchCommodity(@Param("i") int i, @Param("pageSize") int pageSize,
                                       @Param("commodityName") String commodityName, @Param("commodityType") int commodityType);

    int searchedCommodityCountTotal(@Param("i") int i, @Param("pageSize") int pageSize,
                                    @Param("commodityName") String commodityName, @Param("commodityType") int commodityType);

    List<CouponInfoDTO> searchCoupon(@Param("i") int i, @Param("pageSize") int pageSize,
                                     @Param("goodType") int goodType, @Param("useStoreId") int useStoreId,
                                     @Param("couponName") String couponName, @Param("couponType") int couponType,
                                     @Param("useType") int useType);

    int searchCouponCountTotal(@Param("i") int i, @Param("pageSize") int pageSize,
                               @Param("goodType") int goodType, @Param("useStoreId") int useStoreId,
                               @Param("couponName") String couponName, @Param("couponType") int couponType,
                               @Param("useType") int useType);

    void addCommodity(CommodityDTO commodity);

    void addCoupon(CouponInfoDTO coupon);

    void deleteCommodity(int commodityId);

    void deleteCoupon(int couponId);

    void updateCommodity(CommodityDTO commodity);

    void updateCoupon(CouponInfoDTO coupon);
}
