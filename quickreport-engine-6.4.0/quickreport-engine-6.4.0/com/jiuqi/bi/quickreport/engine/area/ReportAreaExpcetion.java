/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.ReportEngineException;

public class ReportAreaExpcetion
extends ReportEngineException {
    private static final long serialVersionUID = 1L;

    public ReportAreaExpcetion() {
    }

    public ReportAreaExpcetion(String message) {
        super(message);
    }

    public ReportAreaExpcetion(Throwable cause) {
        super(cause);
    }

    public ReportAreaExpcetion(String message, Throwable cause) {
        super(message, cause);
    }
}

