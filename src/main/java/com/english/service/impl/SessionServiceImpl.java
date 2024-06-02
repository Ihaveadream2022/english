package com.english.service.impl;

import com.english.constant.CacheConstant;
import com.english.model.LoginUser;
import com.english.service.RedisService;
import com.english.service.SessionService;
import com.english.util.StringUtil;
import com.english.util.uuid.UUID;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SessionServiceImpl implements SessionService
{
    public final Integer expireIn = 31536000;

    private static final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    @Autowired
    RedisService redisService;

    /**
     * Create Session
     *
     * @param loginUser
     *
     * @return
     */
    public Map<String, Object> createSession(LoginUser loginUser)
    {
        String uuid = UUID.fastUUID().toString();
        String sessionId = CacheConstant.SESSION_ID + uuid;
        loginUser.setSessionId(sessionId);
        loginUser.setExpireAt(System.currentTimeMillis() + (expireIn.intValue() * 1000L));
        redisService.setValue(sessionId, loginUser, expireIn, TimeUnit.SECONDS);

        Map<String, Object> claims = new HashMap<>();
        claims.put("session_id", sessionId);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("access_token", jwtCreateToken(claims));
        tokenMap.put("expires_in", expireIn);

        return tokenMap;
    }

    public String jwtCreateToken(Map<String, Object> claims)
    {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public Claims jwtParseToken(String token)
    {
        return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
    }

    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        if (!StringUtil.isEmpty(token) && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
        }

        return token;
    }

    public LoginUser getLoginUser(HttpServletRequest request)
    {
        String token = getToken(request);
        if (!StringUtil.isEmpty(token)) {
            try {
                /* 解析有效载荷 */
                Claims claims = jwtParseToken(token);
                String sessionId = (String) claims.get("session_id");
                return redisService.getValue(sessionId, new ParameterizedTypeReference<LoginUser>(){});
            } catch (Exception e) {
                serviceLogger.info("获取用户信息异常'{}'", e.getMessage());
                return null;
            }
        }
        return null;
    }

    /**
     * Update session after update user column in mysql
     *
     * @param loginUser
     */
    public void updateLoginUser(LoginUser loginUser)
    {
        if (loginUser != null && loginUser.getSessionId() != null) {
            loginUser.setExpireAt(System.currentTimeMillis() + (expireIn.intValue() * 1000L));
            redisService.setValue(loginUser.getSessionId(), loginUser, expireIn, TimeUnit.SECONDS);
        }
    }

    /**
     * Refresh session's ttl
     *
     * @param loginUser
     */
    public void refreshSession(LoginUser loginUser)
    {
        Long expireAt = loginUser.getExpireAt();
        if (expireAt - System.currentTimeMillis() <= 1200000) {
            loginUser.setExpireAt(System.currentTimeMillis() + (expireIn.intValue() * 1000L));
            redisService.setValue(loginUser.getSessionId(), loginUser, expireIn, TimeUnit.SECONDS);
        }
    }

    /**
     * Delete session from redis
     *
     * @param loginUser
     */
    public void deleteSession(LoginUser loginUser)
    {
        String sessionId = loginUser.getSessionId();
        if (sessionId != null) {
            redisService.delete(sessionId);
        }
    }
}