package com.carboncredits.carbon_integration_system.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *用户的基本信息
 */
@Setter @Getter
@ToString
public class User implements Serializable {

    private int id;
    private String username;
    private int sex;
    private String mobilePhone;
    private String nickname;
    private String profession;
    private int idNo;
    private int idType;
    private Date birthday;
    private String userImagePath;
    private int cityId;
    private String cityName;
    private int realNameReg;
    private int realNameAuth;
    private int realNameChk;
    private int realNameOpen;

}
