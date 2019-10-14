package com.snowman.mymall.service;

import com.snowman.mymall.vo.UserVO;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:53
 * @Version 1.0
 **/
public interface UserService {

    public UserVO queryByUserId(Integer userId);

    public UserVO queryByMobile(String mobile);

    public Integer login(String mobile, String password);

    public UserVO queryByOpenId(String openId);

    public void save(UserVO userVo);

}
