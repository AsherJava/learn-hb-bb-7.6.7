/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.domain.option;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="OPTION_BILLRULE_ITEM")
public class BillRuleOptionItemDO
extends TenantDO {
    @Id
    private UUID id;
    private String val;
    private UUID parentid;

    public UUID getParentid() {
        return this.parentid;
    }

    public void setParentid(UUID parentid) {
        this.parentid = parentid;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVal() {
        return this.val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}

