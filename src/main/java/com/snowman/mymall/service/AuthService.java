package com.snowman.mymall.service;

import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.vo.LoginVO;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 10:10
 * @Version 1.0
 **/
public interface AuthService {

    public Result login(String mobile, String password);

    public Result loginByWeixin(LoginVO loginVO, String ip);

    public Result loginByAli(LoginVO loginVO, String ip) throws Exception;
}
