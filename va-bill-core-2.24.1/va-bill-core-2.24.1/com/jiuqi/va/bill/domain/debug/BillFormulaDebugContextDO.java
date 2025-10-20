/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.domain.debug;

import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="formula_debug_context")
public class BillFormulaDebugContextDO
extends TenantDO {
    @Id
    private String id;
    private String contextdata;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContextdata() {
        return this.contextdata;
    }

    public void setContextdata(String contextdata) {
        this.contextdata = contextdata;
    }
}

