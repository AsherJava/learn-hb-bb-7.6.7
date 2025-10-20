/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.sqlutil;

public class TempTableCondition {
    private final String condition;
    private final String tempGroupId;

    public String getCondition() {
        return this.condition;
    }

    public String getTempGroupId() {
        return this.tempGroupId;
    }

    public TempTableCondition(String condition, String tempGroupId) {
        this.condition = condition;
        this.tempGroupId = tempGroupId;
    }
}

