/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.io;

import java.io.IOException;

public class BIFFErrorException
extends IOException {
    private static final long serialVersionUID = -3949093452514403577L;

    public BIFFErrorException() {
    }

    public BIFFErrorException(String message) {
        super(message);
    }

    public BIFFErrorException(Throwable cause) {
        super(cause);
    }

    public BIFFErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

