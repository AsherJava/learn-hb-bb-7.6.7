/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

public class JobConfigParameter {
    private String entityName;
    private String value;

    public JobConfigParameter(String entityName, String value) {
        this.entityName = entityName;
        this.value = value;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

