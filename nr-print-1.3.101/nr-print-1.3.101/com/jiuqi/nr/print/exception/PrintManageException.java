/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.print.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum PrintManageException implements ErrorEnum
{
    DO_NOT_HAS_CHILDREN("PRINT_MANAGE_1001", "\u6253\u5370\u7ba1\u7406\u4e0d\u5b58\u5728\u4e0b\u7ea7");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private PrintManageException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

