/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.transmission.data.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SchemeImportException implements ErrorEnum
{
    IMPORT_ERROR("I-01", "\u88c5\u5165\u6570\u636e\u65f6\u9519\u8bef"),
    IMPORT_PROCESS_ERROR("I-02", "\u88c5\u5165\u8fdb\u5ea6\u67e5\u8be2\u9519\u8bef");

    private String code;
    private String message;

    private SchemeImportException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return null;
    }

    public String getMessage() {
        return null;
    }
}

