/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.exceptions;

public class CloseException
extends RuntimeException {
    public CloseException() {
    }

    public CloseException(String msg) {
        super(msg);
    }

    public CloseException(Exception e) {
        super(e);
    }

    public CloseException(String msg, Exception e) {
        super(msg, e);
    }
}

