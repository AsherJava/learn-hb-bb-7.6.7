/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport;

public class QuickReportError
extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public QuickReportError() {
    }

    public QuickReportError(String message) {
        super(message);
    }

    public QuickReportError(Throwable cause) {
        super(cause);
    }

    public QuickReportError(String message, Throwable cause) {
        super(message, cause);
    }
}

