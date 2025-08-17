package com.market.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.market.entity.Admin;
import com.market.entity.Person;

import java.util.Date;

/**
 * @author hanmingzhi
 * @date 2022/8/14 18:47
 **/
public class TokenUtil {
    //token到期时间30分钟(根据需求改)
    private static final long EXPIRE_TIME =  30 * 60 * 1000;
    //密钥 (随机生成,可以从网上找到随机密钥生成器)
    private static final String TOKEN_SECRET = "MD9**+4MG^EG79RV+T?J87AI4NWQVT^&";

    /**
     * 生成token
     */
    public static String createToken(Person user) {
        String token = null;
        try {
            Date expireAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("auth0")
                    //存放数据
                    //.withClaim("email",user.getEmail())
                    //.withClaim("id",user.getId())
                    .withClaim("account", user.getAccount())
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException je) {
            //System.out.println("创建界面出错");
        }
        return token;
    }

    public static String createToken(Admin user) {
        String token = null;
        try {
            Date expireAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            token = JWT.create()
                    //发行人
                    .withIssuer("auth0")
                    //存放数据
                    //.withClaim("email",user.getEmail())
                    //.withClaim("id",user.getId())
                    .withClaim("account", user.getAdminAccount())
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException | JWTCreationException je) {
            //System.out.println("创建界面出错");
        }
        return token;
    }

    /**
     * token验证
     */
    public static Boolean checkToken(String token) {
        try {
            //创建token验证器

            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return false;
        }
        return true;
    }
}
