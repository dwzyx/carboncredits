package com.catlovers.carbon_credits.enumeration;

public enum URLEnum {

    USER_INFO_URL ("http://59.110.174.204:7280/v1.0/api/app/user/getUserInfo"),//获取用户信息
    TRIP_BASE_URL ("http://59.110.174.204:7280/v1.0/api/app/trip/baseTriplist"),//获取行程列表
    TRIP_GET_BASE_URL("http://59.110.174.204:7280/v1.0/api/app//app/trip/getBaseTrip"),//获取单个行程信息
    MESSAGE_URL ("http://59.110.174.204:7280/v1.0/api/app/message/push"),//获取消息列表
    MESSAGE_GET_BY_TRIP_URL ("http://59.110.174.204:7280/v1.0/api/app/message/list/by/type"),//
    USER_PORTRAIT_URL("http://59.110.174.204:7280/v1.0/api/portrait/getUserPortrait");//用户画像信息

    private String url;

    URLEnum(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
