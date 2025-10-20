/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.model;

import com.jiuqi.bi.quickreport.QuickReportException;

public class ReportModelException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public ReportModelException() {
    }

    public ReportModelException(String message) {
        super(message);
    }

    public ReportModelException(Throwable cause) {
        super(cause);
    }

    public ReportModelException(String message, Throwable cause) {
        super(message, cause);
    }
}

