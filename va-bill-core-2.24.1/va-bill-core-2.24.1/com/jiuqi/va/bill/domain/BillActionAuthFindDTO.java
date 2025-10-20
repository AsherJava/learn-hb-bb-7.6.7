/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillActionAuthFindDTO
extends TenantDO {
    private String defineName;
    private String[] actNames;

    public String[] getActNames() {
        return this.actNames;
    }

    public void setActNames(String[] actNames) {
        this.actNames = actNames;
    }

    public String getDefineName() {
        return this.defineName;
    }

    public void setDefineName(String defineName) {
        this.defineName = defineName;
    }
}

