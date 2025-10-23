/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.exception;

import com.jiuqi.nr.fmdm.exception.FMDMUpdateException;

public class FMDMNoWriteAccessException
extends FMDMUpdateException {
    private static final long serialVersionUID = -733255764524535461L;

    public FMDMNoWriteAccessException(String message) {
        super(message);
    }

    public FMDMNoWriteAccessException(Throwable cause) {
        super(cause);
    }

    public FMDMNoWriteAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}

