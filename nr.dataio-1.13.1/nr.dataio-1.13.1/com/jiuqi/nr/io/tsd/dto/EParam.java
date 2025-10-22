/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EParam
implements Serializable {
    private static final long serialVersionUID = 2L;
    private Map<String, DimensionValue> masterKeys;
    private String periodValue;
    private String taskKey;
    private String formSchemeKey;
    private String mappingSchemeKey;
    private List<String> formKeys;
    private boolean exportAttachments = true;
    private List<String> exportTypes;

    public Map<String, DimensionValue> getMasterKeys() {
        return this.masterKeys;
    }

    public void setMasterKeys(Map<String, DimensionValue> masterKeys) {
        this.masterKeys = masterKeys;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getExportTypes() {
        return this.exportTypes;
    }

    public void setExportTypes(List<String> exportTypes) {
        this.exportTypes = exportTypes;
    }

    public String getMappingSchemeKey() {
        return this.mappingSchemeKey;
    }

    public void setMappingSchemeKey(String mappingSchemeKey) {
        this.mappingSchemeKey = mappingSchemeKey;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public boolean isExportAttachments() {
        return this.exportAttachments;
    }

    public void setExportAttachments(boolean exportAttachments) {
        this.exportAttachments = exportAttachments;
    }
}

