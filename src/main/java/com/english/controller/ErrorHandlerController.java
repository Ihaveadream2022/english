package com.english.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorHandlerController implements ErrorController
{
    @RequestMapping(value = "/error")
    public void error(HttpServletRequest request)
    {
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");

        throw new RuntimeException(requestUri + " Not Found");
    }
}