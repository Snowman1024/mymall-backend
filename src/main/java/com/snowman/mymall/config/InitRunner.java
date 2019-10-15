package com.snowman.mymall.config;

import com.snowman.mymall.common.Constant;
import com.snowman.mymall.common.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 16:57
 * @Version 1.0
 **/
@Configuration
public class InitRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(InitRunner.class);

    @Autowired
    private RedisService redisService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try{
            redisService.remove(Constant.NEW_GOODS_KEY);
            redisService.remove(Constant.BANNER_KEY);

        }catch (Exception e){
            logger.error("InitRunner error:{}",e);
        }
    }
}
