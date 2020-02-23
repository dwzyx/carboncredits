package com.catlovers.carbon_credits.model.client;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Setter @Getter
@ToString
/**
 * 从八维通接口获取数据
 * 与传输关联的user实体类
 */
public class UserClientDTO {

    private int userId;
    private String userName;
    private int sex;
    private String mobilePhone;
    private String profession;
    private String idNo;
    private int idType;
    @JSONField(format="yyyy/MM/dd HH:mm:ss")
    private Date birthday;
    private String userImagePath;
    private String nickname;
    private int cityId;
    private String cityName;
    private int realNameReg;
    private int realNameAuth ;
    private int realNameChk;
    private int realNameOpen;


}
