/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.financialstatus.vo;

public class FinancialStatusInfo {
    private String status;
    private int closeUnitCount;
    private int openUnitCount;

    public FinancialStatusInfo(String status) {
        this.status = status;
    }

    public FinancialStatusInfo(String status, int closeUnitCount, int openUnitCount) {
        this.status = status;
        this.closeUnitCount = closeUnitCount;
        this.openUnitCount = openUnitCount;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCloseUnitCount() {
        return this.closeUnitCount;
    }

    public void setCloseUnitCount(int closeUnitCount) {
        this.closeUnitCount = closeUnitCount;
    }

    public int getOpenUnitCount() {
        return this.openUnitCount;
    }

    public void setOpenUnitCount(int openUnitCount) {
        this.openUnitCount = openUnitCount;
    }
}

