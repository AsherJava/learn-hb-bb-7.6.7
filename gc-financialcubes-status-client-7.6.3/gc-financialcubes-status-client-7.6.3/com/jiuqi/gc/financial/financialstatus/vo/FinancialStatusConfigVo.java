/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.financialstatus.vo;

public class FinancialStatusConfigVo {
    private boolean isSelectOrgType;
    private boolean isSelectPeriodType;

    public FinancialStatusConfigVo(boolean isSelectOrgType, boolean isSelectPeriodType) {
        this.isSelectOrgType = isSelectOrgType;
        this.isSelectPeriodType = isSelectPeriodType;
    }

    public boolean isSelectOrgType() {
        return this.isSelectOrgType;
    }

    public void setSelectOrgType(boolean selectOrgType) {
        this.isSelectOrgType = selectOrgType;
    }

    public boolean isSelectPeriodType() {
        return this.isSelectPeriodType;
    }

    public void setSelectPeriodType(boolean selectPeriodType) {
        this.isSelectPeriodType = selectPeriodType;
    }
}

