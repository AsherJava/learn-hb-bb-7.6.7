/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

public class DataSourceConfigBinder<T> {
    private Environment environment;

    public DataSourceConfigBinder(Environment environment) {
        this.environment = environment;
    }

    public T bindProperties(String prefix, T target) {
        if (this.environment == null) {
            throw new IllegalArgumentException("Environment must not be null");
        }
        BindResult<T> bindResult = Binder.get(this.environment).bind(prefix, Bindable.ofInstance(target));
        return bindResult.get();
    }
}

