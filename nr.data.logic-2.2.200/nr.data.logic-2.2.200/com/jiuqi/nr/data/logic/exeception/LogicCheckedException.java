/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.exeception;

public class LogicCheckedException
extends Exception {
    private static final long serialVersionUID = -5947758016694361583L;

    public LogicCheckedException(String errorCode) {
        super(errorCode);
    }

    public LogicCheckedException(String message, Throwable cause) {
        super(message, cause);
    }
}

