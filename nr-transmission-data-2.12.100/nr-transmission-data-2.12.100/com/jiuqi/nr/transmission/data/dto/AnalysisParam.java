/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.dto;

import com.jiuqi.nr.transmission.data.common.MappingType;
import com.jiuqi.nr.transmission.data.intf.ImportParam;

public class AnalysisParam {
    private String taskKey;
    private String formSchemeKey;
    private MappingType mappingType;

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

    public MappingType getMappingType() {
        return this.mappingType;
    }

    public void setMappingType(MappingType mappingType) {
        this.mappingType = mappingType;
    }

    public static AnalysisParam getAnalysisParam(ImportParam importParam, MappingType mappingType) {
        AnalysisParam analysisParam = new AnalysisParam();
        if (importParam != null) {
            analysisParam.setTaskKey(importParam.getTaskKey());
            analysisParam.setFormSchemeKey(importParam.getFormSchemeKey());
        }
        analysisParam.setMappingType(mappingType);
        return analysisParam;
    }
}

