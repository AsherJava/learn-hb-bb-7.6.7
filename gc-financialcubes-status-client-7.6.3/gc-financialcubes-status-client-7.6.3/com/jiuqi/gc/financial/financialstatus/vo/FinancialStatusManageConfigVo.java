/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financial.financialstatus.vo;

import com.jiuqi.gc.financial.financialstatus.vo.FinancialStatusConfigVo;

public class FinancialStatusManageConfigVo {
    private Integer index;
    private String code;
    private String name;
    private FinancialStatusConfigVo financialStatusConfigVo;

    public Integer getIndex() {
        return this.index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FinancialStatusConfigVo getFinancialStatusConfigVo() {
        return this.financialStatusConfigVo;
    }

    public void setFinancialStatusConfigVo(FinancialStatusConfigVo financialStatusConfigVo) {
        this.financialStatusConfigVo = financialStatusConfigVo;
    }
}

