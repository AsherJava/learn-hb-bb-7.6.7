/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tsd.dto;

public class AnalysisParam {
    private String taskKey;
    private String formSchemeKey;
    private String mappingKey;
    private boolean analysisDw;
    private boolean analysisDim;

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

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }

    public boolean isAnalysisDw() {
        return this.analysisDw;
    }

    public void setAnalysisDw(boolean analysisDw) {
        this.analysisDw = analysisDw;
    }

    public boolean isAnalysisDim() {
        return this.analysisDim;
    }

    public void setAnalysisDim(boolean analysisDim) {
        this.analysisDim = analysisDim;
    }
}

