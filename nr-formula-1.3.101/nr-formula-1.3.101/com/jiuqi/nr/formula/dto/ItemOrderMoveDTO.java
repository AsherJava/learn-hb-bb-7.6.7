/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.dto;

public class ItemOrderMoveDTO {
    private String formSchemeKey;
    private String sourceKey;
    private String targetKey;
    private Integer way;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public String getTargetKey() {
        return this.targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public Integer getWay() {
        return this.way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }
}

