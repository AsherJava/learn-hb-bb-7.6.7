/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.np.definition.common;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import org.springframework.context.ApplicationContext;

public class SpringBeanProvider {
    public static ApplicationContext getApplicationContext() {
        return SpringBeanUtils.getApplicationContext();
    }

    public static Object getBean(String name) {
        return SpringBeanUtils.getBean((String)name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T)SpringBeanUtils.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return (T)SpringBeanUtils.getBean((String)name, clazz);
    }
}

