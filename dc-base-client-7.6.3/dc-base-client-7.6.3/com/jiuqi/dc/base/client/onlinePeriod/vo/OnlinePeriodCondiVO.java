/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.client.onlinePeriod.vo;

public class OnlinePeriodCondiVO {
    private String moduleCode;
    private String unitCode;

    public OnlinePeriodCondiVO() {
    }

    public OnlinePeriodCondiVO(String moduleCode, String unitCode) {
        this.moduleCode = moduleCode;
        this.unitCode = unitCode;
    }

    public String getModuleCode() {
        return this.moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }
}

