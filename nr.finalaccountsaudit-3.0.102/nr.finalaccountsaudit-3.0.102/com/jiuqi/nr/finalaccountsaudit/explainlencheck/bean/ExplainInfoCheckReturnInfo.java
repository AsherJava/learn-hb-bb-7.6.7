/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.finalaccountsaudit.explainlencheck.bean;

import java.io.Serializable;

public class ExplainInfoCheckReturnInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalUnitCount;
    private int errUnitCount;
    private String checkTip;

    public int getTotalUnitCount() {
        return this.totalUnitCount;
    }

    public void setTotalUnitCount(int totalUnitCount) {
        this.totalUnitCount = totalUnitCount;
    }

    public int getErrUnitCount() {
        return this.errUnitCount;
    }

    public void setErrUnitCount(int errUnitCount) {
        this.errUnitCount = errUnitCount;
    }

    public String getCheckTip() {
        return this.checkTip;
    }

    public void setCheckTip(String checkTip) {
        this.checkTip = checkTip;
    }
}

