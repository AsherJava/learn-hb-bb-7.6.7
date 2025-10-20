/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.anno.intf;

import java.util.Map;

public interface IDefaultValueEnum {
    public String getTableName();

    public String getID();

    default public String getCode() {
        if (this.getFieldValues() != null) {
            return String.valueOf(this.getFieldValues().get("CODE"));
        }
        return null;
    }

    default public String getTitle() {
        if (this.getFieldValues() != null) {
            return String.valueOf(this.getFieldValues().get("TITLE"));
        }
        return null;
    }

    public Map<String, Object> getFieldValues();
}

