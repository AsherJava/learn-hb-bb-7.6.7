/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.QuickReportException;

public class WritebackException
extends QuickReportException {
    private static final long serialVersionUID = 1L;

    public WritebackException() {
    }

    public WritebackException(String message) {
        super(message);
    }

    public WritebackException(Throwable cause) {
        super(cause);
    }

    public WritebackException(String message, Throwable cause) {
        super(message, cause);
    }
}

