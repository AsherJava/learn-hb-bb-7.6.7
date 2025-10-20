/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.intf;

public interface TableFieldNode {
    public String getTableName();

    public String getFieldName();

    default public boolean isMultiChoice() {
        return false;
    }
}

