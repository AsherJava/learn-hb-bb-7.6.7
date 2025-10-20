/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.impl.expimp.common;

import com.jiuqi.bde.penetrate.impl.expimp.common.VoucherPenetrateExportRow;
import java.util.Map;

public class PenetrateExcelExportParam {
    private String bizModel;
    private String penetrateType;
    private Map<String, Object> condi;
    private VoucherPenetrateExportRow voucherRow;

    public VoucherPenetrateExportRow getVoucherRow() {
        return this.voucherRow;
    }

    public void setVoucherRow(VoucherPenetrateExportRow voucherRow) {
        this.voucherRow = voucherRow;
    }

    public String getBizModel() {
        return this.bizModel;
    }

    public void setBizModel(String bizModel) {
        this.bizModel = bizModel;
    }

    public String getPenetrateType() {
        return this.penetrateType;
    }

    public void setPenetrateType(String penetrateType) {
        this.penetrateType = penetrateType;
    }

    public Map<String, Object> getCondi() {
        return this.condi;
    }

    public void setCondi(Map<String, Object> condi) {
        this.condi = condi;
    }
}

