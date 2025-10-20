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
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="BILL_LOCK")
public class BillLockDO
extends TenantDO {
    private static final long serialVersionUID = 5961151941707923944L;
    @Id
    private String billcode;

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }
}

