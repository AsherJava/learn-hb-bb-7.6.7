/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.billextract.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeCtxParamDTO;
import java.math.BigDecimal;
import java.util.List;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BillFetchSchemeDTO {
    private String id;
    private String name;
    private String billType;
    private String bizType;
    private BigDecimal ordinal;
    private List<BillFetchSchemeCtxParamDTO> params;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillType() {
        return this.billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public List<BillFetchSchemeCtxParamDTO> getParams() {
        return this.params;
    }

    public void setParams(List<BillFetchSchemeCtxParamDTO> params) {
        this.params = params;
    }

    public String toString() {
        return "BillFetchSchemeDTO [id=" + this.id + ", name=" + this.name + ", billType=" + this.billType + ", bizType=" + this.bizType + ", ordinal=" + this.ordinal + ", params=" + this.params + "]";
    }
}

