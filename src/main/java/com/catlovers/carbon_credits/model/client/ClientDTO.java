package com.catlovers.carbon_credits.model.client;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class ClientDTO<T> {

    private int errcode;
    private String errmsg;
    private T result;

}
