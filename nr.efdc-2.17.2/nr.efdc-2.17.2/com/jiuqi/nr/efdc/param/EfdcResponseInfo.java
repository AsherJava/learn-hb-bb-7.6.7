/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 */
package com.jiuqi.nr.efdc.param;

import com.jiuqi.nr.jtable.params.base.EntityViewData;
import java.util.List;

public class EfdcResponseInfo {
    private String unitCode;
    private String userName;
    private String startTime;
    private String endTime;
    private boolean isInclude;
    private boolean isFloat;
    private String efdcFormula;
    private String frangeFml;
    private String filterFml;
    private String efdcUrl;
    private String fieldCode;
    private boolean jsonType;
    private String fieldId;
    private String unitTitle;
    private List<EntityViewData> entityList;
    private String bblx;

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getFieldCode() {
        return this.fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFrangeFml() {
        return this.frangeFml;
    }

    public void setFrangeFml(String frangeFml) {
        this.frangeFml = frangeFml;
    }

    public String getFilterFml() {
        return this.filterFml;
    }

    public void setFilterFml(String filterFml) {
        this.filterFml = filterFml;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isInclude() {
        return this.isInclude;
    }

    public void setInclude(boolean include) {
        this.isInclude = include;
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public void setFloat(boolean aFloat) {
        this.isFloat = aFloat;
    }

    public String getEfdcFormula() {
        return this.efdcFormula;
    }

    public void setEfdcFormula(String efdcFormula) {
        this.efdcFormula = efdcFormula;
    }

    public String getEfdcUrl() {
        return this.efdcUrl;
    }

    public void setEfdcUrl(String efdcUrl) {
        this.efdcUrl = efdcUrl;
    }

    public boolean isJsonType() {
        return this.jsonType;
    }

    public void setJsonType(boolean jsonType) {
        this.jsonType = jsonType;
    }

    public String getFieldId() {
        return this.fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getUnitTitle() {
        return this.unitTitle;
    }

    public List<EntityViewData> getEntityList() {
        return this.entityList;
    }

    public void setEntityList(List<EntityViewData> entityList) {
        this.entityList = entityList;
    }
}

