/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.clbr.dto;

import java.util.Set;

public class ClbrBillManualConfirmParamDTO {
    private Set<String> ids;
    private Set<String> oppIds;

    public Set<String> getIds() {
        return this.ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }

    public Set<String> getOppIds() {
        return this.oppIds;
    }

    public void setOppIds(Set<String> oppIds) {
        this.oppIds = oppIds;
    }
}

