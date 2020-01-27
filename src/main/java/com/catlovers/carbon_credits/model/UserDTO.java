package com.catlovers.carbon_credits.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 前端需要user实体类
 */
@Setter @Getter
@ToString
public class UserDTO {

    private String nickname;            //昵称
    private String userImagePath;       //头像的图片路径
    private int cityId;                 //居住城市id
    private String cityName;            //居住的城市名称
    private int userLevel;              //用户等级
    private int signInNumber;           //签到天数
    private int carbonCreditsMonth;     //月碳积分
    private int userRank;               //用户排名
    private int isStore;                //是否是商家
    private int signInToday;            //今日是否签到

    public UserDTO() {
    }

    public UserDTO(String nickname, String userImagePath, int cityId, String cityName,
                   int userLevel, int signInNumber, int carbonCreditsMonth, int userRank,
                   int isStore, int signInToday) {
        this.nickname = nickname;
        this.userImagePath = userImagePath;
        this.cityId = cityId;
        this.cityName = cityName;
        this.userLevel = userLevel;
        this.signInNumber = signInNumber;
        this.carbonCreditsMonth = carbonCreditsMonth;
        this.userRank = userRank;
        this.isStore = isStore;
        this.signInToday = signInToday;
    }
}
