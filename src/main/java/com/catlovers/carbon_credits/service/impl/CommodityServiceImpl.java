package com.catlovers.carbon_credits.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.catlovers.carbon_credits.dao.CommodityDao;
import com.catlovers.carbon_credits.enumeration.StatusEnum;
import com.catlovers.carbon_credits.model.CommodityDTO;
import com.catlovers.carbon_credits.model.CouponInfoDTO;
import com.catlovers.carbon_credits.service.CommodityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CommodityServiceImpl implements CommodityService {

    private final CommodityDao commodityDao;

    public CommodityServiceImpl(CommodityDao commodityDao) {
        this.commodityDao = commodityDao;
    }

    @Override
    public JSONObject getCommodityInfo(int pageNo, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        List<CommodityDTO> commodityDTOList = null;
        int pageTotal = -1;

        try{
            commodityDTOList = commodityDao.getCommodityInfo((pageNo-1)*pageSize, pageSize);
            pageTotal = (commodityDao.getCommodityCountTotal((pageNo-1)*pageSize, pageSize)+pageSize-1)/pageSize;

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("commodity", commodityDTOList);
        resultMap.put("coupon", null);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject getCouponInfo(int pageNo, int pageSize, int goodType) {
        JSONObject jsonObject = new JSONObject();
        List<CouponInfoDTO> couponInfoDTOList = null;
        int pageTotal = -1;

        try{
            couponInfoDTOList = commodityDao.getCouponInfo((pageNo-1)*pageSize, pageSize, goodType);
            pageTotal = (commodityDao.getCouponCountTotal((pageNo-1)*pageSize, pageSize, goodType)+pageSize-1)/pageSize;

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("commodity", null);
        resultMap.put("coupon", couponInfoDTOList);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject searchCommodity(JSONObject commodity, int pageNo, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        List<CommodityDTO> commodityDTOList = null;
        int pageTotal = -1;

        try{
            String commodityName = (String) commodity.get("commodity_name");
            int commodityType = (int) commodity.get("commodity_type");
            commodityDTOList = commodityDao.searchCommodity((pageNo-1)*pageSize, pageSize, commodityName, commodityType);
            pageTotal = (commodityDao.searchedCommodityCountTotal((pageNo-1)*pageSize, pageSize, commodityName, commodityType)
                    +pageSize-1)/pageSize;

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }


        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("commodity", commodityDTOList);
        resultMap.put("coupon", null);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject searchCoupon(JSONObject coupon, int pageNo, int pageSize, int goodType) {
        JSONObject jsonObject = new JSONObject();
        List<CouponInfoDTO> couponInfoDTOList = null;
        int pageTotal = -1;

        try{
            int useStoreId = (int) coupon.get("use_store_id");
            String couponName = (String) coupon.get("coupon_name");
            int couponType = (int)coupon.get("coupon_type");
            int useType = (int)coupon.get("use_type");
            couponInfoDTOList = commodityDao.searchCoupon((pageNo-1)*pageSize, pageSize, goodType,
                    useStoreId, couponName, couponType, useType);
            pageTotal = (commodityDao.searchCouponCountTotal((pageNo-1)*pageSize, pageSize, goodType,
                    useStoreId, couponName, couponType, useType)+pageSize-1)/pageSize;

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("commodity", null);
        resultMap.put("coupon", couponInfoDTOList);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject addCommodity(CommodityDTO commodity) {
        JSONObject jsonObject = new JSONObject();

        try{
            commodityDao.addCommodity(commodity);

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }

    @Override
    public JSONObject addCoupon(CouponInfoDTO coupon) {
        JSONObject jsonObject = new JSONObject();

        try{
            commodityDao.addCoupon(coupon);

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }

    @Override
    public JSONObject deleteCommodity(int commodityId) {
        JSONObject jsonObject = new JSONObject();

        try{
            commodityDao.deleteCommodity(commodityId);

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }

    @Override
    public JSONObject deleteCoupon(int couponId) {
        JSONObject jsonObject = new JSONObject();

        try{
            commodityDao.deleteCoupon(couponId);

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }

    @Override
    public JSONObject updateCommodity(CommodityDTO commodity) {
        JSONObject jsonObject = new JSONObject();

        try{
            commodityDao.updateCommodity(commodity);

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }

    @Override
    public JSONObject updateCoupon(CouponInfoDTO coupon) {
        JSONObject jsonObject = new JSONObject();

        try{
            commodityDao.updateCoupon(coupon);

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getCoding());
            jsonObject.put("msg_message", StatusEnum.REQUIRED_PARAMETERS_INCORRECT.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }
}
