/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.core;

import com.jiuqi.nr.zb.scheme.common.ZbDataType;

public interface PropData {
    public String getTitle();

    public ZbDataType getDataType();

    public String getFieldName();

    public Object getValue();
}

