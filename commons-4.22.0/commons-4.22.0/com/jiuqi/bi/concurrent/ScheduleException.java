/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.concurrent;

public class ScheduleException
extends Exception {
    private static final long serialVersionUID = -4451841029198447919L;

    public ScheduleException() {
    }

    public ScheduleException(String message) {
        super(message);
    }

    public ScheduleException(Throwable cause) {
        super(cause);
    }

    public ScheduleException(String message, Throwable cause) {
        super(message, cause);
    }
}

