/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.fix.exception;

public class DataSchemeDeployFixException
extends RuntimeException {
    public DataSchemeDeployFixException(String message) {
        super(message);
    }

    public DataSchemeDeployFixException(Throwable e) {
        super(e.getMessage(), e);
    }

    public DataSchemeDeployFixException(String message, Throwable e) {
        super(message, e);
    }
}

