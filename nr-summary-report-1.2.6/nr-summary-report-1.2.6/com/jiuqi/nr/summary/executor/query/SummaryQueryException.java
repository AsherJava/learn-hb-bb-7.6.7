/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.executor.query;

import com.jiuqi.nr.summary.executor.SummaryExecuteException;

public class SummaryQueryException
extends SummaryExecuteException {
    public SummaryQueryException() {
    }

    public SummaryQueryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SummaryQueryException(String message) {
        super(message);
    }

    public SummaryQueryException(Throwable cause) {
        super(cause);
    }
}

