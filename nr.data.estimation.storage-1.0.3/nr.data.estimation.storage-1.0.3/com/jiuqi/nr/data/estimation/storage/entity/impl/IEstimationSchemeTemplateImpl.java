/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 */
package com.jiuqi.nr.data.estimation.storage.entity.impl;

import com.jiuqi.nr.data.estimation.storage.entity.IEstimationForm;
import com.jiuqi.nr.data.estimation.storage.entity.IEstimationSchemeTemplate;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import java.util.Date;
import java.util.List;

public class IEstimationSchemeTemplateImpl
implements IEstimationSchemeTemplate {
    private String key;
    private String code;
    private String title;
    private Date updateTime;
    private String creator;
    private String order;
    private TaskDefine taskDefine;
    private FormSchemeDefine formSchemeDefine;
    private List<IEstimationForm> estimationForms;
    private List<FormulaSchemeDefine> accessFormulaSchemes;
    private List<FormulaSchemeDefine> calcFormulaSchemes;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public TaskDefine getTaskDefine() {
        return this.taskDefine;
    }

    public void setTaskDefine(TaskDefine taskDefine) {
        this.taskDefine = taskDefine;
    }

    @Override
    public FormSchemeDefine getFormSchemeDefine() {
        return this.formSchemeDefine;
    }

    public void setFormSchemeDefine(FormSchemeDefine formSchemeDefine) {
        this.formSchemeDefine = formSchemeDefine;
    }

    @Override
    public List<IEstimationForm> getEstimationForms() {
        return this.estimationForms;
    }

    public void setEstimationForms(List<IEstimationForm> estimationForms) {
        this.estimationForms = estimationForms;
    }

    @Override
    public List<FormulaSchemeDefine> getAccessFormulaSchemes() {
        return this.accessFormulaSchemes;
    }

    public void setAccessFormulaSchemes(List<FormulaSchemeDefine> accessFormulaSchemes) {
        this.accessFormulaSchemes = accessFormulaSchemes;
    }

    @Override
    public List<FormulaSchemeDefine> getCalcFormulaSchemes() {
        return this.calcFormulaSchemes;
    }

    public void setCalcFormulaSchemes(List<FormulaSchemeDefine> calcFormulaSchemes) {
        this.calcFormulaSchemes = calcFormulaSchemes;
    }
}

