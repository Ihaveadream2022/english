package com.english.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.english.model.JsonResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
public class UnauthenticatedHandler implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException
    {
        ObjectMapper om = new ObjectMapper();
        String jsonString = om.writeValueAsString(JsonResponse.error("请先登录"));

        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
		response.getWriter().print(jsonString);
    }
}
