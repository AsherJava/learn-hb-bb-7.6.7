/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.datacheck.dataanalyze.vo.AnalyzeErrorInfo;
import java.util.List;

public class ErrorParam {
    private String task;
    private String period;
    private String checkSchemeKey;
    private String formSchemeKey;
    private String orgEntity;
    private String description;
    private boolean cover;
    private List<AnalyzeErrorInfo> errors;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCover() {
        return this.cover;
    }

    public void setCover(boolean cover) {
        this.cover = cover;
    }

    public List<AnalyzeErrorInfo> getErrors() {
        return this.errors;
    }

    public void setErrors(List<AnalyzeErrorInfo> errors) {
        this.errors = errors;
    }

    public String getCheckSchemeKey() {
        return this.checkSchemeKey;
    }

    public void setCheckSchemeKey(String checkSchemeKey) {
        this.checkSchemeKey = checkSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }
}

