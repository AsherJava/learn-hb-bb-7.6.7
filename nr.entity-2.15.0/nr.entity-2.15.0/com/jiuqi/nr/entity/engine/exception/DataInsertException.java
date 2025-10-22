/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.exception;

import java.io.Serializable;

public final class DataInsertException
extends RuntimeException
implements Serializable {
    private static final long serialVersionUID = -4760335476375889452L;
    public static final String INVALID_CAST_ERROR = "\u6570\u636e\u7c7b\u578b\u8f6c\u6362\u65e0\u6548";

    public DataInsertException() {
        super(INVALID_CAST_ERROR);
    }

    public DataInsertException(String arg0) {
        super(arg0);
    }

    public DataInsertException(Exception ex) {
        super(ex);
    }
}

