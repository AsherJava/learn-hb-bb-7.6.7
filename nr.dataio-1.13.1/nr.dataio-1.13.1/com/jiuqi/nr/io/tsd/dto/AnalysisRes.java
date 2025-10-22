/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tsd.dto;

import com.jiuqi.nr.io.tsd.dto.DimValueDTO;
import com.jiuqi.nr.io.tsd.dto.ExportType;
import java.util.List;

public class AnalysisRes {
    private String taskKey;
    private String contextEntityId;
    private String formSchemeKey;
    private String periodValue;
    private String periodTitle;
    private List<ExportType> exportTypes;
    private int nonexistentUnitCount;
    private int allUnitCount;
    private List<DimValueDTO> filterDims;
    private List<DimValueDTO> completionDims;
    private String dwSchemeKey;
    private String dwDimName;
    private int formCount;
    private boolean containAccountForm;

    public int getFormCount() {
        return this.formCount;
    }

    public void setFormCount(int formCount) {
        this.formCount = formCount;
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

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public List<ExportType> getExportTypes() {
        return this.exportTypes;
    }

    public void setExportTypes(List<ExportType> exportTypes) {
        this.exportTypes = exportTypes;
    }

    public List<DimValueDTO> getFilterDims() {
        return this.filterDims;
    }

    public void setFilterDims(List<DimValueDTO> filterDims) {
        this.filterDims = filterDims;
    }

    public List<DimValueDTO> getCompletionDims() {
        return this.completionDims;
    }

    public void setCompletionDims(List<DimValueDTO> completionDims) {
        this.completionDims = completionDims;
    }

    public String getDwSchemeKey() {
        return this.dwSchemeKey;
    }

    public void setDwSchemeKey(String dwSchemeKey) {
        this.dwSchemeKey = dwSchemeKey;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public void setContextEntityId(String contextEntityId) {
        this.contextEntityId = contextEntityId;
    }

    public int getNonexistentUnitCount() {
        return this.nonexistentUnitCount;
    }

    public void setNonexistentUnitCount(int nonexistentUnitCount) {
        this.nonexistentUnitCount = nonexistentUnitCount;
    }

    public int getAllUnitCount() {
        return this.allUnitCount;
    }

    public void setAllUnitCount(int allUnitCount) {
        this.allUnitCount = allUnitCount;
    }

    public String getDwDimName() {
        return this.dwDimName;
    }

    public void setDwDimName(String dwDimName) {
        this.dwDimName = dwDimName;
    }

    public String getPeriodTitle() {
        return this.periodTitle;
    }

    public void setPeriodTitle(String periodTitle) {
        this.periodTitle = periodTitle;
    }

    public boolean isContainAccountForm() {
        return this.containAccountForm;
    }

    public void setContainAccountForm(boolean containAccountForm) {
        this.containAccountForm = containAccountForm;
    }
}

