package com.snowman.mymall.service;

import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TokenEntity queryByToken(String token) {
        return tokenRepository.queryByToken(token);
    }
}
