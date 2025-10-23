/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.DataWrapper
 */
package com.jiuqi.nr.datascheme.internal.service.impl.cache;

import com.jiuqi.nr.datascheme.api.core.DataWrapper;

public class DefaultDataWrapper<T>
implements DataWrapper<T> {
    T data;
    boolean exist;

    public T get() {
        return this.data;
    }

    public boolean isPresent() {
        return this.exist;
    }

    public static <T> DefaultDataWrapper<T> valueOf(T t) {
        DefaultDataWrapper<T> tDataFieldWrapper = new DefaultDataWrapper<T>();
        tDataFieldWrapper.data = t;
        tDataFieldWrapper.exist = true;
        return tDataFieldWrapper;
    }

    public static <T> DefaultDataWrapper<T> empty() {
        return new DefaultDataWrapper<T>();
    }

    public DefaultDataWrapper<T> with(T t) {
        this.data = t;
        this.exist = true;
        return this;
    }
}

