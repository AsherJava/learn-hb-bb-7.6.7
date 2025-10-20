/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.build;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;

public class ReportBuildException
extends ReportEngineException {
    private static final long serialVersionUID = 1L;

    public ReportBuildException() {
    }

    public ReportBuildException(String message) {
        super(message);
    }

    public ReportBuildException(Throwable cause) {
        super(cause);
    }

    public ReportBuildException(String message, Throwable cause) {
        super(message, cause);
    }
}

