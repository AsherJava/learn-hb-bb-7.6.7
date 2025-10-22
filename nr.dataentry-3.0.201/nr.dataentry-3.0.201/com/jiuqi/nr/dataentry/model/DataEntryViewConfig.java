/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.model;

import java.util.List;
import java.util.UUID;

public class DataEntryViewConfig {
    private String templateId;
    private String title;
    private String code;
    private String taskKey;
    private String formSchemeKey;
    private String period;
    private Boolean periodAllowChange;
    private boolean periodHidden;
    private Boolean unitViewHidden;
    private UUID unitKey;
    private String unitTitle;
    private String formKey;
    private String formTitle;
    private String batchDimension;
    private String formScopt;
    private List<String> formLable;
    private Boolean formSchemeAllowChange;
    private Boolean hideAllUnit;
    private String variableMap;

    public String getTemplateId() {
        return this.templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public UUID getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(UUID unitKey) {
        this.unitKey = unitKey;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getBatchDimension() {
        return this.batchDimension;
    }

    public void setBatchDimension(String batchDimension) {
        this.batchDimension = batchDimension;
    }

    public Boolean getUnitViewHidden() {
        return this.unitViewHidden;
    }

    public void setUnitViewHidden(Boolean unitViewHidden) {
        this.unitViewHidden = unitViewHidden;
    }

    public Boolean getFormSchemeAllowChange() {
        return this.formSchemeAllowChange;
    }

    public void setFormSchemeAllowChange(Boolean formSchemeAllowChange) {
        this.formSchemeAllowChange = formSchemeAllowChange;
    }

    public Boolean getPeriodAllowChange() {
        return this.periodAllowChange;
    }

    public void setPeriodAllowChange(Boolean periodAllowChange) {
        this.periodAllowChange = periodAllowChange;
    }

    public List<String> getFormLable() {
        return this.formLable;
    }

    public void setFormLable(List<String> formLable) {
        this.formLable = formLable;
    }

    public String getVariableMap() {
        return this.variableMap;
    }

    public void setVariableMap(String variableMap) {
        this.variableMap = variableMap;
    }

    public String getFormScopt() {
        return this.formScopt;
    }

    public void setFormScopt(String formScopt) {
        this.formScopt = formScopt;
    }

    public boolean isPeriodHidden() {
        return this.periodHidden;
    }

    public void setPeriodHidden(boolean periodHidden) {
        this.periodHidden = periodHidden;
    }

    public Boolean getHideAllUnit() {
        return this.hideAllUnit;
    }

    public void setHideAllUnit(Boolean hideAllUnit) {
        this.hideAllUnit = hideAllUnit;
    }
}

