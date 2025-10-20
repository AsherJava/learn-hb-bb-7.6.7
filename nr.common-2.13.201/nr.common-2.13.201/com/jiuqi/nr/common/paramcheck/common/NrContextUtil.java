/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.paramcheck.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class NrContextUtil
implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (NrContextUtil.applicationContext == null) {
            NrContextUtil.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String name) {
        return NrContextUtil.getApplicationContext().getBean(name);
    }
}

