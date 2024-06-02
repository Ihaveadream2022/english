package com.english.exception.captcha;

import com.english.exception.ServiceRuntimeException;

public class CaptchaErrorException extends ServiceRuntimeException
{
    public CaptchaErrorException()
    {
        super("验证码错误", "[CAPTCHA] ");
    }
}
