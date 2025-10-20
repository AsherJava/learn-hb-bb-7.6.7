/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine;

import com.jiuqi.bi.quickreport.QuickReportException;

public class ReportEngineException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public ReportEngineException() {
    }

    public ReportEngineException(String message) {
        super(message);
    }

    public ReportEngineException(Throwable cause) {
        super(cause);
    }

    public ReportEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}

