/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.transmission.data.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SchemeSchemeException implements ErrorEnum
{
    SEARCH_SCHEME_FORM_SCHEME_ERROR("S-01", "\u67e5\u8be2\u4efb\u52a1\u5728\u8be5\u65f6\u671f\u4e0b\u7684\u62a5\u8868\u65b9\u6848\u9519\u8bef");

    private String code;
    private String message;

    private SchemeSchemeException(String code, String message) {
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

