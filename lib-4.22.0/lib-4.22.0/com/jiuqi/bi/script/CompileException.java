/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

import com.jiuqi.bi.script.ScriptException;

public class CompileException
extends ScriptException {
    private static final long serialVersionUID = -8396645281890206411L;

    public CompileException() {
    }

    public CompileException(Throwable cause) {
        super(cause);
    }

    public CompileException(String message) {
        super(message);
    }

    public CompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompileException(String message, String sourceName, int lineNumber, int columnNumber) {
        super(message, sourceName, lineNumber, columnNumber);
    }

    public CompileException(String message, String sourceName, int lineNumber, int columnNumber, Throwable cause) {
        super(message, sourceName, lineNumber, columnNumber, cause);
    }
}

