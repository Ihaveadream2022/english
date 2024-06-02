package com.english.exception.captcha;

import com.english.exception.ServiceRuntimeException;

public class CaptchaExpiredException extends ServiceRuntimeException
{
    public CaptchaExpiredException()
    {
        super("验证码已过期", "[CAPTCHA] ");
    }
}
