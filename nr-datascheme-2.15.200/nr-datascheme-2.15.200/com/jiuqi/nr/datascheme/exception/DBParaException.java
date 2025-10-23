/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 */
package com.jiuqi.nr.datascheme.exception;

import org.springframework.dao.DataAccessException;

public class DBParaException
extends DataAccessException {
    private static final long serialVersionUID = 7502385790410148817L;

    public DBParaException() {
        super("\u67e5\u8be2\u53c2\u6570\u5f02\u5e38");
    }

    public DBParaException(String message) {
        super(message);
    }

    public DBParaException(Throwable e) {
        super(e.getMessage(), e);
    }
}

