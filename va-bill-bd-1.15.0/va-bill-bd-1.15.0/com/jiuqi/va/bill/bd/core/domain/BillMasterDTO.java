/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.bill.bd.core.domain;

import com.jiuqi.va.mapper.domain.TenantDO;

public class BillMasterDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String billcode;
    private String definecode;
    private String srcbillcode;
    private String srcbilldefine;

    public String getSrcbilldefine() {
        return this.srcbilldefine;
    }

    public void setSrcbilldefine(String srcbilldefine) {
        this.srcbilldefine = srcbilldefine;
    }

    public String getSrcbillcode() {
        return this.srcbillcode;
    }

    public void setSrcbillcode(String srcbillcode) {
        this.srcbillcode = srcbillcode;
    }

    public String getBillcode() {
        return this.billcode;
    }

    public void setBillcode(String billcode) {
        this.billcode = billcode;
    }

    public String getDefinecode() {
        return this.definecode;
    }

    public void setDefinecode(String definecode) {
        this.definecode = definecode;
    }
}

