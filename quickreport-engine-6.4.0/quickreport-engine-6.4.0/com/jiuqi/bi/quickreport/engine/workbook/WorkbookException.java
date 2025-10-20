/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.workbook;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;

public class WorkbookException
extends ReportEngineException {
    private static final long serialVersionUID = 1L;

    public WorkbookException() {
    }

    public WorkbookException(String message) {
        super(message);
    }

    public WorkbookException(Throwable cause) {
        super(cause);
    }

    public WorkbookException(String message, Throwable cause) {
        super(message, cause);
    }
}

