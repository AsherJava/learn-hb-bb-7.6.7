/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

public class DefineQuerySqlParseInfoVO {
    private int startLineNumber;
    private int endLineNumber;
    private int startColumn;
    private int endColumn;
    private boolean existSqlErrorLine;
    private String message;

    public int getStartLineNumber() {
        return this.startLineNumber;
    }

    public void setStartLineNumber(int startLineNumber) {
        this.startLineNumber = startLineNumber;
    }

    public int getEndLineNumber() {
        return this.endLineNumber;
    }

    public void setEndLineNumber(int endLineNumber) {
        this.endLineNumber = endLineNumber;
    }

    public int getStartColumn() {
        return this.startColumn;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    public void setEndColumn(int endColumn) {
        this.endColumn = endColumn;
    }

    public boolean isExistSqlErrorLine() {
        return this.existSqlErrorLine;
    }

    public void setExistSqlErrorLine(boolean existSqlErrorLine) {
        this.existSqlErrorLine = existSqlErrorLine;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

