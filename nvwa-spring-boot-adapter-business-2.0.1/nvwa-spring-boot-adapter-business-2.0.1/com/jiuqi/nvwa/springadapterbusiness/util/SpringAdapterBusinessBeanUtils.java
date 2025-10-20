/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.springadapterbusiness.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringAdapterBusinessBeanUtils
implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringAdapterBusinessBeanUtils.applicationContext == null) {
            SpringAdapterBusinessBeanUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return SpringAdapterBusinessBeanUtils.getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return SpringAdapterBusinessBeanUtils.getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return SpringAdapterBusinessBeanUtils.getApplicationContext().getBean(name, clazz);
    }
}

