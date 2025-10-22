/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.np.common.exception.ErrorEnum;

public class ErrorEnumSub
implements ErrorEnum {
    String code;
    String message;

    public ErrorEnumSub(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static ErrorEnumSub create(Exception e) {
        return new ErrorEnumSub("500", e.getClass().getName() + ":" + e.getMessage());
    }
}

