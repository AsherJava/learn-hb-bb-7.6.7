/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.script;

public class ScriptException
extends Exception {
    private static final long serialVersionUID = -237846942385256529L;
    private String sourceName = null;
    private int lineNumber = -1;
    private int columnNumber = -1;

    public ScriptException() {
    }

    public ScriptException(Throwable cause) {
        super(cause);
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }

    public ScriptException(String message, String sourceName, int lineNumber, int columnNumber) {
        super(message);
        this.sourceName = sourceName;
        this.columnNumber = columnNumber;
        this.lineNumber = lineNumber;
    }

    public ScriptException(String message, String sourceName, int lineNumber, int columnNumber, Throwable cause) {
        super(message, cause);
        this.sourceName = sourceName;
        this.columnNumber = columnNumber;
        this.lineNumber = lineNumber;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public int getColumnNumber() {
        return this.columnNumber;
    }

    @Override
    public String getMessage() {
        String ret = super.getMessage();
        if (this.sourceName != null) {
            ret = ret + " in " + this.sourceName;
            if (this.lineNumber != -1) {
                ret = ret + " at line number " + this.lineNumber;
            }
            if (this.columnNumber != -1) {
                ret = ret + " at column number " + this.columnNumber;
            }
        }
        return ret;
    }
}

