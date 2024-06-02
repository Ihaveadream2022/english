package com.english.service.impl;

import com.english.constant.CacheConstant;
import com.english.entity.User;
import com.english.exception.ServiceRuntimeException;
import com.english.exception.captcha.CaptchaErrorException;
import com.english.exception.captcha.CaptchaExpiredException;
import com.english.model.LoginUser;
import com.english.security.AuthenticationContext;
import com.english.service.SessionService;
import com.english.service.LogService;
import com.english.service.RedisService;
import com.english.service.UserService;
import com.english.util.StringUtil;
import com.english.util.ip.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class LogServiceImpl implements LogService
{
    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Autowired
    SessionService sessionService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Map<String, Object> login(String username, String password, String code, String uuid)
    {
        checkCaptcha(code, uuid);

        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContext.setContext(authenticationToken);
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (InternalAuthenticationServiceException e) {
            throw new ServiceRuntimeException(e.getMessage());
        } finally {
            AuthenticationContext.clearContext();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        afterLogin(loginUser);

        return sessionService.createSession(loginUser);
    }

    public void checkCaptcha(String code, String uuid)
    {
        String cacheKey = CacheConstant.CAPTCHA_KEY + StringUtil.valueDefault(uuid, "");
        String captchaValue = redisService.getValue(cacheKey, new ParameterizedTypeReference<String>() {});
        redisService.delete(cacheKey);
        if (captchaValue == null) {
            throw new CaptchaExpiredException();
        }
        if (!code.equalsIgnoreCase(captchaValue)) {
            throw new CaptchaErrorException();
        }
    }

    public void afterLogin(LoginUser loginUser)
    {
        User user = new User();
        user.setId(loginUser.getUserId());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(IpUtil.getIp());
        userService.updateAfterLogin(user);
    }
}
