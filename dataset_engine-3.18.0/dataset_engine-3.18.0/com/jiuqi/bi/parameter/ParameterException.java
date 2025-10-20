/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.parameter;

public class ParameterException
extends Exception {
    private static final long serialVersionUID = 7494105435293899824L;

    public ParameterException() {
    }

    public ParameterException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ParameterException(String msg) {
        super(msg);
    }

    public ParameterException(String msg, Throwable cause) {
        super(msg, cause);
    }
}

