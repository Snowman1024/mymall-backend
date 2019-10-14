package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.exception.ServiceException;
import com.snowman.mymall.vo.UserVO;
import com.snowman.mymall.entity.UserEntity;
import com.snowman.mymall.repository.UserRepository;
import com.snowman.mymall.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:20
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过id查用户
     *
     * @param userId
     * @return
     */
    @Override
    public UserVO queryByUserId(Integer userId) {
        UserEntity entity = userRepository.queryByUserId(userId);
        if (entity == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopier copier = BeanCopier.create(UserEntity.class, UserVO.class, false);
        copier.copy(entity, userVO, null);
        return userVO;

    }

    /**
     * 通过手机号查用户
     *
     * @param mobile
     * @return
     */
    @Override
    public UserVO queryByMobile(String mobile) {
        UserEntity entity = userRepository.queryByMobile(mobile);
        if (entity == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopier copier = BeanCopier.create(UserEntity.class, UserVO.class, false);
        copier.copy(entity, userVO, null);
        return userVO;
    }

    /**
     * 登陆
     *
     * @param mobile
     * @param password
     * @return
     */
    @Override
    public Integer login(String mobile, String password) {
        UserVO user = queryByMobile(mobile);

        if (null == user ||
                !user.getPassWord().equals(DigestUtils.sha256Hex(password))) {
            throw new ServiceException("手机号或密码错误");
        }

        return user.getUserId();
    }

    /**
     * 通过openId查用户
     *
     * @param openId
     * @return
     */
    @Override
    public UserVO queryByOpenId(String openId) {
        UserEntity entity = userRepository.queryByOpenId(openId);
        if (entity == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanCopier copier = BeanCopier.create(UserEntity.class, UserVO.class, false);
        copier.copy(entity, userVO, null);
        return userVO;
    }

    /**
     * 保存
     *
     * @param userVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserVO userVo) {
        UserEntity entity = new UserEntity();
        BeanCopier entityCopier = BeanCopier.create(UserVO.class, UserEntity.class, false);
        entityCopier.copy(userVo, entity, null);
        userRepository.save(entity);
    }
}
