/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

import java.util.Map;

public class ClbrUpdateDataDTO {
    private String clbrCode;
    private String operateTime;
    private String operateType;
    private String srcBillCode;
    private String unitCode;
    private Map<String, Object> params;

    public String getClbrCode() {
        return this.clbrCode;
    }

    public void setClbrCode(String value) {
        this.clbrCode = value;
    }

    public String getOperateTime() {
        return this.operateTime;
    }

    public void setOperateTime(String value) {
        this.operateTime = value;
    }

    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String value) {
        this.operateType = value;
    }

    public String getSrcBillCode() {
        return this.srcBillCode;
    }

    public void setSrcBillCode(String value) {
        this.srcBillCode = value;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String value) {
        this.unitCode = value;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

