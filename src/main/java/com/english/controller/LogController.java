package com.english.controller;

import com.english.model.JsonResponse;
import com.english.model.LoginUser;
import com.english.model.request.LoginRequestBody;
import com.english.service.SessionService;
import com.english.service.impl.LogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class LogController
{
    @Autowired
    LogServiceImpl logService;

    @Autowired
    private SessionService sessionService;

    @PostMapping("/login")
    public JsonResponse login(@Validated @RequestBody LoginRequestBody loginRequestBody)
    {
        Map<String, Object> token = logService.login(
            loginRequestBody.getUsername(),
            loginRequestBody.getPassword(),
            loginRequestBody.getCode(),
            loginRequestBody.getUuid()
        );

        return JsonResponse.success(token);
    }
}
