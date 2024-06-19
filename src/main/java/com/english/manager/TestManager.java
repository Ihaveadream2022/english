package com.english.manager;

import com.english.config.AppConfig;
import com.english.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class TestManager {
    private static final AppConfig appConfig = (AppConfig) SpringUtil.getBean("AppConfig");

    public static AppConfig getAppConfig() {
        return appConfig;
    }
}
