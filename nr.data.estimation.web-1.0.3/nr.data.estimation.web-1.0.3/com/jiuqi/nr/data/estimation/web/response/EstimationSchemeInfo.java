/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.web.response;

import com.jiuqi.nr.data.estimation.web.response.EstimationFormInfo;
import com.jiuqi.nr.data.estimation.web.response.EstimationFormulaSchemeSet;
import java.util.List;

public class EstimationSchemeInfo {
    private String key;
    private String code;
    private String title;
    private String taskId;
    private String formSchemeId;
    private List<EstimationFormInfo> formInfos;
    private EstimationFormulaSchemeSet accessFormulaSchemes;
    private EstimationFormulaSchemeSet calcFormulaSchemes;

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

    public List<EstimationFormInfo> getFormInfos() {
        return this.formInfos;
    }

    public void setFormInfos(List<EstimationFormInfo> formInfos) {
        this.formInfos = formInfos;
    }

    public EstimationFormulaSchemeSet getAccessFormulaSchemes() {
        return this.accessFormulaSchemes;
    }

    public void setAccessFormulaSchemes(EstimationFormulaSchemeSet accessFormulaSchemes) {
        this.accessFormulaSchemes = accessFormulaSchemes;
    }

    public EstimationFormulaSchemeSet getCalcFormulaSchemes() {
        return this.calcFormulaSchemes;
    }

    public void setCalcFormulaSchemes(EstimationFormulaSchemeSet calcFormulaSchemes) {
        this.calcFormulaSchemes = calcFormulaSchemes;
    }
}

