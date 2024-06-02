package com.english.exception.user;

import com.english.exception.ServiceRuntimeException;

public class UserStatusException extends ServiceRuntimeException
{
    public UserStatusException()
    {
        super("用户状态异常", "[LOGIN] ");
    }
}
