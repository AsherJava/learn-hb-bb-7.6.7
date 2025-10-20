/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.storage;

import com.jiuqi.bi.quickreport.QuickReportException;

public class ReportStorageException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public ReportStorageException() {
    }

    public ReportStorageException(String message) {
        super(message);
    }

    public ReportStorageException(Throwable cause) {
        super(cause);
    }

    public ReportStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}

