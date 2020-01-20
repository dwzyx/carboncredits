package com.catlovers.carbon_credits.model;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

@Setter @Getter
@ToString
public class DisCountDTO {

    private int card_id;
    private String card_name;
    private int type;
    private int sill;
    private int value;
    private Date expiration_time;

}
