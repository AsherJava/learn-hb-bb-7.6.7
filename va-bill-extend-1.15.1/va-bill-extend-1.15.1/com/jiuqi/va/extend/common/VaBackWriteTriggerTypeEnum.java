/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.extend.common;

public enum VaBackWriteTriggerTypeEnum {
    BEFORE_SAVE("beforeSave"),
    AFTER_SAVE("afterSave"),
    BEFORE_DELETE("beforeDelete"),
    AFTER_DELETE("afterDelete");

    private String name;

    private VaBackWriteTriggerTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}

