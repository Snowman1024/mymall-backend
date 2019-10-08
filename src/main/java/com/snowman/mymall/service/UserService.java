package com.snowman.mymall.service;

import com.snowman.mymall.entity.UserEntity;
import com.snowman.mymall.repository.UserRepository;
import com.snowman.mymall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:53
 * @Version 1.0
 **/
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity queryByUserId(Long userId) {
        return userRepository.queryByUserId(userId);
    }
}
