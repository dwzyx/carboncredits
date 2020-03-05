package com.catlovers.carbon_credits.service.impl;

        import com.alibaba.fastjson.JSONObject;
        import com.catlovers.carbon_credits.dao.CarbonCreditsDao;
        import com.catlovers.carbon_credits.dao.CommodityDao;
        import com.catlovers.carbon_credits.dao.UserDao;
        import com.catlovers.carbon_credits.enumeration.StatusEnum;
        import com.catlovers.carbon_credits.model.*;
        import com.catlovers.carbon_credits.service.CommodityService;
        import org.springframework.data.crossstore.ChangeSetPersister;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;
        import org.springframework.transaction.interceptor.TransactionAspectSupport;

        import java.util.HashMap;
        import java.util.List;

@Service
@Transactional(rollbackFor=Exception.class)
public class CommodityServiceImpl implements CommodityService {

    private final CommodityDao commodityDao;
    private final UserDao userDao;
    private final CarbonCreditsDao carbonCreditsDao;

    public CommodityServiceImpl(CommodityDao commodityDao,UserDao userDao,CarbonCreditsDao carbonCreditsDao) {
        this.commodityDao = commodityDao;
        this.userDao = userDao;
        this.carbonCreditsDao = carbonCreditsDao;
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
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
    public JSONObject getCouponInfoById(int pageNo, int pageSize, int goodType, int merchantId) {
        JSONObject jsonObject = new JSONObject();
        List<CouponInfoDTO> couponInfoDTOList = null;
        int pageTotal = -1;

        try{
            couponInfoDTOList = commodityDao.getCouponInfoById((pageNo-1)*pageSize, pageSize, goodType, merchantId);
            pageTotal = (commodityDao.getCouponCountTotalById((pageNo-1)*pageSize, pageSize, goodType, merchantId)+pageSize-1)/pageSize;

            jsonObject.put("msg_code", StatusEnum.SUCCESS.getCoding());
            jsonObject.put("msg_message", StatusEnum.SUCCESS.getMessage());
        } catch (NullPointerException e){
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
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
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR, jsonObject);
        } catch (Exception e){
            e.printStackTrace();
            jsonObject.put("msg_code", StatusEnum.FAILED.getCoding());
            jsonObject.put("msg_message", StatusEnum.FAILED.getMessage());
        }

        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public JSONObject exchangeCoupon(int couponId, int userId) {
        JSONObject jsonObject = new JSONObject();
        try {
            UserVO userVO = userDao.getUserBasicById(userId);
            if(userVO==null){
                throw new NullPointerException();
            }

            CarbonCreditsVO carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(userId);
            int creditsTotal = carbonCreditsVO.getCarbonCreditsUseful();

            CouponInfoDTO couponInfoDTO= commodityDao.getCouponForBag(couponId);
            int needCredits = couponInfoDTO.getCouponCost();

            if(creditsTotal<needCredits){
                StatusEnum.getMessageJson(StatusEnum.CARBON_CREDITS_ERROR,jsonObject);
                return jsonObject;
            }else {
                carbonCreditsVO.setCarbonCreditsUseful(creditsTotal-needCredits);
                carbonCreditsDao.updateCarbonCredits(carbonCreditsVO);

                commodityDao.exchangeCoupon(couponId);
                java.util.Date  date=new java.util.Date();
                java.sql.Date  data1=new java.sql.Date(date.getTime());
                CouponBagDTO couponBagDTO =
                        new CouponBagDTO(0,userId,couponId,couponInfoDTO.getCouponName(),couponInfoDTO.getCouponType(),couponInfoDTO.getUseType(),couponInfoDTO.getCouponCost(),data1,couponInfoDTO.getExpirationTime(),couponInfoDTO.getSill(),couponInfoDTO.getValue(),0);
                commodityDao.addToCouponBag(couponBagDTO);
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);}
        }catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (IndexOutOfBoundsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }
        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public JSONObject exchangeCommodity(int commodityId, int userId,int deliveryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            CarbonCreditsVO carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(userId);
            int creditsTotal = carbonCreditsVO.getCarbonCreditsUseful();

            CommodityDTO commodityDTO = commodityDao.getCommodityForRecord(commodityId);
            int needCredits = commodityDTO.getCarbonCreditsNeed();

            if(creditsTotal<needCredits){
                StatusEnum.getMessageJson(StatusEnum.CARBON_CREDITS_ERROR,jsonObject);
                return jsonObject;
            }else {
                carbonCreditsVO.setCarbonCreditsUseful(creditsTotal-needCredits);
                carbonCreditsDao.updateCarbonCredits(carbonCreditsVO);

                commodityDao.exchangeCommodity(commodityId);
                CommodityRecordDTO commodityRecordDTO = new CommodityRecordDTO(0, commodityId, userId, commodityDTO.getCommodityPicture(), commodityDTO.getCommodityName(), commodityDTO.getCommodityType(), commodityDTO.getCommodityIntroduce(), commodityDTO.getCommodityPriceOriginal(), commodityDTO.getCommodityPrice(), commodityDTO.getCarbonCreditsNeed(), 0, null,deliveryId);
                commodityDao.addToCommodityRecord(commodityRecordDTO);
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);}
        }catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (IndexOutOfBoundsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }
        return jsonObject;
    }

    @Override
    public JSONObject getSecondHandGood(int pageNo, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        List<SecondHandGoodDTO> secondHandGoodDTOList = null;
        int pageTotal = -1;

        try{
            secondHandGoodDTOList = commodityDao.getSecondHandGoodInfo((pageNo-1)*pageSize, pageSize);
            pageTotal = (commodityDao.getSecondHandGoodCountTotal((pageNo-1)*pageSize, pageSize)+pageSize-1)/pageSize;
            if(secondHandGoodDTOList.isEmpty()){
                jsonObject.put("msg_code", StatusEnum.DATE_NULL.getCoding());
                jsonObject.put("msg_message", StatusEnum.DATE_NULL.getMessage());
            }
            else {
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
            }

        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("secondHandGood", secondHandGoodDTOList);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject getSecondHandGoodFromSeller(int pageNo, int pageSize, int sellerId) {
        JSONObject jsonObject = new JSONObject();
        List<SecondHandGoodDTO> secondHandGoodDTOList = null;
        int pageTotal = -1;

        try{
            secondHandGoodDTOList = commodityDao.getSecondHandGoodInfoFromSeller((pageNo-1)*pageSize, pageSize,sellerId);
            pageTotal = (commodityDao.getSecondHandGoodCountTotalFromSeller((pageNo-1)*pageSize, pageSize,sellerId)+pageSize-1)/pageSize;
            if(secondHandGoodDTOList.isEmpty()){
                jsonObject.put("msg_code", StatusEnum.DATE_NULL.getCoding());
                jsonObject.put("msg_message", StatusEnum.DATE_NULL.getMessage());
            }
            else {
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
            }

        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("secondHandGood", secondHandGoodDTOList);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject getSecondHandGoodFromBuyer(int pageNo, int pageSize, int buyerId) {
        JSONObject jsonObject = new JSONObject();
        List<SecondHandGoodDTO> secondHandGoodDTOList = null;
        int pageTotal = -1;

        try{
            secondHandGoodDTOList = commodityDao.getSecondHandGoodInfoFromBuyer((pageNo-1)*pageSize, pageSize,buyerId);
            pageTotal = (commodityDao.getSecondHandGoodCountTotalFromBuyer((pageNo-1)*pageSize, pageSize,buyerId)+pageSize-1)/pageSize;
            if(secondHandGoodDTOList.isEmpty()){
                jsonObject.put("msg_code", StatusEnum.DATE_NULL.getCoding());
                jsonObject.put("msg_message", StatusEnum.DATE_NULL.getMessage());
            }
            else {
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
            }

        } catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            e.printStackTrace();
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("secondHandGood", secondHandGoodDTOList);
        jsonObject.put("page_total", pageTotal);
        jsonObject.put("result", resultMap);
        return jsonObject;
    }

    @Override
    public JSONObject addSecondHandGood(SecondHandGoodDTO secondHandGoodDTO) {
        JSONObject jsonObject = new JSONObject();
        try {
            int i = commodityDao.addSecondHandGood(secondHandGoodDTO);
            if(i==1){
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
            }
            else {
                StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
            }
        }catch(Exception e){
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }finally {
            return jsonObject;
        }

    }

    @Override
    public JSONObject deleteSecondHandGood(int goodId) {
        JSONObject jsonObject = new JSONObject();
        try {
            SecondHandGoodDTO secondHandGoodDTO = commodityDao.getSecondHandGood(goodId);
            if(secondHandGoodDTO==null){
                StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
            }
            if(secondHandGoodDTO.getBuyerId()==0){
                commodityDao.deleteSecondHandGood(goodId);
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);

            }
        }catch(Exception e){
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }


        return jsonObject;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public JSONObject buySecondHandGood(int buyerId, int goodId, int sellerId,int deliveryId) {
        JSONObject jsonObject = new JSONObject();
        try {
            SecondHandGoodDTO secondHandGoodDTO = commodityDao.getSecondHandGood(goodId);
            CarbonCreditsVO carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(buyerId);
            int creditsTotal = carbonCreditsVO.getCarbonCreditsUseful();
            int needCredits = secondHandGoodDTO.getGoodCarbonCredits();
            if(creditsTotal<needCredits){
                StatusEnum.getMessageJson(StatusEnum.CARBON_CREDITS_ERROR,jsonObject);
                return jsonObject;
            }else {
                carbonCreditsVO.setCarbonCreditsUseful(creditsTotal-needCredits);
                carbonCreditsDao.updateCarbonCredits(carbonCreditsVO);
                //个人商家的碳积分++
                carbonCreditsVO = carbonCreditsDao.getUserAllCarbonCredits(sellerId);
                int formerCredits = carbonCreditsVO.getCarbonCreditsUseful();
                int formerTotal = carbonCreditsVO.getCarbonCreditsTotal();
                carbonCreditsVO.setCarbonCreditsUseful(formerCredits+needCredits);
                carbonCreditsVO.setCarbonCreditsTotal(formerTotal+needCredits);
                carbonCreditsDao.updateCarbonCredits(carbonCreditsVO);

                commodityDao.updateSecondHandGood(buyerId,deliveryId,goodId);
                StatusEnum.getMessageJson(StatusEnum.SUCCESS,jsonObject);
            }

        }catch (NullPointerException e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        } catch (IndexOutOfBoundsException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.PARAMETER_ERROR,jsonObject);
        }catch(Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚失误
            StatusEnum.getMessageJson(StatusEnum.FAILED,jsonObject);
        }
        return jsonObject;

    }
}
