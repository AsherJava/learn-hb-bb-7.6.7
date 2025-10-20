/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.coop.feign.config.domain;

import com.jiuqi.coop.feign.config.domain.ClbrRecordDO;
import java.util.List;

public class ClbrRecordDTO
extends ClbrRecordDO {
    private String operateType;
    private Boolean matchCondFlag;
    private List<String> billrowids;

    public Boolean getMatchCondFlag() {
        return this.matchCondFlag;
    }

    public void setMatchCondFlag(Boolean matchCondFlag) {
        this.matchCondFlag = matchCondFlag;
    }

    public String getOperateType() {
        return this.operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public List<String> getBillrowids() {
        return this.billrowids;
    }

    public void setBillrowids(List<String> billrowids) {
        this.billrowids = billrowids;
    }
}

