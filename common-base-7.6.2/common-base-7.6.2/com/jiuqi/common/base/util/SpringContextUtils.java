/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import java.util.Collection;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

@Component(value="GcSpringContextUtils")
@Lazy(value=false)
public class SpringContextUtils
implements ApplicationContextAware,
EmbeddedValueResolverAware {
    private static ApplicationContext applicationContext;
    private static StringValueResolver valueResolver;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return SpringContextUtils.getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        try {
            return SpringContextUtils.getApplicationContext().getBean(clazz);
        }
        catch (BeansException beansException) {
            return null;
        }
    }

    public static <T> T getBean(Class<T> clazz, Object ... args) {
        return SpringContextUtils.getApplicationContext().getBean(clazz, args);
    }

    public static <T> Collection<T> getBeans(Class<T> clazz) {
        Map<String, T> beans = SpringContextUtils.getApplicationContext().getBeansOfType(clazz);
        return beans.values();
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return SpringContextUtils.getApplicationContext().getBean(name, clazz);
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        valueResolver = resolver;
    }

    public static String getPropertiesValue(String name) {
        return valueResolver.resolveStringValue("${" + name + "}");
    }
}

