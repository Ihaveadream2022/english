package com.english.service;

import com.english.model.LoginUser;
import io.jsonwebtoken.Claims;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SessionService
{
    public final String secret = "QWERTYUIOPASDFGHJKLZXCVBNM123456";

    public Map<String, Object> createSession(LoginUser loginUser);

    public void deleteSession(LoginUser loginUser);

    public void refreshSession(LoginUser loginUser);

    public String jwtCreateToken(Map<String, Object> claims);

    public Claims jwtParseToken(String token);

    public LoginUser getLoginUser(HttpServletRequest request);

    public void updateLoginUser(LoginUser loginUser);
}