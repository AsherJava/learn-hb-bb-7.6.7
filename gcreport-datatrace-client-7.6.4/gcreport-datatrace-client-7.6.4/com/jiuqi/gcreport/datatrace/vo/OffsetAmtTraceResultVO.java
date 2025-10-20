/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.datatrace.vo;

import com.jiuqi.gcreport.datatrace.vo.OffsetAmtTraceItemVO;
import java.util.List;

public class OffsetAmtTraceResultVO {
    private String function;
    private Double amt;
    List<OffsetAmtTraceItemVO> offsetAmtTraceItems;

    public String getFunction() {
        return this.function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public List<OffsetAmtTraceItemVO> getOffsetAmtTraceItems() {
        return this.offsetAmtTraceItems;
    }

    public void setOffsetAmtTraceItems(List<OffsetAmtTraceItemVO> offsetAmtTraceItems) {
        this.offsetAmtTraceItems = offsetAmtTraceItems;
    }

    public Double getAmt() {
        return this.amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String toString() {
        return "OffsetAmtTraceResultVO{function='" + this.function + '\'' + ", amt=" + this.amt + ", offsetAmtTraceItems=" + this.offsetAmtTraceItems + '}';
    }
}

