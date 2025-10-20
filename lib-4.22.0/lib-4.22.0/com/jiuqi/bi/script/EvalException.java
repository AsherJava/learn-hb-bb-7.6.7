/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

import com.jiuqi.bi.script.ScriptException;

public class EvalException
extends ScriptException {
    private static final long serialVersionUID = -2749174852931138970L;

    public EvalException() {
    }

    public EvalException(Throwable cause) {
        super(cause);
    }

    public EvalException(String message) {
        super(message);
    }

    public EvalException(String message, Throwable cause) {
        super(message, cause);
    }

    public EvalException(String message, String sourceName, int lineNumber, int columnNumber) {
        super(message, sourceName, lineNumber, columnNumber);
    }

    public EvalException(String message, String sourceName, int lineNumber, int columnNumber, Throwable cause) {
        super(message, sourceName, lineNumber, columnNumber, cause);
    }
}

