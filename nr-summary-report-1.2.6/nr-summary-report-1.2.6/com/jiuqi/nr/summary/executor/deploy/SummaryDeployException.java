/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.executor.deploy;

import com.jiuqi.nr.summary.executor.SummaryExecuteException;

public class SummaryDeployException
extends SummaryExecuteException {
    public SummaryDeployException() {
    }

    public SummaryDeployException(String message, Throwable cause) {
        super(message, cause);
    }

    public SummaryDeployException(String message) {
        super(message);
    }

    public SummaryDeployException(Throwable cause) {
        super(cause);
    }
}

