/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.coop.feign.config.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillCreateClbrDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String defineCode;
    private String billCode;
    private Integer templateType;

    public BillCreateClbrDTO() {
    }

    public BillCreateClbrDTO(String defineCode, String billCode, Integer templateType) {
        this.defineCode = defineCode;
        this.billCode = billCode;
        this.templateType = templateType;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Integer getTemplateType() {
        return this.templateType;
    }

    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }

    public String toString() {
        return "BillCreateClbrDTO{defineCode='" + this.defineCode + '\'' + ", billCode='" + this.billCode + '\'' + ", templateType=" + this.templateType + '}';
    }
}

