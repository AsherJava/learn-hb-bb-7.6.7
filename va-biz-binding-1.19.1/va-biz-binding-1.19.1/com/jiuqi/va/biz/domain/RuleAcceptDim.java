/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain;

import java.util.Objects;

public final class RuleAcceptDim {
    private final String name;
    private final String triggerType;
    private final String tableName;
    private final String fieldName;
    private final int hashCodeValue;

    public String getName() {
        return this.name;
    }

    public String getTriggerType() {
        return this.triggerType;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    private RuleAcceptDim(String name, String triggerType, String tableName, String fieldName) {
        this.name = name;
        this.triggerType = triggerType;
        this.tableName = tableName;
        this.fieldName = fieldName;
        int h = 1;
        h = 31 * h + name.hashCode();
        h = 31 * h + (triggerType == null ? 0 : triggerType.hashCode());
        h = 31 * h + (tableName == null ? 0 : tableName.hashCode());
        this.hashCodeValue = h = 31 * h + (fieldName == null ? 0 : fieldName.hashCode());
    }

    public static RuleAcceptDim of(String name, String triggerType, String tableName, String fieldName) {
        return new RuleAcceptDim(name, triggerType, tableName, fieldName);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        RuleAcceptDim that = (RuleAcceptDim)o;
        return Objects.equals(this.name, that.name) && Objects.equals(this.triggerType, that.triggerType) && Objects.equals(this.tableName, that.tableName) && Objects.equals(this.fieldName, that.fieldName);
    }

    public int hashCode() {
        return this.hashCodeValue;
    }
}

