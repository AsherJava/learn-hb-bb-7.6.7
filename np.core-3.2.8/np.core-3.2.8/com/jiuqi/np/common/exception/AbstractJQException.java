/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.common.exception;

import com.jiuqi.np.common.exception.JQError;

public abstract class AbstractJQException
extends Exception
implements JQError {
    private static final long serialVersionUID = -4107811452339184990L;

    protected AbstractJQException() {
    }

    protected AbstractJQException(String message) {
        super(message);
    }

    protected AbstractJQException(Throwable cause) {
        super(cause);
    }

    protected AbstractJQException(String message, Throwable cause) {
        super(message, cause);
    }
}

