/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.tz.exception;

import com.jiuqi.nr.io.tz.exception.TzImportException;

public class ParamCheckException
extends TzImportException {
    public ParamCheckException() {
    }

    public ParamCheckException(Throwable cause) {
        super(cause);
    }

    public ParamCheckException(String message) {
        super(message);
    }

    public ParamCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}

