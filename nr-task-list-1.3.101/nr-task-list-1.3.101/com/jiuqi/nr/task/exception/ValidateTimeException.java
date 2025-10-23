/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ValidateTimeException implements ErrorEnum
{
    QUERY_FORM_SCHEME_EFFECT_TIME_FAILED("30001", "\u67e5\u8be2\u65b9\u6848\u751f\u6548\u65f6\u671f\u5931\u8d25"),
    QUERY_FORM_SCHEME_EFFECT_TIME_LIMITED_FAILED("30002", "\u67e5\u8be2\u65f6\u671f\u8bbe\u7f6e\u8303\u56f4\u5931\u8d25"),
    MERGE_FORM_SCHEME_EFFECT_TIME_FAILED("30003", "\u5408\u5e76\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f\u5931\u8d25"),
    CHECK_FORM_SCHEME_EFFECT_TIME_FAILED("30004", "\u68c0\u67e5\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f\u5931\u8d25"),
    SAVE_FORM_SCHEME_EFFECT_TIME_FAILED("30005", "\u4fdd\u5b58\u62a5\u8868\u65b9\u6848\u751f\u6548\u65f6\u671f\u5931\u8d25");

    private String code;
    private String message;

    private ValidateTimeException(String code, String message) {
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

