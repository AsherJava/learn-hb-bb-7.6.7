/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formula.web.param;

import com.jiuqi.nr.formula.web.param.FormulaListPM;

public class FormulaQueryPM
extends FormulaListPM {
    private String tailKey;
    private Integer queryCount;

    public String getTailKey() {
        return this.tailKey;
    }

    public void setTailKey(String tailKey) {
        this.tailKey = tailKey;
    }

    public Integer getQueryCount() {
        return this.queryCount;
    }

    public void setQueryCount(Integer queryCount) {
        this.queryCount = queryCount;
    }
}

