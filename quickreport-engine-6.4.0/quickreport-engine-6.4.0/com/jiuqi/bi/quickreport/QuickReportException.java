/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport;

public class QuickReportException
extends Exception {
    private static final long serialVersionUID = 1L;

    public QuickReportException() {
    }

    public QuickReportException(String message) {
        super(message);
    }

    public QuickReportException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public QuickReportException(String message, Throwable cause) {
        super(message, cause);
    }
}

