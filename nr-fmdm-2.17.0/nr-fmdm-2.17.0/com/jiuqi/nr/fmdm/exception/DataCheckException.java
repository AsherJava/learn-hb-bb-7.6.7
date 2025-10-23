/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.exception;

import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;

public class DataCheckException
extends FMDMUpdateException {
    public DataCheckException(String message) {
        super(message);
    }

    public DataCheckException(Throwable cause) {
        super(cause);
    }

    public DataCheckException(String message, Throwable cause) {
        super(message, cause);
    }
}

