/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.dto;

import java.util.HashMap;
import java.util.Map;

public class PenetrationParamDTO {
    private String mdCode;
    private String subjectCode;
    private String mdCurrency;
    private String mdGcOrgType;
    private String unitCode;
    private String oppoUnitCode;
    private String srcType;
    private String dataTime;
    private String unitType;
    private String penetrationType;
    private Map<String, Object> data;

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getSubjectCode() {
        return this.subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getMdCurrency() {
        return this.mdCurrency;
    }

    public void setMdCurrency(String mdCurrency) {
        this.mdCurrency = mdCurrency;
    }

    public String getMdGcOrgType() {
        return this.mdGcOrgType;
    }

    public void setMdGcOrgType(String mdGcOrgType) {
        this.mdGcOrgType = mdGcOrgType;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOppoUnitCode() {
        return this.oppoUnitCode;
    }

    public void setOppoUnitCode(String oppoUnitCode) {
        this.oppoUnitCode = oppoUnitCode;
    }

    public String getSrcType() {
        return this.srcType;
    }

    public void setSrcType(String srcType) {
        this.srcType = srcType;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getUnitType() {
        return this.unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getPenetrationType() {
        return this.penetrationType;
    }

    public void setPenetrationType(String penetrationType) {
        this.penetrationType = penetrationType;
    }

    public void addData(String key, Object value) {
        if (this.data == null) {
            this.data = new HashMap<String, Object>();
        }
        this.data.put(key, value);
    }
}

