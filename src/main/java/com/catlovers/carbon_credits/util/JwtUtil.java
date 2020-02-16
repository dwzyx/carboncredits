package com.catlovers.carbon_credits.util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class JwtUtil {
    //签名私钥
    public static final String key = "aimaoshadoudui*zyx";

    public static final int calendarField= Calendar.DATE;
    //签名的失效时间
    public static  final int calendarInterval=7;//过期时间,一周

    /**
     * 设置认证token
     */
    public String createJwt(int id, UUID uuid) {
        Date iatDate=new Date();
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(calendarField,calendarInterval);
        Date expiresDate=nowTime.getTime();//过期时间
        Algorithm algorithm = Algorithm.HMAC256(key);

        String token= JWT.create()
                .withClaim("id",id)
                .withClaim("UUID",uuid.toString())
                .withIssuedAt(iatDate)
                .withExpiresAt(expiresDate)
                .sign(algorithm);
        return token;
    }

    /**
    *  校验 token是否正确
    *
    * @param token  密钥
    * @return 是否正确
    */
    public static Map<String, Claim> verify(String token) {
        DecodedJWT jwt=null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            jwt = verifier.verify(token);
            return jwt.getClaims();
         } catch (Exception e) {
            return null;
         }
    }
    /**
    * 从 获取用户信息
    *
    * @return token中包含的用户Id
    */
    public static Integer getUserId(String token ) {
        try {
            System.out.println(token);
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim("id");
            claim.asInt();
            System.out.println("id"+claim.asString());
            return claim.asInt();
        }catch(Exception e){
            return null;
        }

    }

    /**
     * 从 获取用户信息
     *
     * @return token中包含的UUID
     */
    public static String getUID(String token){
        Map<String,Claim> map=JwtUtil.verify(token);
        return map.get("UUID").asString();
    }

}
