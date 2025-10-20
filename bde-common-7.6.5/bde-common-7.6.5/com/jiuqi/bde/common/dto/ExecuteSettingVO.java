/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 */
package com.jiuqi.bde.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bde.common.dto.FetchSettingVO;

@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class ExecuteSettingVO
extends FetchSettingVO {
    private Integer floatOrder;
    private String fieldDefineTitle;
    private String vchrUniqueCode;
    private String investedUnit;

    public Integer getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(Integer floatOrder) {
        this.floatOrder = floatOrder;
    }

    public String getFieldDefineTitle() {
        return this.fieldDefineTitle;
    }

    public void setFieldDefineTitle(String fieldDefineTitle) {
        this.fieldDefineTitle = fieldDefineTitle;
    }

    public String getVchrUniqueCode() {
        return this.vchrUniqueCode;
    }

    public void setVchrUniqueCode(String vchrUniqueCode) {
        this.vchrUniqueCode = vchrUniqueCode;
    }

    public String getInvestedUnit() {
        return this.investedUnit;
    }

    public void setInvestedUnit(String investedUnit) {
        this.investedUnit = investedUnit;
    }

    @Override
    public String toString() {
        return "ExecuteSettingVO{floatOrder=" + this.floatOrder + ", fieldDefineTitle='" + this.fieldDefineTitle + '\'' + ", vchrUniqueCode='" + this.vchrUniqueCode + '\'' + ", investedUnit='" + this.investedUnit + '\'' + '}';
    }
}

