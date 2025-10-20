/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.base;

import com.jiuqi.va.datamodel.exchange.nvwa.base.SetValueNotNull;

public class ObjectUtil {
    public static <T> void setValue(T value, SetValueNotNull<T> setVal) throws Exception {
        if (value != null) {
            setVal.setValue(value);
        }
    }

    public static <K, M> K getValue(M value, GetValueNotNull<K, M> setVal) throws Exception {
        if (value != null) {
            return setVal.getValue(value);
        }
        return null;
    }

    @FunctionalInterface
    public static interface GetValueNotNull<K, M> {
        public K getValue(M var1) throws Exception;
    }
}

