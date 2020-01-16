package com.catlovers.carbon_credits.enumeration;

/**
 * 错误信息枚举类
 */
public enum StatusEnum {

    SUCCESS("0000", "处理成功"),
    ILLEGAL_APPID("0001", "appid不合法"),
    SIGNATURE_AUTHENTICATION_FAILED("0002", "签名认证失败"),
    PERMISSION_DENIED("0003", "没有权限"),
    PARAMETER_ERROR("0004", "参数错误"),
    LOGIN_INVALID("0005", "登录失效，请重新登录"),
    INTERFACE_NOT_EXIST("0006", "接口不存在"),
    APPID_IS_NULL("0009", "app唯一标识为空"),
    FAILED("0010", "处理失败"),
    REQUIRED_PARAMETERS_EMPTY("0011", "必填参数不能为空"),
    REQUIRED_PARAMETERS_INCORRECT("0012", "必填参数不正确"),
    TERMINAL_ID_INCORRECT("0013", "终端识别码不正确"),
    WRONG_CITY_CHOICE("0014", "城市选择错误"),
    CITY_TEMPORARILY_CLOSED("0015", "该城市仅向内测用户开放，请选择其他城市"),
    FEATURES_TEMPORARILY_CLOSED("0016", "该功能仅向内测用户开放"),
    SYSTEM_BUSY("0017", "系统繁忙，请稍后重试"),
    SYSTEM_EXCEPTION("0901", "系统异常"),
    DATE_NULL("0902", "数据为空"),
    VERSION_NUMBER_NOT_SUPPORT("0088", "版本号不支持"),
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
    

}
