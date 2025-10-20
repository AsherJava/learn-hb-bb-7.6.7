/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.export;

import com.jiuqi.bi.quickreport.QuickReportException;

public class ExportException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public ExportException() {
    }

    public ExportException(String message) {
        super(message);
    }

    public ExportException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public ExportException(String message, Throwable cause) {
        super(message, cause);
    }
}

