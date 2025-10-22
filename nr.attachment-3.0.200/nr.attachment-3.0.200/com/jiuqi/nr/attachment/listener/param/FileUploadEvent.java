/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.listener.param;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public class FileUploadEvent {
    private String dataSchemeKey;
    private String taskKey;
    private String formSchemeKey;
    private DimensionCombination dimensionCombination;
    private String fieldKey;
    private String groupKey;
    private List<String> fileKeys;

    public FileUploadEvent() {
    }

    public FileUploadEvent(String dataSchemeKey, String taskKey, String formSchemeKey, DimensionCombination dimensionCombination, String fieldKey, String groupKey, List<String> fileKeys) {
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.dimensionCombination = dimensionCombination;
        this.fieldKey = fieldKey;
        this.groupKey = groupKey;
        this.fileKeys = fileKeys;
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

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(List<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

