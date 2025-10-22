/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.definition.print.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum PrintErrorEnum implements ErrorEnum
{
    PRINTERROR_001("001", "\u6253\u5370\u65b9\u6848\u4e0d\u5b58\u5728"),
    PRINTERROR_002("002", "\u62a5\u8868\u4e0d\u5b58\u5728");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private PrintErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

