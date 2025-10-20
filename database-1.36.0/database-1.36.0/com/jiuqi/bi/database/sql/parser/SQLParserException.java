/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.parser;

public class SQLParserException
extends Exception {
    private static final long serialVersionUID = 2285085499922719628L;

    public SQLParserException(String message) {
        super(message);
    }

    public SQLParserException(Throwable e) {
        super(e);
    }

    public SQLParserException(String message, Throwable e) {
        super(message, e);
    }
}

