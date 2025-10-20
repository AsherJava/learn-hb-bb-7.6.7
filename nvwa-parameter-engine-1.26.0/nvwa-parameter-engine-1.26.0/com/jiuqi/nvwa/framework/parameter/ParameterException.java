/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter;

public class ParameterException
extends Exception {
    private static final long serialVersionUID = 1L;

    public ParameterException(String message) {
        super(message);
    }

    public ParameterException(Throwable e) {
        super(e);
    }

    public ParameterException(String message, Throwable e) {
        super(message, e);
    }
}

