/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.migration.transferdata.bean;

import com.jiuqi.nr.migration.transferdata.bean.TransZbValue;
import java.util.Map;

public class TransFloatRowZbValue {
    private double floatOrder;
    private Map<String, String> code2Idx;
    private Map<String, TransZbValue> idx2valueInfo;

    public double getFloatOrder() {
        return this.floatOrder;
    }

    public void setFloatOrder(double floatOrder) {
        this.floatOrder = floatOrder;
    }

    public Map<String, String> getCode2Idx() {
        return this.code2Idx;
    }

    public void setCode2Idx(Map<String, String> code2Idx) {
        this.code2Idx = code2Idx;
    }

    public Map<String, TransZbValue> getIdx2valueInfo() {
        return this.idx2valueInfo;
    }

    public void setIdx2valueInfo(Map<String, TransZbValue> idx2valueInfo) {
        this.idx2valueInfo = idx2valueInfo;
    }
}

