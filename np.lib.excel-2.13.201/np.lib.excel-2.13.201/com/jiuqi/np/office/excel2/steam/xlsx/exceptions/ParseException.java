/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.exceptions;

public class ParseException
extends RuntimeException {
    public ParseException() {
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(Exception e) {
        super(e);
    }

    public ParseException(String msg, Exception e) {
        super(msg, e);
    }
}

