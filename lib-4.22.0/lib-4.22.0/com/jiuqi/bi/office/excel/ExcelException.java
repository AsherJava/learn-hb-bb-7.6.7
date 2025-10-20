/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.office.OfficeException;

public class ExcelException
extends OfficeException {
    private static final long serialVersionUID = -8789118055437630377L;

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}

