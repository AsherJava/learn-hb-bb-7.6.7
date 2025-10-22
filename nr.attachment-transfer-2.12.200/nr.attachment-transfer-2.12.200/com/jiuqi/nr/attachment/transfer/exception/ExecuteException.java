/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.attachment.transfer.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExecuteException implements ErrorEnum
{
    FAILED_EXE("000", "\u6267\u884c\u5931\u8d25"),
    FAILED_START("001", "\u5f00\u542f\u4efb\u52a1\u5931\u8d25"),
    FAILED_RESET("002", "\u91cd\u7f6e\u4efb\u52a1\u5931\u8d25"),
    FAILED_RESUME("003", "\u6062\u590d\u4efb\u52a1\u5931\u8d25"),
    FAILED_PAUSE("004", "\u6682\u505c\u4efb\u52a1\u5931\u8d25"),
    FAILED_CLEAN("005", "\u6e05\u7406\u5e76\u91cd\u65b0\u6267\u884c\u5931\u8d25");

    private String code;
    private String title;

    private ExecuteException(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.title;
    }
}

