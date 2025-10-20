/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.gcreport.billextract.client.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class BillFetchSchemeCtxParamDTO {
    private String id;
    private String srcField;
    private String targetField;
    private BigDecimal ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcField() {
        return this.srcField;
    }

    public void setSrcField(String srcField) {
        this.srcField = srcField;
    }

    public String getTargetField() {
        return this.targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }

    public String toString() {
        return "BillFetchSchemeParamDTO [id=" + this.id + ", srcField=" + this.srcField + ", targetField=" + this.targetField + ", ordinal=" + this.ordinal + "]";
    }
}

