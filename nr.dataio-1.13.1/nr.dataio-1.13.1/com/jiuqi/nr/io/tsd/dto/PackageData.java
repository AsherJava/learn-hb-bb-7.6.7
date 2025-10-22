/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 */
package com.jiuqi.nr.io.tsd.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.io.tsd.dto.DimValueDTO;
import com.jiuqi.nr.io.tsd.dto.Form;
import com.jiuqi.nr.io.tsd.dto.Unit;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PackageData {
    private String taskKey;
    private String taskCode;
    private String taskTitle;
    private String formSchemeKey;
    private String formSchemeCode;
    private String formSchemeTitle;
    private String periodValue;
    @JsonIgnore
    private List<Unit> entityKeys;
    private List<Form> forms;
    private List<String> dataCatalog;
    private String version;
    private String caliberEntity;
    private List<DimValueDTO> allDims;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeCode() {
        return this.formSchemeCode;
    }

    public void setFormSchemeCode(String formSchemeCode) {
        this.formSchemeCode = formSchemeCode;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public List<Unit> getEntityKeys() {
        return this.entityKeys;
    }

    public void setEntityKeys(List<Unit> entityKeys) {
        this.entityKeys = entityKeys;
    }

    public List<DimValueDTO> getAllDims() {
        return this.allDims;
    }

    public void setAllDims(List<DimValueDTO> allDims) {
        this.allDims = allDims;
    }

    public List<Form> getForms() {
        return this.forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public List<String> getDataCatalog() {
        return this.dataCatalog;
    }

    public void setDataCatalog(List<String> dataCatalog) {
        this.dataCatalog = dataCatalog;
    }

    public String getCaliberEntity() {
        return this.caliberEntity;
    }

    public void setCaliberEntity(String caliberEntity) {
        this.caliberEntity = caliberEntity;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}

