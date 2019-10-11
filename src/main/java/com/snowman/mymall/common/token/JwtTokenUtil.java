package com.snowman.mymall.common.token;

import com.snowman.mymall.entity.TokenEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 13:26
 * @Version 1.0
 **/
@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = 8022581301096173338L;

    private Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    // 密钥
    private final String SECRET = "MyMall@2019!QAZ";
    // token有效时间 30天过期
    public static final long VALID_TIME = 3600 * 24 * 30L;


    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public String generateToken(Integer userId) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("sub", userVO.getUserName());
        claims.put("jti", userId);

        claims.put("created", new Date());
        return generateToken(claims);
    }

    /**
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + VALID_TIME * 1000);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    /**
     * 从token中获取数据声明
     *
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 从token中获取用户id
     *
     * @param token
     * @return
     */
    public Integer getUserIdFromToken(String token) {
        Integer userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            String userIdStr = claims.getId();
            if (StringUtils.isNotBlank(userIdStr)) {
                userId = Integer.valueOf(userIdStr);
            }
        } catch (Exception e) {
            logger.error("从token中获取用户id异常:{}", e);
            userId = null;
        }
        return userId;
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 验证token
     *
     * @param token
     * @param tokenEntity
     * @return
     */
    public Boolean validateToken(String token, TokenEntity tokenEntity) {
        Integer userId = getUserIdFromToken(token);
        if (null == userId) {
            return false;
        }
        return (userId.equals(tokenEntity.getUserId()) && !isTokenExpired(token));
    }

    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    /**
     * 向已有token添加新属性，并更换新token
     *
     * @param token
     * @param addClaims
     * @return
     */
    public String getNewToken(String token, Map<String, Object> addClaims) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        for (String key : addClaims.keySet()) {
            claims.put(key, addClaims.get(key));
        }
        claims.put("created", new Date());
        return generateToken(claims);
    }
}
