/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util.DynamicDataSourceContextHolder;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicDataSourceProxy
implements InvocationHandler {
    private String datasourceKey;
    private Object target;

    private Object bind(Object target, String datasourceKey) {
        this.target = target;
        this.datasourceKey = datasourceKey;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), (InvocationHandler)this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DynamicDataSourceContextHolder.setDataSourceKey(this.datasourceKey);
        try {
            Object object = method.invoke(this.target, args);
            return object;
        }
        finally {
            DynamicDataSourceContextHolder.clear();
        }
    }

    public static <T> T createProxyInstance(T target, String datasourceKey) {
        return (T)new DynamicDataSourceProxy().bind(target, datasourceKey);
    }
}

