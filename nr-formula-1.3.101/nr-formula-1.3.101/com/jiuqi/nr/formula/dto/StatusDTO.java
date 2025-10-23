/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.task.api.common.Constants$DataStatus
 */
package com.jiuqi.nr.formula.dto;

import com.jiuqi.nr.task.api.common.Constants;

public class StatusDTO {
    private Constants.DataStatus status = Constants.DataStatus.NONE;

    public Constants.DataStatus getStatus() {
        return this.status;
    }

    public void setStatus(Constants.DataStatus status) {
        this.status = status;
    }
}

