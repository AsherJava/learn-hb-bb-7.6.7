/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.exception.SchemeDataException
 */
package com.jiuqi.nr.datascheme.adjustment.exception;

import com.jiuqi.nr.datascheme.api.exception.SchemeDataException;

public class AdjustPeriodException
extends SchemeDataException {
    private static final long serialVersionUID = 1490015185791364270L;

    public AdjustPeriodException() {
    }

    public AdjustPeriodException(String message) {
        super(message);
    }

    public AdjustPeriodException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdjustPeriodException(Throwable cause) {
        super(cause);
    }
}

