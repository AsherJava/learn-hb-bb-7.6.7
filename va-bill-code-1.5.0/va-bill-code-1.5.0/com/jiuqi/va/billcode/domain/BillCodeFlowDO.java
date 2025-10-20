/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  javax.persistence.Column
 *  javax.persistence.Id
 *  javax.persistence.Table
 */
package com.jiuqi.va.billcode.domain;

import com.jiuqi.va.mapper.domain.TenantDO;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="MD_BILLCODE_FLOW")
public class BillCodeFlowDO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    @Id
    private String dimensions;
    @Column(name="FLOWNUMBER")
    private Long flowNumber;

    public String getDimensions() {
        return this.dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public Long getFlowNumber() {
        return this.flowNumber;
    }

    public void setFlowNumber(Long flowNumber) {
        this.flowNumber = flowNumber;
    }
}

