/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.dto;

import java.util.List;

public class ItemOrderMoveDTO {
    private String sourceKey;
    private List<String> sourceKeyLists;
    private String targetKey;
    private Integer way;
    private String formGroupKey;
    private String formSchemeKey;

    public String getSourceKey() {
        return this.sourceKey;
    }

    public void setSourceKey(String sourceKey) {
        this.sourceKey = sourceKey;
    }

    public List<String> getSourceKeyLists() {
        return this.sourceKeyLists;
    }

    public void setSourceKeyLists(List<String> sourceKeyLists) {
        this.sourceKeyLists = sourceKeyLists;
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

    public String getFormGroupKey() {
        return this.formGroupKey;
    }

    public void setFormGroupKey(String formGroupKey) {
        this.formGroupKey = formGroupKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

