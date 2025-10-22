/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.splittable.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExceptionEnum implements ErrorEnum
{
    SPLIT_FILED_ST_1("ST_01", "\u62c6\u8868\u5931\u8d25"),
    ALL_FAILED("10000", "\u79fb\u52a8\u8868\u62c6\u5206\u7b97\u6cd5\u5f02\u5e38"),
    SERIALIZATION_FAILED("10001", "\u5e8f\u5217\u5316\u5931\u8d25\uff1a"),
    FORM_KEY_FAILED("10002", "\u627e\u4e0d\u5230formKey\u7684\u8868\u683c"),
    REGION_KEY_FAILED("10003", "\u627e\u4e0d\u5230regionKey\u7684\u6570\u636e\u533a\u57df\uff0c\u6216\u8005\u6570\u636e\u533a\u57df\u4e3a\u7a7a\uff0c\u6216\u8005\u6ca1\u6709\u6570\u636e\u533a\u57df");

    private String code;
    private String message;

    private ExceptionEnum(String code, String message) {
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

