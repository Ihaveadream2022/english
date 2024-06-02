package com.english.exception.user;

import com.english.exception.ServiceRuntimeException;

public class UserPasswordMatchException extends ServiceRuntimeException
{
    public UserPasswordMatchException()
    {
        super("用户名或密码错误", "[LOGIN] ");
    }
}
