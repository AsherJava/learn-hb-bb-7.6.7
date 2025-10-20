/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.bill.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="BILL_CHANGEREC_DATA")
public class BillChangeRecDataDO
extends TenantDO {
    @Id
    private UUID id;
    private String optdata;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getOptdata() {
        return this.optdata;
    }

    public void setOptdata(String optdata) {
        this.optdata = optdata;
    }
}

