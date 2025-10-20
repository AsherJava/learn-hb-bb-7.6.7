/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillAppParamDTO
extends TenantDO {
    private String billCode;
    private String defineCode;
    private String verifyCodeByUserName;

    public String getBillCode() {
        return this.billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public String getVerifyCodeByUserName() {
        return this.verifyCodeByUserName;
    }

    public void setVerifyCodeByUserName(String verifyCodeByUserName) {
        this.verifyCodeByUserName = verifyCodeByUserName;
    }
}

