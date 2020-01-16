package com.catlovers.carbon_credits.enumeration;

public enum URLEnum {

    USER_INFO_URL ("http://59.110.174.204:7280/v1.0/api/app/user/getUserInfo"),
    TRIP_BASE_URL ("http://59.110.174.204:7280/v1.0/api/app/trip/baseTriplist"),
    TRIP_GET_BASE_URL("http://59.110.174.204:7280/v1.0/api/app//app/trip/getBaseTrip"),
    MESSAGE_URL ("http://59.110.174.204:7280/v1.0/api/app/message/push"),
    MESSAGE_GET_BY_TRIP_URL ("http://59.110.174.204:7280/v1.0/api/app/message/list/by/type");

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
