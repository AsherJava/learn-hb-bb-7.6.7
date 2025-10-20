/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.context;

import com.jiuqi.bi.quickreport.engine.context.ReportContextException;

public class ReportNotFoundException
extends ReportContextException {
    private static final long serialVersionUID = -5895105277432527566L;

    public ReportNotFoundException() {
    }

    public ReportNotFoundException(String message) {
        super(message);
    }

    public ReportNotFoundException(Throwable cause) {
        super(cause);
    }

    public ReportNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

