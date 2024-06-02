package com.english.exception.user;

import com.english.exception.ServiceRuntimeException;

public class UserPassRetryLimitException extends ServiceRuntimeException
{
    public UserPassRetryLimitException()
    {
        super("密码错误次数达到上限", "[LOGIN] ");
    }
}
