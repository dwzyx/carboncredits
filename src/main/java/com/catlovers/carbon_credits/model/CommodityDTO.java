package com.catlovers.carbon_credits.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Setter @Getter
@ToString
public class CommodityDTO {
    private int commodity_id;
    private String commodity_picture;
    private String commodity_name;
    private String commodity_introduce;
    private double commodity_price_original;
    private double commodity_price;
    private int discount_useful;
    private List<Map<String, Object>> discount;

}
