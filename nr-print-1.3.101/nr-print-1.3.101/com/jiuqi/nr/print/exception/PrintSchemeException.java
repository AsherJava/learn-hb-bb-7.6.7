/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.print.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum PrintSchemeException implements ErrorEnum
{
    ADD_PRINT_SCHEME_FAIL("PRINT_SCHEME_1101", "\u65b0\u589e\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    DELETE_TEMPLATE_FAIL("PRINT_SCHEME_1102", "\u5220\u9664\u6253\u5370\u65b9\u6848\u4e0b\u6a21\u677f\u5931\u8d25"),
    UPDATE_PRINT_SCHEME_FAIL("PRINT_SCHEME_1103", "\u66f4\u65b0\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    DELETE_PRINT_SCHEME_FAIL("PRINT_SCHEME_1104", "\u5220\u9664\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    COPY_PRINT_SCHEME_FAIL("PRINT_SCHEME_1105", "\u590d\u5236\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    MOVE_PRINT_SCHEME_FAIL("PRINT_SCHEME_1106", "\u79fb\u52a8\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    DEPLOY_PRINT_SCHEME_FAIL("PRINT_SCHEME_1107", "\u53d1\u5e03\u6253\u5370\u65b9\u6848\u5931\u8d25"),
    CHECK_PRINT_SCHEME_TITLE_FAIL("PRINT_SCHEME_1108", "\u6253\u5370\u65b9\u6848\u540d\u79f0\u6821\u9a8c\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private PrintSchemeException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

