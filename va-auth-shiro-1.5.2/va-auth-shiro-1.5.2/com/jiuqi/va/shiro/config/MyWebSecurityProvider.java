/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.shiro.config;

import com.jiuqi.va.shiro.config.MyFormAuthenticationFilter;
import com.jiuqi.va.shiro.config.MyWebSecurityManage;

public interface MyWebSecurityProvider {
    public MyWebSecurityManage getWebSecurityManager();

    default public MyFormAuthenticationFilter getFormAuthenticationFilter() {
        return new MyFormAuthenticationFilter();
    }
}

