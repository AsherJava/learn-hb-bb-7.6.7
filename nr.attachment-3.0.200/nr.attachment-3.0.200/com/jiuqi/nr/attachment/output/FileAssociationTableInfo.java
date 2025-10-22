/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.attachment.output;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class FileAssociationTableInfo {
    private String id;
    private String dwCode;
    private String dataTime;
    private DimensionCombination dims;
    private String taskKey;
    private String formKey;
    private String fieldKey;
    private String groupKey;
    private String fileKey;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDwCode() {
        return this.dwCode;
    }

    public void setDwCode(String dwCode) {
        this.dwCode = dwCode;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public DimensionCombination getDims() {
        return this.dims;
    }

    public void setDims(DimensionCombination dims) {
        this.dims = dims;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
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

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}

