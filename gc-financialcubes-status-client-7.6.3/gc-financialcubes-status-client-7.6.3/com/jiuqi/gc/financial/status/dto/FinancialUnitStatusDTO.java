/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.status.dto;

import com.jiuqi.gc.financial.status.enums.FinancialStatusEnum;
import java.util.Date;

public class FinancialUnitStatusDTO {
    private String dataTime;
    private String unitCode;
    private FinancialStatusEnum status;
    private Date validTime;
    private Date invalidTime;

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public FinancialStatusEnum getStatus() {
        return this.status;
    }

    public void setStatus(FinancialStatusEnum status) {
        this.status = status;
    }

    public Date getValidTime() {
        return this.validTime;
    }

    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    public Date getInvalidTime() {
        return this.invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}

