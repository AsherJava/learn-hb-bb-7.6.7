/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.parser;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;

public class ReportExpressionException
extends ReportEngineException {
    private static final long serialVersionUID = 1L;

    public ReportExpressionException() {
    }

    public ReportExpressionException(String message) {
        super(message);
    }

    public ReportExpressionException(Throwable cause) {
        super(cause);
    }

    public ReportExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}

