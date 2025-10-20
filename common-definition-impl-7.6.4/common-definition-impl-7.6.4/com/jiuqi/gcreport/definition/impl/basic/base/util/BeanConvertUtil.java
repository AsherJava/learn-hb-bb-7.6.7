/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.util;

public class BeanConvertUtil {
    public static <T> void setValue(T value, SetValueNotNull<T> setVal) throws Exception {
        if (value != null) {
            setVal.setValue(value);
        }
    }

    @FunctionalInterface
    public static interface SetValueNotNull<T> {
        public void setValue(T var1) throws Exception;
    }
}

