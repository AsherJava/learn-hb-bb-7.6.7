/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtils
implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtils.applicationContext == null) {
            SpringBeanUtils.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return SpringBeanUtils.getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return SpringBeanUtils.getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return SpringBeanUtils.getApplicationContext().getBean(name, clazz);
    }
}

