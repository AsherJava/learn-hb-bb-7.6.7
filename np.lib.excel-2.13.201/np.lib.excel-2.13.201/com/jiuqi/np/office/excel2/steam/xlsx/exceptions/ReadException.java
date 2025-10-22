/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.exceptions;

public class ReadException
extends RuntimeException {
    public ReadException() {
    }

    public ReadException(String msg) {
        super(msg);
    }

    public ReadException(Exception e) {
        super(e);
    }

    public ReadException(String msg, Exception e) {
        super(msg, e);
    }
}

