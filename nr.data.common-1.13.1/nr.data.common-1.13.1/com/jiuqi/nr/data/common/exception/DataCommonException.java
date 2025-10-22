/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.exception;

import com.jiuqi.nr.data.common.exception.ErrorCode;
import com.jiuqi.nr.data.common.exception.ErrorCodeEnum;

public class DataCommonException
extends RuntimeException {
    private static final long serialVersionUID = -3359687280085219395L;
    protected final ErrorCode errorCode;

    public DataCommonException() {
        super(ErrorCodeEnum.UNSPECIFIED.getDescription());
        this.errorCode = ErrorCodeEnum.UNSPECIFIED;
    }

    public DataCommonException(ErrorCode errorCode) {
        super(DataCommonException.getDescription(errorCode));
        this.errorCode = errorCode;
    }

    public DataCommonException(String detailedMessage) {
        super(detailedMessage);
        this.errorCode = ErrorCodeEnum.UNSPECIFIED;
    }

    public DataCommonException(Throwable t) {
        super(t);
        this.errorCode = ErrorCodeEnum.UNSPECIFIED;
    }

    public DataCommonException(ErrorCode errorCode, String detailedMessage) {
        super(detailedMessage);
        this.errorCode = errorCode;
    }

    public DataCommonException(ErrorCode errorCode, Throwable t) {
        super(errorCode.getDescription(), t);
        this.errorCode = errorCode;
    }

    public DataCommonException(String detailedMessage, Throwable t) {
        super(detailedMessage, t);
        this.errorCode = ErrorCodeEnum.UNSPECIFIED;
    }

    public DataCommonException(ErrorCode errorCode, String detailedMessage, Throwable t) {
        super(detailedMessage, t);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    private static String getDescription(ErrorCode code) {
        return code.bizName() + ":" + code.getDescription();
    }
}

