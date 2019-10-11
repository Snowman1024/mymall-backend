package com.snowman.mymall.service;

import com.snowman.mymall.common.exception.ServiceException;
import com.snowman.mymall.common.validator.Assert;
import com.snowman.mymall.common.vo.UserVO;
import com.snowman.mymall.entity.UserEntity;
import com.snowman.mymall.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
