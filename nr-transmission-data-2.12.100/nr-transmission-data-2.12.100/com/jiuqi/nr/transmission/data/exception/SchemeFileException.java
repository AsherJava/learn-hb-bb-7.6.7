/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.transmission.data.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SchemeFileException implements ErrorEnum
{
    FILE_CHECK_ERROR("F-01", "\u6587\u4ef6\u6821\u9a8c\u5931\u8d25");

    private String code;
    private String message;

    private SchemeFileException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

