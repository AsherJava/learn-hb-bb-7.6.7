/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.dataanalyze.vo;

import com.jiuqi.nr.datacheck.dataanalyze.CheckCondition;
import com.jiuqi.nr.datacheck.dataanalyze.OrgSelectType;
import java.util.List;
import java.util.Map;

public class AnalyzeParam {
    private String recordKey;
    private String itemKey;
    private String task;
    private List<String> orgCodes;
    private String period;
    private Map<String, String> dimSet;
    private List<String> models;
    private Map<String, OrgSelectType> orgSelectMap;
    private CheckCondition checkRequires;
    private String type;
    private String orgEntity;
    private String formScheme;
    private Map<String, String> entityLabelValues;
    private String checkSchemeKey;

    public String getRecordKey() {
        return this.recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getItemKey() {
        return this.itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Map<String, String> getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(Map<String, String> dimSet) {
        this.dimSet = dimSet;
    }

    public List<String> getModels() {
        return this.models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public Map<String, OrgSelectType> getOrgSelectMap() {
        return this.orgSelectMap;
    }

    public void setOrgSelectMap(Map<String, OrgSelectType> orgSelectMap) {
        this.orgSelectMap = orgSelectMap;
    }

    public CheckCondition getCheckRequires() {
        return this.checkRequires;
    }

    public void setCheckRequires(CheckCondition checkRequires) {
        this.checkRequires = checkRequires;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgEntity() {
        return this.orgEntity;
    }

    public void setOrgEntity(String orgEntity) {
        this.orgEntity = orgEntity;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

    public Map<String, String> getEntityLabelValues() {
        return this.entityLabelValues;
    }

    public void setEntityLabelValues(Map<String, String> entityLabelValues) {
        this.entityLabelValues = entityLabelValues;
    }

    public String getCheckSchemeKey() {
        return this.checkSchemeKey;
    }

    public void setCheckSchemeKey(String checkSchemeKey) {
        this.checkSchemeKey = checkSchemeKey;
    }
}

