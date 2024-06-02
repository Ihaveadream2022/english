package com.english.service;

import java.util.Map;

public interface LogService
{
    public Map<String, Object> login(String username, String password, String code, String uuid);

    public void checkCaptcha(String code, String uuid);
}