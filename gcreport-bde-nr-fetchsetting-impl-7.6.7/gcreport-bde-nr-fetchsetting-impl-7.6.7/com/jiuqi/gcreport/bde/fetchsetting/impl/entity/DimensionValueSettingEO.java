/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.entity;

public class DimensionValueSettingEO {
    public static final String TABLENAME = "BDE_DIMVALUESETTING";
    private String id;
    private String fetchSchemeId;
    private String formSchemeId;
    private String formId;
    private String directionType;
    private String positionNum;
    private String rowGroupId;
    private String dimType;
    private String dimValue;

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

    public String getRowGroupId() {
        return this.rowGroupId;
    }

    public void setRowGroupId(String rowGroupId) {
        this.rowGroupId = rowGroupId;
    }

    public String getDimType() {
        return this.dimType;
    }

    public void setDimType(String dimType) {
        this.dimType = dimType;
    }

    public String getDimValue() {
        return this.dimValue;
    }

    public void setDimValue(String dimValue) {
        this.dimValue = dimValue;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

