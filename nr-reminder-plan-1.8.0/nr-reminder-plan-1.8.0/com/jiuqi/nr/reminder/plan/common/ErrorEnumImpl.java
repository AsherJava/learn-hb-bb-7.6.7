/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.reminder.plan.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public class ErrorEnumImpl
implements ErrorEnum {
    private String message;

    public ErrorEnumImpl() {
    }

    public ErrorEnumImpl(String message) {
        this.message = message;
    }

    public String getCode() {
        return "0";
    }

    public String getMessage() {
        return this.message;
    }
}

