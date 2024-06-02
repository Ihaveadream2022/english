package com.english.service.impl;

import com.english.constant.CacheConstant;
import com.english.entity.User;
import com.english.exception.user.UserPassRetryLimitException;
import com.english.exception.user.UserPasswordMatchException;
import com.english.security.AuthenticationContext;
import com.english.service.PasswordService;
import com.english.service.RedisService;
import com.english.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PasswordServiceImpl implements PasswordService
{
    @Autowired
    private RedisService redisService;

    public void validate(User user)
    {
        Authentication usernamePasswordAuthenticationToken = AuthenticationContext.getContext();
        String username = usernamePasswordAuthenticationToken.getName();
        String password = usernamePasswordAuthenticationToken.getCredentials().toString();
        String cacheKey = CacheConstant.PASS_ERROR_THRESHOLD + username;
        Integer retryCount = redisService.getValue(cacheKey, new ParameterizedTypeReference<Integer>(){});
        if (retryCount == null) {
            retryCount = 0;
        }
        if (retryCount >= 5) {
            throw new UserPassRetryLimitException();
        }
        if (!SecurityUtil.matchesPassword(password, user.getPassword())) {
            retryCount = retryCount + 1;
            // 一分钟内错误5次
            redisService.setValue(cacheKey, retryCount, 1, TimeUnit.MINUTES);
            throw new UserPasswordMatchException();
        } else {
            redisService.delete(cacheKey);
        }
    }
}
