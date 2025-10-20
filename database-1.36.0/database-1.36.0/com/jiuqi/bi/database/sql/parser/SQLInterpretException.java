/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.sql.parser;

import com.jiuqi.bi.database.DBException;

public class SQLInterpretException
extends DBException {
    private static final long serialVersionUID = 2506570806674944093L;

    public SQLInterpretException(String message) {
        super(message);
    }

    public SQLInterpretException(Throwable e) {
        super(e);
    }

    public SQLInterpretException(String message, Throwable e) {
        super(message, e);
    }
}

