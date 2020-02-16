package com.catlovers.carbon_credits.enumeration;

import com.alibaba.fastjson.JSONObject;

/**
 * 错误信息枚举类
 */
public enum StatusEnum {

    SUCCESS("0000", "处理成功"),
    PARAMETER_ERROR("0001", "参数错误"),
    FAILED("0002", "处理失败"),
    CARBON_CREDITS_ERROR("0018","碳积分不足"),
    DATE_NULL("0902", "数据为空"),
    UNKNOWN_ERROR("9999", "未知错误");


    private String coding;
    private String message;


    StatusEnum(String coding, String message) {
        this.coding = coding;
        this.message = message;
    }


    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static void getMessageJson(StatusEnum statusEnum, JSONObject jsonObject){

        jsonObject.put("msg_code", statusEnum.getCoding());
        jsonObject.put("msg_message", statusEnum.getMessage());

    }

}