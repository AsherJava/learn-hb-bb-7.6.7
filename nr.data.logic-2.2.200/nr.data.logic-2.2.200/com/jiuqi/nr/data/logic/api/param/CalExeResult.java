/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.api.param;

import com.jiuqi.nr.data.logic.api.param.CalState;

public class CalExeResult {
    CalState calState;

    public CalExeResult(CalState calState) {
        this.calState = calState;
    }

    public CalState getCalState() {
        return this.calState;
    }

    public void setCalState(CalState calState) {
        this.calState = calState;
    }
}

