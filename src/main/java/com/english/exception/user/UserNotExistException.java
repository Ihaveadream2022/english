package com.english.exception.user;

import com.english.exception.ServiceRuntimeException;

public class UserNotExistException extends ServiceRuntimeException
{
    public UserNotExistException()
    {
        super("用户名或密码错误", "[LOGIN] ");
    }
}
