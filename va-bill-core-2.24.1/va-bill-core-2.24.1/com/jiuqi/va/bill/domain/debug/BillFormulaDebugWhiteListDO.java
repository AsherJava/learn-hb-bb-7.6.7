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

@Table(name="formula_debug_whitelist")
public class BillFormulaDebugWhiteListDO
extends TenantDO {
    @Id
    private String id;
    private Long ver;
    private String formulaname;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVer() {
        return this.ver;
    }

    public void setVer(Long ver) {
        this.ver = ver;
    }

    public String getFormulaname() {
        return this.formulaname;
    }

    public void setFormulaname(String formulaname) {
        this.formulaname = formulaname;
    }
}

