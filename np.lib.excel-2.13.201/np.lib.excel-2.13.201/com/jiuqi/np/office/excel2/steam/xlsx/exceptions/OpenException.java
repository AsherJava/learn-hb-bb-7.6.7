/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.exceptions;

public class OpenException
extends RuntimeException {
    public OpenException() {
    }

    public OpenException(String msg) {
        super(msg);
    }

    public OpenException(Exception e) {
        super(e);
    }

    public OpenException(String msg, Exception e) {
        super(msg, e);
    }
}

