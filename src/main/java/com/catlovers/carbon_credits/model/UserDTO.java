package com.catlovers.carbon_credits.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class UserDTO {

    private String nickname;
    private String user_image_path;
    private int city_id;
    private String city_name;
    private int user_level;
    private int sign_in_number;
    private int carbon_credits_month;
    private int user_rank;


}
