package com.snowman.mymall.service;

import com.snowman.mymall.common.token.JwtTokenUtil;
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
public interface TokenService {

    public TokenEntity queryByToken(String token);

    public Map<String, Object> createToken(Integer userId);

    public TokenEntity queryByUserId(Integer userId);

}
