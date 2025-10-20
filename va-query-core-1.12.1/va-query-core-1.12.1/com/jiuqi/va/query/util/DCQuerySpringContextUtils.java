/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

import java.util.Collection;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class DCQuerySpringContextUtils
implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DCQuerySpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return DCQuerySpringContextUtils.getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return DCQuerySpringContextUtils.getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(Class<T> clazz, Object ... args) {
        return DCQuerySpringContextUtils.getApplicationContext().getBean(clazz, args);
    }

    public static <T> Collection<T> getBeans(Class<T> clazz) {
        Map<String, T> beans = DCQuerySpringContextUtils.getApplicationContext().getBeansOfType(clazz);
        return beans.values();
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return DCQuerySpringContextUtils.getApplicationContext().getBean(name, clazz);
    }
}

