/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain.debug;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillFormulaDebugContextVO
extends TenantDO {
    private String defineCode;
    private Object contextData;

    public String getDefineCode() {
        return this.defineCode;
    }

    public void setDefineCode(String defineCode) {
        this.defineCode = defineCode;
    }

    public Object getContextData() {
        return this.contextData;
    }

    public void setContextData(Object contextData) {
        this.contextData = contextData;
    }
}

