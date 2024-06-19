package com.english.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtil implements ApplicationContextAware, BeanFactoryPostProcessor {

    private static ApplicationContext applicationContext;

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtil.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtil.beanFactory = beanFactory;
    }

    public static Object getBean(String name) throws BeansException {
        return beanFactory.getBean(name);
    }

    public static String[] getProfile() {
        return SpringUtil.applicationContext.getEnvironment().getActiveProfiles();
    }
}
