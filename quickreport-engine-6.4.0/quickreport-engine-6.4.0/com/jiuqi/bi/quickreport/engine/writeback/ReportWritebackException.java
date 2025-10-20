/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.writeback;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;

public class ReportWritebackException
extends ReportEngineException {
    private static final long serialVersionUID = 1L;

    public ReportWritebackException() {
    }

    public ReportWritebackException(String message) {
        super(message);
    }

    public ReportWritebackException(Throwable cause) {
        super(cause);
    }

    public ReportWritebackException(String message, Throwable cause) {
        super(message, cause);
    }
}

