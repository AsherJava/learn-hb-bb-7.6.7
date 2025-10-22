/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.jiuqi.nr.data.estimation.storage.entity.impl.EstimationForm;
import java.util.Date;
import java.util.List;

public class EstimationSchemeBase {
    private String key;
    private String code;
    private String title;
    private String taskId;
    private String formSchemeId;
    private Date updateTime;
    private String creator;
    private String order;
    private List<EstimationForm> formDefines;
    private List<String> accessFormulaSchemes;
    private List<String> calcFormulaSchemes;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<EstimationForm> getFormDefines() {
        return this.formDefines;
    }

    public void setFormDefines(List<EstimationForm> formDefines) {
        this.formDefines = formDefines;
    }

    public List<String> getAccessFormulaSchemes() {
        return this.accessFormulaSchemes;
    }

    public void setAccessFormulaSchemes(List<String> accessFormulaSchemes) {
        this.accessFormulaSchemes = accessFormulaSchemes;
    }

    public List<String> getCalcFormulaSchemes() {
        return this.calcFormulaSchemes;
    }

    public void setCalcFormulaSchemes(List<String> calcFormulaSchemes) {
        this.calcFormulaSchemes = calcFormulaSchemes;
    }
}

