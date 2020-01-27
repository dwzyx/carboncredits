package com.catlovers.carbon_credits.enumeration;

public enum CityIdEnum {
    CHINA("全国", 86),
    TIANJING("天津", 12),
    WUXI("无锡", 3202),
    SUZHOU("苏州", 3205),
    FUZHOU("福州", 3501);

    private String name;
    private int value;

    CityIdEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static String getCityName(int value){
        for (CityIdEnum city : values()) {
            if (city.getValue() == value) {
                return city.getName();
            }
        }
        throw new IndexOutOfBoundsException();
    }
}
