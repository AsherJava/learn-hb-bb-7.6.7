/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.exceptions;

public class NotSupportedException
extends RuntimeException {
    public NotSupportedException() {
    }

    public NotSupportedException(String msg) {
        super(msg);
    }

    public NotSupportedException(Exception e) {
        super(e);
    }

    public NotSupportedException(String msg, Exception e) {
        super(msg, e);
    }
}

