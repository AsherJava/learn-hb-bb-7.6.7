/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.file.exception;

import com.jiuqi.nr.file.exception.FileException;

public class FileSizeOutOfLimitException
extends FileException {
    private static final long serialVersionUID = -3689079963757937926L;
    private long limit;

    public FileSizeOutOfLimitException(long limit) {
        super("file size out of limit, must little than " + limit + ".");
        this.limit = limit;
    }

    public long getLimit() {
        return this.limit;
    }
}

