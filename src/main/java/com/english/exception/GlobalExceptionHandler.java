package com.english.exception;

import com.english.model.JsonResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger serviceLogger = LoggerFactory.getLogger("SERVICE");

    private static final Logger frameworkLogger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceRuntimeException.class)
    public JsonResponse handleServiceRuntimeException(ServiceRuntimeException e, HttpServletRequest request)
    {
        serviceLogger.info(e.getServiceType() + e.getMessage() + " 请求地址'{}'", request.getRequestURI());

        return JsonResponse.error(e.getMessage());
    }

    /**
     * 业务参数格式异常. Jakarta EE Validation 或 Spring Validation
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JsonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request)
    {
        serviceLogger.info("[ARGUMENT] " + e.getBindingResult().getFieldError().getDefaultMessage() + " 请求地址'{}'", request.getRequestURI());

        return JsonResponse.error(e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public JsonResponse handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        frameworkLogger.error("请求地址'{}', 发生运行时异常.", request.getRequestURI(), e);

        return JsonResponse.error("系统异常");
    }

    /**
     * 检查异常
     */
    @ExceptionHandler(Exception.class)
    public JsonResponse handleException(Exception e, HttpServletRequest request)
    {
        frameworkLogger.error("请求地址'{}', 发生检查异常.", request.getRequestURI(), e);

        return JsonResponse.error("系统异常");
    }
}