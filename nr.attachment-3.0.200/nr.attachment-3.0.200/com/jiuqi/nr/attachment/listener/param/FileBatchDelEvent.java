/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.listener.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class FileBatchDelEvent {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCombination dimensionCombination;
    private List<String> fieldKeys;
    private List<String> groupKeys;

    public FileBatchDelEvent() {
    }

    public FileBatchDelEvent(String dataSchemeKey, String taskKey, String formSchemeKey, DimensionCombination dimensionCombination, List<String> fieldKeys, List<String> groupKeys) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.dimensionCombination = dimensionCombination;
        this.fieldKeys = fieldKeys;
        this.groupKeys = groupKeys;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
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

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
    }

    public List<String> getFieldKeys() {
        return this.fieldKeys;
    }

    public void setFieldKeys(List<String> fieldKeys) {
        this.fieldKeys = fieldKeys;
    }

    public List<String> getGroupKeys() {
        return this.groupKeys;
    }

    public void setGroupKeys(List<String> groupKeys) {
        this.groupKeys = groupKeys;
    }
}

