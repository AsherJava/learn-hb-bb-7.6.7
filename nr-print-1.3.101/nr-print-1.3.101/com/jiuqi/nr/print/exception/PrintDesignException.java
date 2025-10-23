/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.print.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum PrintDesignException implements ErrorEnum
{
    TEMPLATE_UNSAVED("PRINT_DESIGNER_000", "\u8bf7\u5148\u4fdd\u5b58\u6253\u5370\u6a21\u677f"),
    TEMPLATE_LOAD_FAIL("PRINT_DESIGNER_800", "\u6253\u5370\u6a21\u677f\u52a0\u8f7d\u5931\u8d25"),
    TEMPLATE_EXPORT_FAIL("PRINT_DESIGNER_801", "\u6253\u5370\u6a21\u677f\u5bfc\u51fa\u5931\u8d25"),
    TEMPLATE_IMPORT_FAIL("PRINT_DESIGNER_801", "\u6253\u5370\u6a21\u677f\u5bfc\u5165\u5931\u8d25"),
    COMTEM_SAVE_FAIL("COMTEM_000", "\u6bcd\u7248\u4fdd\u5b58\u5931\u8d25"),
    COMTEM_DELETE_FAIL("COMTEM_000", "\u6bcd\u7248\u5220\u9664\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private PrintDesignException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

