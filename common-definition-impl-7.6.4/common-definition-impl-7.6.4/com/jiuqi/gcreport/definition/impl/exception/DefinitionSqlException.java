/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.exception;

public class DefinitionSqlException
extends RuntimeException {
    private static final long serialVersionUID = -8251484725458089774L;

    public DefinitionSqlException(String message) {
        super(message);
    }

    public DefinitionSqlException(String message, Throwable e) {
        super(message, e);
    }

    public DefinitionSqlException(Throwable e) {
        super(e);
    }
}

