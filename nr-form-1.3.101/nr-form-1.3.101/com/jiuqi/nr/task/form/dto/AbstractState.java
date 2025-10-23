/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.task.form.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nr.task.api.common.Constants;

public abstract class AbstractState {
    private int state = Constants.DataStatus.NONE.ordinal();

    public int getState() {
        return this.state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @JsonIgnore
    public final Constants.DataStatus getStatus() {
        Constants.DataStatus[] values = Constants.DataStatus.values();
        if (this.state >= 0 && this.state < values.length) {
            return values[this.state];
        }
        return Constants.DataStatus.NONE;
    }
}

