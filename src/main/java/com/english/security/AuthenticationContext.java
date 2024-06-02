package com.english.security;

import org.springframework.security.core.Authentication;

public class AuthenticationContext
{
    private static final ThreadLocal<Authentication> context = new ThreadLocal<>();

    public static Authentication getContext()
    {
        return context.get();
    }

    public static void setContext(Authentication authentication)
    {
        context.set(authentication);
    }

    public static void clearContext()
    {
        context.remove();
    }
}
