package com.english.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.english.model.JsonResponse;
import com.english.model.LoginUser;
import com.english.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class LogoutSuccessHandlerImpl implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler
{
    @Autowired
    private SessionService sessionService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        LoginUser loginUser = sessionService.getLoginUser(request);
        if (loginUser != null) {
            sessionService.deleteSession(loginUser);
        }
        ObjectMapper om = new ObjectMapper();
        String jsonString = om.writeValueAsString(JsonResponse.success("You have successfully logged out"));
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(jsonString);
    }
}
