/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.datasource;

public class OuterDataSourceNotExistedException
extends RuntimeException {
    private static final long serialVersionUID = -512838182238248790L;

    public OuterDataSourceNotExistedException(String message) {
        super(message);
    }

    public OuterDataSourceNotExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public OuterDataSourceNotExistedException(Throwable cause) {
        super(cause);
    }
}

