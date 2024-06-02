package com.english.exception.user;

import com.english.exception.ServiceRuntimeException;

public class UnauthenticatedException extends ServiceRuntimeException
{
    public UnauthenticatedException()
    {
        super("请先登录", "[LOGIN] ");
    }
}
