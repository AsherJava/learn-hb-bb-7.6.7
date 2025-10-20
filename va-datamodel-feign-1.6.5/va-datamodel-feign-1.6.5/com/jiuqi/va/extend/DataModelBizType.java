/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend;

public interface DataModelBizType {
    public String getName();

    public String getTitle();

    default public int getOrdinal() {
        return 99;
    }
}

