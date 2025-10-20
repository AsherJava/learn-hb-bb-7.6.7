/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.quickreport.QuickReportException;

public class ReportContextException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public ReportContextException() {
    }

    public ReportContextException(String message) {
        super(message);
    }

    public ReportContextException(Throwable cause) {
        super(cause);
    }

    public ReportContextException(String message, Throwable cause) {
        super(message, cause);
    }
}

