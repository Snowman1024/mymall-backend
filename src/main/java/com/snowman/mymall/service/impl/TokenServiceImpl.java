package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.token.JwtTokenUtil;
import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.repository.TokenRepository;
import com.snowman.mymall.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);

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
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> createToken(Integer userId) {
        logger.info("生成tokenService开始,userId:{}", userId);

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

            tokenRepository.save(tokenEntity);
        } else {
            tokenRepository.updateToken(tokenEntity.getUserId(), token, now, expireTime);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", JwtTokenUtil.VALID_TIME);

        logger.info("生成tokenService结束,map:{}", map);
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

}
