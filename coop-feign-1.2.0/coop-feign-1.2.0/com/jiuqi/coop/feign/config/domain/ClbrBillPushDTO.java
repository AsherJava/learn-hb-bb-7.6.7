/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO
 */
package com.jiuqi.coop.feign.config.domain;

import com.jiuqi.gcreport.clbr.dto.ClbrBillPushItemParamDTO;

public class ClbrBillPushDTO
extends ClbrBillPushItemParamDTO {
    private Boolean matchCondFlag;

    public Boolean getMatchCondFlag() {
        return this.matchCondFlag;
    }

    public void setMatchCondFlag(Boolean matchCondFlag) {
        this.matchCondFlag = matchCondFlag;
    }

    public String toString() {
        return "ClbrBillPushDTO{matchCondFlag=" + this.matchCondFlag + "} " + super.toString();
    }
}

