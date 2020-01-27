package com.catlovers.carbon_credits.enumeration;

public enum ServiceIDEnum {
    SUBWAY("1"),
    BUS("2"),
    BIKE("3"),
    HighSpeedRail("4"),
    WALK("5");

    private String value;

    public final String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ServiceIDEnum(String value) {
        this.value = value;
    }

    public static ServiceIDEnum getByValue(String value) {
        for (ServiceIDEnum service : values()) {
            if (service.getValue().equals(value)) {
                return service;
            }
        }
        throw new IndexOutOfBoundsException();
    }

}
