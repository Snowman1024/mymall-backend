package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.token.JwtTokenUtil;
import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.repository.TokenRepository;
import com.snowman.mymall.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:19
 * @Version 1.0
 **/
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 通过token查询
     *
     * @param token
     * @return
     */
    @Override
    public TokenEntity queryByToken(String token) {
        return tokenRepository.queryByToken(token);
    }

    /**
     * 生成token
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> createToken(Integer userId) {

        //生成一个token
        String token = jwtTokenUtil.generateToken(userId);
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + JwtTokenUtil.VALID_TIME * 1000);

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
        map.put("expire", JwtTokenUtil.VALID_TIME);

        return map;
    }

    /**
     * 通过userid查
     *
     * @param userId
     * @return
     */
    @Override
    public TokenEntity queryByUserId(Integer userId) {
        return tokenRepository.queryByUserId(userId);
    }

    /**
     * 保存
     *
     * @param tokenEntity
     */
    @Override
    public void save(TokenEntity tokenEntity) {
        tokenRepository.save(tokenEntity);
    }
}
