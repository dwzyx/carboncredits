package com.catlovers.carbon_credits.model.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter @Getter
@ToString
public class BaseTripListClientDTO {

    private int totalCount;
    private int pageNo;
    private int pageSize;
    private int totalPage;
    private List<BaseTripClientDTO> rows;


}
