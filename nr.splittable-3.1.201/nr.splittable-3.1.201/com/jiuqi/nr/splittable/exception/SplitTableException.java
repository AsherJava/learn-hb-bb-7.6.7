/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.splittable.exception;

import com.jiuqi.nr.splittable.exception.ExceptionEnum;

public class SplitTableException
extends RuntimeException {
    public SplitTableException() {
        super(ExceptionEnum.ALL_FAILED.getMessage());
    }

    public SplitTableException(String message, Throwable cause) {
        super(message, cause);
    }

    public SplitTableException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public SplitTableException(String msg) {
        super(msg);
    }
}

