package com.catlovers.carbon_credits.model;

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
public class UserMessageDTO {

    private int user_id;
    private String user_name;
    private int sex;
    private String mobile_phone;
    private String profession;
    private String id_no;
    private int id_type;
    @JSONField(format="yyyy/MM/dd HH:mm:ss")
    private Date birthday;
    private String user_image_path;
    private String nickname;
    private int city_id;
    private String city_name;
    private int real_name_reg;
    private int real_name_auth ;
    private int real_name_chk;
    private int real_name_open;


}
