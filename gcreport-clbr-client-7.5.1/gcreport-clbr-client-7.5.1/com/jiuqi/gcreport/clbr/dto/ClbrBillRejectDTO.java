/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Set;

public class ClbrBillRejectDTO {
    private Set<String> clbrBillIds;
    private String rejectReason;

    public Set<String> getClbrBillIds() {
        return this.clbrBillIds;
    }

    public void setClbrBillIds(Set<String> clbrBillIds) {
        this.clbrBillIds = clbrBillIds;
    }

    public String getRejectReason() {
        return this.rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}

