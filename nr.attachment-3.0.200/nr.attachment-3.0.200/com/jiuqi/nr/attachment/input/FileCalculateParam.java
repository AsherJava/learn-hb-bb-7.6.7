/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class FileCalculateParam {
    private String taskKey;
    private String formSchemeKey;
    private String fieldKey;
    private String fromGroupKey;
    private DimensionCombination toDims;

    public FileCalculateParam() {
    }

    public FileCalculateParam(String taskKey, String formSchemeKey, String fieldKey, String fromGroupKey, DimensionCombination toDims) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.fieldKey = fieldKey;
        this.fromGroupKey = fromGroupKey;
        this.toDims = toDims;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFromGroupKey() {
        return this.fromGroupKey;
    }

    public void setFromGroupKey(String fromGroupKey) {
        this.fromGroupKey = fromGroupKey;
    }

    public DimensionCombination getToDims() {
        return this.toDims;
    }

    public void setToDims(DimensionCombination toDims) {
        this.toDims = toDims;
    }
}

