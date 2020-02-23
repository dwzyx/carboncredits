package com.catlovers.carbon_credits.realm;

import com.catlovers.carbon_credits.filter.JWTToken;
import com.catlovers.carbon_credits.model.MerchantVO;
import com.catlovers.carbon_credits.service.MerchantService;
import com.catlovers.carbon_credits.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class MerchantRealm extends AuthorizingRealm {



    @Autowired
    MerchantService merchantService;

    @Override
    public void setName(String name){
        super.setName("merchantRealm");
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }


    @Override
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

/**
 *认证方法
 *参数:传递的用户名密码
 **/
@Override
    protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken auth) throws AuthenticationException {
    //        //1.获取登录的用户名密码(token)
    //        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
    //        String userId = upToken.getUsername();
    //        String password = new String(upToken.getPassword());
    //        //2.根据用户名查询数据库
    //        MerchantVO merchantVO = merchantService .findById (userId);
    //        //3.判断用户是否存在或者密码是否一致
    //
    //    password = new Md5Hash(password, merchantVO.getMerchantName(), 3).toString();
    //        if(merchantVO != null && merchantVO.getMerchantPassword().equals(password)) {
    //            //4.如果一致返回安全数据
    //            //构造方法:安全数据，密码，realm域名
    //            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(merchantVO , merchantVO.getMerchantPassword() , this.getName());
    //            return info;
    //
    //        }
    //    //5.不一致，返回null (抛出异常)
    //        return null;
    System.out.println("realm");
    String token = (String) auth.getCredentials();
    // 解密获得username，用于和数据库进行对比
    try {
        int userId = JwtUtil.getUserId(token);
        String UUID = JwtUtil.getUID(token);
//        System.out.println("userid:" + userId);
//        System.out.println("UUID" + UUID);

        String getUUID = merchantService.login(userId, null);
//        System.out.println("getUUID:"+getUUID);

        if(getUUID == null) {
            return null;
        }
        if(getUUID .equals(UUID)) {
            System.out.println("true");
            merchantService.loginAnyway(userId,getUUID);
            return new SimpleAuthenticationInfo(token, token, "merchantRealm");
        }
        System.out.println("nooo");
        return null;
    }catch(Exception e){
        throw new AuthenticationException("token认证失败！");
    }



}

 }
