/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.value;

import com.jiuqi.va.biz.intf.value.ConvertException;

public interface Converter {
    public Class<?> getSourceType();

    public Class<?> getTargetType();

    public Object cast(Object var1) throws ConvertException;
}

