/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.AbstractJQException
 */
package com.jiuqi.nr.summary.exception;

import com.jiuqi.np.common.exception.AbstractJQException;
import com.jiuqi.nr.summary.exception.SummaryErrorEnum;

public class SummaryCommonException
extends AbstractJQException {
    private String code;
    private String message;
    private Object data;

    public SummaryCommonException(SummaryErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMessage();
    }

    public SummaryCommonException(SummaryErrorEnum errorEnum, Object ... args) {
        this.code = errorEnum.getCode();
        this.message = String.format(errorEnum.getMessage(), args);
    }

    public String getErrorCode() {
        return this.code;
    }

    public String getErrorMessage() {
        return this.message;
    }

    public Object getErrorData() {
        return this.data;
    }
}

