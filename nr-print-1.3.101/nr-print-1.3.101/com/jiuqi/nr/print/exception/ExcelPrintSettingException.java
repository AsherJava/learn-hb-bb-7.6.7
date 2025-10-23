/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.print.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExcelPrintSettingException implements ErrorEnum
{
    PRINT_SETTING_SERIALISE_FAIL("PRINT_SETTING_0", "Excel\u6253\u5370\u8bbe\u7f6e\u67e5\u8be2\u65f6\u89e3\u6790\u8868\u6837\u5931\u8d25"),
    PRINT_SETTING_QUERY_FAIL("PRINT_SETTING_00", "Excel\u6253\u5370\u8bbe\u7f6e\u67e5\u8be2\u5931\u8d25"),
    PRINT_SETTING_SAVE_FAIL("PRINT_SETTING_01", "Excel\u6253\u5370\u8bbe\u7f6e\u4fdd\u5b58\u5931\u8d25"),
    PRINT_SETTING_RESET_FAIL("PRINT_SETTING_02", "Excel\u6253\u5370\u8bbe\u7f6e\u91cd\u7f6e\u5931\u8d25"),
    PRINT_SETTING_DELETE_FAIL("PRINT_SETTING_03", "Excel\u6253\u5370\u8bbe\u7f6e\u5220\u9664\u5931\u8d25");

    private String code;
    private String message;

    public String getCode() {
        return "";
    }

    public String getMessage() {
        return "";
    }

    private ExcelPrintSettingException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

