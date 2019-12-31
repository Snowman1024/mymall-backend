package com.snowman.mymall.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.snowman.mymall.common.utils.CharUtil;
import com.snowman.mymall.common.utils.CommonUtil;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.common.config.AliInfo;
import com.snowman.mymall.common.config.WeixinInfo;
import com.snowman.mymall.entity.UserEntity;
import com.snowman.mymall.repository.UserRepository;
import com.snowman.mymall.service.AuthService;
import com.snowman.mymall.service.TokenService;
import com.snowman.mymall.service.UserService;
import com.snowman.mymall.vo.FullUserInfo;
import com.snowman.mymall.vo.LoginVO;
import com.snowman.mymall.vo.UserInfo;
import com.snowman.mymall.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
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
 * @Date 2019/10/12 10:11
 * @Version 1.0
 **/
@Service
public class AuthServiceImpl implements AuthService {

    private Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private WeixinInfo weixinInfo;

    @Autowired
    private AliInfo aliInfo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;


    /**
     * 通过手机，密码登录
     *
     * @param mobile
     * @param password
     * @return
     */
    @Override
    public Result login(String mobile, String password) {
        logger.info("通过手机，密码登录service开始：mobile:{},password:{}", mobile, password);
        //用户登录
        Integer userId = userService.login(mobile, password);
        //生成token
        Map<String, Object> map = tokenService.createToken(userId);
        logger.info("通过手机，密码登录service结束");
        return Result.ok(map);
    }

    /**
     * 微信登录
     *
     * @param loginVO
     * @param ip
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result loginByWeixin(LoginVO loginVO, String ip) {
        logger.info("微信登录service开始：loginVO:{},ip:{}", JSON.toJSONString(loginVO), ip);

        String code = loginVO.getCode();
        FullUserInfo fullUserInfo = loginVO.getFullUserInfo();
        UserInfo userInfo = fullUserInfo.getUserInfo();

        //获取openid
        String webAccessTokenhttps = weixinInfo.getWebAccessTokenHttps();
        String appId = weixinInfo.getAppId();
        String secret = weixinInfo.getSecret();
        String requestUrl = String.format(webAccessTokenhttps, appId, secret, code);
        logger.info("微信登录service,微信requestUrl：" + requestUrl);

        JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null == sessionData || StringUtils.isBlank(sessionData.getString("openid"))) {
            logger.error("微信登录service,sessionData是空或openid不存在");
            return Result.error("登录失败");
        }
        //验证用户信息完整性
        String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
        if (!fullUserInfo.getSignature().equals(sha1)) {
            logger.error("微信登录service,用户信息不完整");
            return Result.error("登录失败");
        }
        Date nowTime = new Date();
        String openId = sessionData.getString("openid");
        UserEntity userEntity = userRepository.queryByOpenId(openId);

        if (null == userEntity) {
            userEntity = new UserEntity();
            userEntity.setUserName("微信用户" + CharUtil.getRandomString(12));
            userEntity.setPassWord(openId);
            userEntity.setRegisterTime(nowTime);
            userEntity.setRegisterIp(ip);
            userEntity.setLastLoginIp(ip);
            userEntity.setLastLoginTime(nowTime);
            userEntity.setWeixinOpenId(openId);
            userEntity.setAvatar(userInfo.getAvatarUrl());
            //性别 0：未知、1：男、2：女
            userEntity.setGender(userInfo.getGender());
            userEntity.setNickName(userInfo.getNickName());
            userEntity = userRepository.save(userEntity);

        } else {
            userRepository.updateUser(userEntity.getUserId(), ip, nowTime);
        }

        Map<String, Object> tokenMap = tokenService.createToken(userEntity.getUserId());
        String token = (String) tokenMap.get("token");

        if (StringUtils.isBlank(token)) {
            logger.error("微信登录service,userId:{}生成d的token是空", userEntity.getUserId());
            return Result.error("登录失败");
        }

        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("token", token);
        resultObj.put("userInfo", userInfo);
        resultObj.put("userId", userEntity.getUserId());

        logger.info("微信登录service结束");
        return Result.ok(resultObj);
    }


    /**
     * 支付宝登录
     *
     * @param loginVO
     * @param ip
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result loginByAli(LoginVO loginVO, String ip) throws Exception {
        logger.info("支付宝登录service开始：loginVO:{},ip:{}", JSON.toJSONString(loginVO), ip);

        String code = loginVO.getCode();
        String webAccessTokenHttps = aliInfo.getWebAccessTokenHttps();
        String appId = aliInfo.getAppId();
        String privateKey = aliInfo.getPrivateKey();
        String pubKey = aliInfo.getPubKey();

        AlipayClient alipayClient = new DefaultAlipayClient(webAccessTokenHttps, appId, privateKey,
                "json", "UTF-8", pubKey, "RSA2");
        AlipaySystemOauthTokenRequest alipaySystemOauthTokenRequest = new AlipaySystemOauthTokenRequest();
        alipaySystemOauthTokenRequest.setCode(code);
        alipaySystemOauthTokenRequest.setGrantType("authorization_code");

        //code 换取token
        AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
        String accessToken = oauthTokenResponse.getAccessToken();

        //根据token获取用户头像、昵称等信息
        AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse userInfoResponse = alipayClient.execute(userInfoShareRequest, accessToken);

        Date nowTime = new Date();

        UserVO userVo = userService.queryByOpenId(userInfoResponse.getUserId());
        if (null == userVo) {
            userVo = new UserVO();
            userVo.setUserName("支付宝用户" + CharUtil.getRandomString(12));
            userVo.setPassWord(userInfoResponse.getUserId());
            userVo.setRegisterTime(nowTime);
            userVo.setRegisterIp(ip);
            userVo.setLastLoginIp(ip);
            userVo.setLastLoginTime(nowTime);
            userVo.setWeixinOpenId(userInfoResponse.getUserId());
            userVo.setAvatar(userInfoResponse.getAvatar());
            //性别 0：未知、1：男、2：女
            //F：女性；M：男性
            userVo.setGender("m".equalsIgnoreCase(userInfoResponse.getGender()) ? 1 : 0);
            userVo.setNickName(userInfoResponse.getNickName());

        } else {
            userVo.setLastLoginIp(ip);
            userVo.setLastLoginTime(nowTime);
        }
        userService.save(userVo);

        Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
        String token = (String) tokenMap.get("token");

        if (StringUtils.isBlank(token)) {
            logger.error("支付宝登录service,userId:{}生成的token不存在");
            return Result.error("登录失败");
        }

        Map<String, Object> resultObj = new HashMap<String, Object>();
        resultObj.put("token", token);
        resultObj.put("userInfo", userInfoResponse);
        resultObj.put("userId", userVo.getUserId());
        logger.info("支付宝登录service结束");
        return Result.ok(resultObj);
    }
}
