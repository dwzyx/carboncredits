package com.catlovers.carbon_credits.model.client;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
public class UserPortraitClientDTO {

    private String BWT_CPL_INDM_GEND_S;     //行为性别：F:女性、M:男性
    private String BWT_CPL_INDM_NATI;       //国籍：中国、其他
    private String BWT_CPL_INDM_AGE_C5;     //年龄段
    private String WT_CPL_INDM_AGE_DEC;     //出生年段
    private String BWT_ABM_LBS_IDADRS;      //籍贯地址
    private String BWT_ABM_LBS_PHNADRS;     //手机号归属地
    private String BWT_CID_MODEL;           //手机型号
    private String BWT_CPL_DVM_ISP;         //运营商
    private String BWT_CPL_DVM_OS;          //操作系统
    private String BWT_TH_SUB_ATCW;         //周平均乘车次数
    private String BWT_TH_SUB_ATCM;         //月均乘车次数
    private String BWT_TH_SUB_ATP;          //平均票价
    private String BWT_TH_SUB_FREP;         //是否常旅客
    private String BWT_TH_SUB_REGP;         //是否规律旅客
    private String BWT_TH_SUB_HOMEP;        //居住地站点
    private String BWT_TH_SUB_WORKP;        //工作站站点
    private String BWT_TH_SUB_OFP;          //常去站点

}
