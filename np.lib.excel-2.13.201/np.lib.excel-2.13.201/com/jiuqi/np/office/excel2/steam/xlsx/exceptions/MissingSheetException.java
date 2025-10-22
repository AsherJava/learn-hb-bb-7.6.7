/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.steam.xlsx.exceptions;

public class MissingSheetException
extends RuntimeException {
    public MissingSheetException() {
    }

    public MissingSheetException(String msg) {
        super(msg);
    }

    public MissingSheetException(Exception e) {
        super(e);
    }

    public MissingSheetException(String msg, Exception e) {
        super(msg, e);
    }
}

