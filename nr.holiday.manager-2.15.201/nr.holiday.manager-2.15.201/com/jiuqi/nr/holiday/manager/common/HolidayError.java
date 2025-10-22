/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.holiday.manager.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum HolidayError implements ErrorEnum
{
    HOLIDAY_ERROR_001("001", "\u5047\u65e5\u7ba1\u7406\u4fdd\u5b58\u5f02\u5e38"),
    HOLIDAY_ERROR_002("002", "\u5047\u65e5\u7ba1\u7406\u5220\u9664\u5f02\u5e38"),
    HOLIDAY_ERROR_003("003", "\u5047\u65e5\u7ba1\u7406\u91cd\u7f6e\u5f02\u5e38");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private HolidayError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

