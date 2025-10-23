/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.transmission.data.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SchemeExportException implements ErrorEnum
{
    EXPORT_FORM_ERROR("E-01", "\u5bfc\u51fa\u6570\u636e\u65f6\u62a5\u8868\u4e3a\u7a7a"),
    EXPORT_ENTITY_ERROR("E-02", "\u5bfc\u51fa\u6570\u636e\u65f6\u5355\u4f4d\u4e3a\u7a7a"),
    EXPORT_ERROR("E-03", "\u5bfc\u51fa\u6570\u636e\u65f6\u9519\u8bef"),
    EXPORT_NULL_ERROR("E-04", "\u5bfc\u51fa\u6570\u636e\u65f6\u6587\u4ef6\u4e0d\u5b58\u5728"),
    EXPORT_HISTORY_DETAIL_ERROR("E-05", "\u5bfc\u51fa\u540c\u6b65\u8be6\u60c5\u5931\u8d25");

    private String code;
    private String message;

    private SchemeExportException(String code, String message) {
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

