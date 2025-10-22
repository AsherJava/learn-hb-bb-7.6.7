/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.entity;

import java.math.BigDecimal;

public class BillExtractDefineEO {
    public static final String TABLENAME = "BDE_BILLEXTRACT_DEFINE";
    private String id;
    private String billSettingType;
    private BigDecimal ordinal;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillSettingType() {
        return this.billSettingType;
    }

    public void setBillSettingType(String billSettingType) {
        this.billSettingType = billSettingType;
    }

    public BigDecimal getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(BigDecimal ordinal) {
        this.ordinal = ordinal;
    }
}

