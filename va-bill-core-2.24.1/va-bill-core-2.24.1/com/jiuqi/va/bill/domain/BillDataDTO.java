/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.domain;

import java.util.List;
import java.util.Map;

@Deprecated
public class BillDataDTO {
    private String defineCode;
    private String billCode;
    private String verifyCode;
    private Map<String, List<Map<String, Object>>> billData;

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

    public String getVerifyCode() {
        return this.verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Map<String, List<Map<String, Object>>> getBillData() {
        return this.billData;
    }

    public void setBillData(Map<String, List<Map<String, Object>>> billData) {
        this.billData = billData;
    }
}

