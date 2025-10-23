/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.execute.runtime;

import com.jiuqi.nr.workflow2.service.execute.runtime.IExecuteTimeSetting;

public class ExecuteTimeSetting
implements IExecuteTimeSetting {
    private boolean inTimeRange;
    private String tipMessage;

    @Override
    public boolean isInTimeRange() {
        return this.inTimeRange;
    }

    public void setInTimeRange(boolean inTimeRange) {
        this.inTimeRange = inTimeRange;
    }

    @Override
    public String getTipMessage() {
        return this.tipMessage;
    }

    public void setTipMessage(String tipMessage) {
        this.tipMessage = tipMessage;
    }
}

