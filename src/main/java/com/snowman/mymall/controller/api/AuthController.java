package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.utils.CharUtil;
import com.snowman.mymall.common.utils.CommonUtil;
import com.snowman.mymall.common.utils.IpUtil;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.common.validator.Assert;
import com.snowman.mymall.common.vo.FullUserInfo;
import com.snowman.mymall.common.vo.LoginVO;
import com.snowman.mymall.common.vo.UserInfo;
import com.snowman.mymall.common.vo.UserVO;
import com.snowman.mymall.config.AliInfo;
import com.snowman.mymall.config.WeixinInfo;
import com.snowman.mymall.service.TokenService;
import com.snowman.mymall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 17:33
 * @Version 1.0
 **/
@Api(tags = "登录授权接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WeixinInfo weixinInfo;

    @Autowired
    private AliInfo aliInfo;

    /**
     * 登录接口
     *
     * @param mobile
     * @param password
     * @return
     */
    @ApiOperation(value = "登录接口")
    @IgnoreAuth
    @PostMapping("/login")
    public Result login(String mobile, String password) {
        Assert.isBlank(mobile, "手机号不能为空");
        Assert.isBlank(password, "密码不能为空");
        //用户登录
        long userId = userService.login(mobile, password);
        //生成token
        Map<String, Object> map = tokenService.createToken(userId);

        return Result.ok(map);
    }


    /**
     * 微信登录
     *
     * @param loginVO
     * @param request
     * @return
     */
    @ApiOperation(value = "微信登录")
    @IgnoreAuth
    @PostMapping("/login_by_weixin")
    public Object loginByWeixin(LoginVO loginVO, HttpServletRequest request) {

        if (null == loginVO || null == loginVO.getFullUserInfo()
                || null == loginVO.getFullUserInfo().getUserInfo()) {
            return Result.error("登录失败");
        }
        Map<String, Object> resultObj = new HashMap<String, Object>();

        String code = loginVO.getCode();
        FullUserInfo fullUserInfo = loginVO.getFullUserInfo();
        UserInfo userInfo = fullUserInfo.getUserInfo();

        //获取openid
        String webAccessTokenhttps = weixinInfo.getWebAccessTokenHttps();
        String appId = weixinInfo.getAppId();
        String secret = weixinInfo.getSecret();
        String requestUrl = String.format(webAccessTokenhttps, appId, secret, code);
        logger.info(">>>weixin requestUrl：" + requestUrl);

        JSONObject sessionData = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null == sessionData || StringUtils.isBlank(sessionData.getString("openid"))) {
            return Result.error("登录失败");
        }
        //验证用户信息完整性
        String sha1 = CommonUtil.getSha1(fullUserInfo.getRawData() + sessionData.getString("session_key"));
        if (!fullUserInfo.getSignature().equals(sha1)) {
            return Result.error("登录失败");
        }
        Date nowTime = new Date();
        String openId = sessionData.getString("openid");
        UserVO userVo = userService.queryByOpenId(openId);
        String ip = IpUtil.getClientIp(request);
        if (null == userVo) {
            userVo = new UserVO();
            userVo.setUserName("微信用户" + CharUtil.getRandomString(12));
            userVo.setPassWord(openId);
            userVo.setRegisterTime(nowTime);
            userVo.setRegisterIp(ip);
            userVo.setLastLoginIp(ip);
            userVo.setLastLoginTime(nowTime);
            userVo.setWeixinOpenId(openId);
            userVo.setAvatar(userInfo.getAvatarUrl());
            //性别 0：未知、1：男、2：女
            userVo.setGender(userInfo.getGender());
            userVo.setNickName(userInfo.getNickName());
            userService.save(userVo);
        } else {
            userVo.setLastLoginIp(ip);
            userVo.setLastLoginTime(nowTime);
            userService.save(userVo);
        }

        Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
        String token = (String) tokenMap.get("token");

        if (StringUtils.isBlank(token)) {
            return Result.error("登录失败");
        }

        resultObj.put("token", token);
        resultObj.put("userInfo", userInfo);
        resultObj.put("userId", userVo.getUserId());
        return Result.ok(resultObj);
    }

    /**
     * 支付宝登录
     * @param loginVO
     * @param httpServletRequest
     * @return
     */
    @ApiOperation(value = "支付宝登录")
    @IgnoreAuth
    @PostMapping("/login_by_ali")
    public Object login_by_ali(LoginVO loginVO, HttpServletRequest httpServletRequest) {
        if (null == loginVO) {
            return Result.error("登录失败");
        }

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
        try {
            //code 换取token
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(alipaySystemOauthTokenRequest);
            String accessToken = oauthTokenResponse.getAccessToken();

            //根据token获取用户头像、昵称等信息
            AlipayUserInfoShareRequest userInfoShareRequest = new AlipayUserInfoShareRequest();
            AlipayUserInfoShareResponse userInfoResponse = alipayClient.execute(userInfoShareRequest, accessToken);

            Date nowTime = new Date();
            String ip = IpUtil.getClientIp(httpServletRequest);

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
                userService.save(userVo);
            } else {
                userVo.setLastLoginIp(ip);
                userVo.setLastLoginTime(nowTime);
                userService.save(userVo);
            }

            Map<String, Object> tokenMap = tokenService.createToken(userVo.getUserId());
            String token = (String) tokenMap.get("token");

            if (StringUtils.isBlank(token)) {
                return Result.error("登录失败");
            }

            Map<String, Object> resultObj = new HashMap<String, Object>();
            resultObj.put("token", token);
            resultObj.put("userInfo", userInfoResponse);
            resultObj.put("userId", userVo.getUserId());
            return Result.ok(resultObj);
        } catch (AlipayApiException e) {
            return Result.error("登录失败");
        }
    }
}
