/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.interaction;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;

public class ReportInteractionException
extends ReportEngineException {
    private static final long serialVersionUID = 1L;

    public ReportInteractionException() {
    }

    public ReportInteractionException(String message) {
        super(message);
    }

    public ReportInteractionException(Throwable cause) {
        super(cause);
    }

    public ReportInteractionException(String message, Throwable cause) {
        super(message, cause);
    }
}

