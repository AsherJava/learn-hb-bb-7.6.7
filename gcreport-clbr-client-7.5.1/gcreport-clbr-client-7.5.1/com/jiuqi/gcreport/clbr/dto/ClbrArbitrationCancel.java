/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Set;

public class ClbrArbitrationCancel {
    private Set<String> clbrBillIds;
    private String arbitrationReject;

    public String getArbitrationReject() {
        return this.arbitrationReject;
    }

    public void setArbitrationReject(String arbitrationReject) {
        this.arbitrationReject = arbitrationReject;
    }

    public Set<String> getClbrBillIds() {
        return this.clbrBillIds;
    }

    public void setClbrBillIds(Set<String> clbrBillIds) {
        this.clbrBillIds = clbrBillIds;
    }
}

