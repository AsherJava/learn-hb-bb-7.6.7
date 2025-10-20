/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.IOException;

public class FileLockException
extends IOException {
    private static final long serialVersionUID = 1L;

    public FileLockException() {
    }

    public FileLockException(String message) {
        super(message);
    }

    public FileLockException(Throwable cause) {
        super(cause);
    }

    public FileLockException(String message, Throwable cause) {
        super(message, cause);
    }
}

