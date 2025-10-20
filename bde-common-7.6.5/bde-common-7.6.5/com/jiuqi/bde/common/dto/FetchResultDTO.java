/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.dto;

import com.jiuqi.bde.common.dto.fetch.result.FloatRegionResultDTO;
import java.util.Map;

public class FetchResultDTO {
    private String requestTaskId;
    private String unitCode;
    private String periodScheme;
    private String fetchSchemeId;
    private String formSchemeId;
    private String taskId;
    private String formId;
    private Map<String, String> otherEntity;
    private String dimensionSetStr;
    private boolean success;
    private String errorInfo;
    private Map<String, Map<String, Object>> fixedResults;
    private Map<String, FloatRegionResultDTO> floatResults;

    public String getRequestTaskId() {
        return this.requestTaskId;
    }

    public void setRequestTaskId(String requestTaskId) {
        this.requestTaskId = requestTaskId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getPeriodScheme() {
        return this.periodScheme;
    }

    public void setPeriodScheme(String periodScheme) {
        this.periodScheme = periodScheme;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }

    public String getFormSchemeId() {
        return this.formSchemeId;
    }

    public void setFormSchemeId(String formSchemeId) {
        this.formSchemeId = formSchemeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public Map<String, String> getOtherEntity() {
        return this.otherEntity;
    }

    public void setOtherEntity(Map<String, String> otherEntity) {
        this.otherEntity = otherEntity;
    }

    public String getDimensionSetStr() {
        return this.dimensionSetStr;
    }

    public void setDimensionSetStr(String dimensionSetStr) {
        this.dimensionSetStr = dimensionSetStr;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Map<String, Map<String, Object>> getFixedResults() {
        return this.fixedResults;
    }

    public void setFixedResults(Map<String, Map<String, Object>> fixedResults) {
        this.fixedResults = fixedResults;
    }

    public Map<String, FloatRegionResultDTO> getFloatResults() {
        return this.floatResults;
    }

    public void setFloatResults(Map<String, FloatRegionResultDTO> floatResults) {
        this.floatResults = floatResults;
    }
}

