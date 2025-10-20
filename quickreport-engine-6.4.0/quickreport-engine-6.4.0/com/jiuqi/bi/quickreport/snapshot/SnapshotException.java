/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.snapshot;

import com.jiuqi.bi.quickreport.QuickReportException;

public class SnapshotException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public SnapshotException() {
    }

    public SnapshotException(String message) {
        super(message);
    }

    public SnapshotException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public SnapshotException(String message, Throwable cause) {
        super(message, cause);
    }
}

