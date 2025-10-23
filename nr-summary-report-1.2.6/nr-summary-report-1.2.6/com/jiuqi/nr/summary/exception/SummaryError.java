/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.summary.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public class SummaryError
implements ErrorEnum {
    private final String code;
    private final String message;

    private SummaryError(String code, String message, Object ... args) {
        this.code = code;
        this.message = String.format(message, args);
    }

    private SummaryError(ErrorEnum errorEnum, Object ... args) {
        this.code = errorEnum.getCode();
        this.message = String.format(errorEnum.getMessage(), args);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static SummaryError getInstance(String code, String message, Object ... args) {
        return new SummaryError(code, message, args);
    }

    public static SummaryError getInstance(ErrorEnum errorEnum, Object ... args) {
        return new SummaryError(errorEnum, args);
    }
}

