/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.print.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum PrintAttributeException implements ErrorEnum
{
    ATTRIBUTE_COVER_FAIL("910", "\u6253\u5370\u8bbe\u7f6e\u8986\u76d6\u5931\u8d25"),
    ATTRIBUTE_SYNC_FAIL("911", "\u6253\u5370\u8bbe\u7f6e\u540c\u6b65\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private PrintAttributeException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

