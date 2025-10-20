/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.print;

import com.jiuqi.bi.quickreport.QuickReportException;

public class PrintException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public PrintException() {
    }

    public PrintException(String message) {
        super(message);
    }

    public PrintException(Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public PrintException(String message, Throwable cause) {
        super(message, cause);
    }
}

