/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.budget.common.domain.DefaultTenantDTO
 */
package com.jiuqi.budget.simpleparameters;

import com.jiuqi.budget.common.domain.DefaultTenantDTO;

public class IntegerParam
implements DefaultTenantDTO {
    private Integer val;

    public IntegerParam(Integer val) {
        this.val = val;
    }

    public Integer getVal() {
        return this.val;
    }

    public void setVal(Integer val) {
        this.val = val;
    }
}

