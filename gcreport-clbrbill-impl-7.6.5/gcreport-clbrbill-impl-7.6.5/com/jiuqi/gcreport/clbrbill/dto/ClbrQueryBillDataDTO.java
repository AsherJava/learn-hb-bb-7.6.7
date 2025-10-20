/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbrbill.dto;

import com.jiuqi.gcreport.clbrbill.dto.ClbrBillExtendFieldDTO;

public class ClbrQueryBillDataDTO
extends ClbrBillExtendFieldDTO {
    private String oppUnitCode;
    private String srcBillDefine;
    private String srcBusinessType;
    private String unitCode;
    private Integer offset;
    private Integer limit;

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getOppUnitCode() {
        return this.oppUnitCode;
    }

    public void setOppUnitCode(String value) {
        this.oppUnitCode = value;
    }

    public String getSrcBillDefine() {
        return this.srcBillDefine;
    }

    public void setSrcBillDefine(String value) {
        this.srcBillDefine = value;
    }

    public String getSrcBusinessType() {
        return this.srcBusinessType;
    }

    public void setSrcBusinessType(String value) {
        this.srcBusinessType = value;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String value) {
        this.unitCode = value;
    }
}

