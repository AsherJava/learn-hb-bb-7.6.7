/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.DefaultTenantDTO
 */
package com.jiuqi.budget.simpleparameters;

import com.jiuqi.budget.common.domain.DefaultTenantDTO;

public class UUIDParam
implements DefaultTenantDTO {
    private String val;

    public UUIDParam(String val) {
        this.val = val;
    }

    public String getVal() {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}

