package com.snowman.mymall.service;

import com.snowman.mymall.common.utils.CharUtil;
import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:09
 * @Version 1.0
 **/
@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * 30天过期
     */
    private final static Long EXPIRE = 3600 * 24 * 30L;


    /**
     * 通过token查询
     *
     * @param token
     * @return
     */
    public TokenEntity queryByToken(String token) {
        return tokenRepository.queryByToken(token);
    }

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    public Map<String, Object> createToken(long userId) {
        //生成一个token
        String token = CharUtil.getRandomString(32);
        //当前时间
        Date now = new Date();

        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        TokenEntity tokenEntity = queryByUserId(userId);
        if (tokenEntity == null) {
            tokenEntity = new TokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token(jpa主键存在则更新)
            save(tokenEntity);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", EXPIRE);

        return map;
    }

    /**
     * 通过userid查
     *
     * @param userId
     * @return
     */
    public TokenEntity queryByUserId(Long userId) {
        return tokenRepository.queryByUserId(userId);
    }

    /**
     * 保存
     *
     * @param tokenEntity
     */
    public void save(TokenEntity tokenEntity) {
        tokenRepository.save(tokenEntity);
    }
}
