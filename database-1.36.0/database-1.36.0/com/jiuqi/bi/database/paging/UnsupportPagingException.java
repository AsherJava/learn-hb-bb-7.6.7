/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.database.paging;

import com.jiuqi.bi.database.DBException;

public class UnsupportPagingException
extends DBException {
    private static final long serialVersionUID = -6637199621877480253L;

    public UnsupportPagingException() {
    }

    public UnsupportPagingException(String message) {
        super(message);
    }
}

