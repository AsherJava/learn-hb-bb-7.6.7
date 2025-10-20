/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

public class DimensionSettingEO {
    public static final String TABLENAME = "BDE_DIMSETTING";
    private static final long serialVersionUID = -3802585331769379467L;
    private String id;
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String globalDim;
    private String globalDimValue;
    private String rowDim;
    private String colDim;
    private String directionType;
    private String positionNum;
    private String dimSetting;

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

    public String getFormId() {
        return this.formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getGlobalDim() {
        return this.globalDim;
    }

    public void setGlobalDim(String globalDim) {
        this.globalDim = globalDim;
    }

    public String getGlobalDimValue() {
        return this.globalDimValue;
    }

    public void setGlobalDimValue(String globalDimValue) {
        this.globalDimValue = globalDimValue;
    }

    public String getRowDim() {
        return this.rowDim;
    }

    public void setRowDim(String rowDim) {
        this.rowDim = rowDim;
    }

    public String getColDim() {
        return this.colDim;
    }

    public void setColDim(String colDim) {
        this.colDim = colDim;
    }

    public String getDirectionType() {
        return this.directionType;
    }

    public void setDirectionType(String directionType) {
        this.directionType = directionType;
    }

    public String getPositionNum() {
        return this.positionNum;
    }

    public void setPositionNum(String positionNum) {
        this.positionNum = positionNum;
    }

    public String getDimSetting() {
        return this.dimSetting;
    }

    public void setDimSetting(String dimSetting) {
        this.dimSetting = dimSetting;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

