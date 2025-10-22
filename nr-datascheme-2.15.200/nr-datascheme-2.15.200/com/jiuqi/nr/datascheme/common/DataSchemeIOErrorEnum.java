/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DataSchemeIOErrorEnum implements ErrorEnum
{
    DS_IO_001("IO001", "\u6570\u636e\u65b9\u6848\u5bfc\u5165,\u53c2\u6570\u5305\u53c2\u6570\u4e0d\u5168\uff01"),
    DS_IO_002("IO002", "\u6570\u636e\u65b9\u6848\u5bfc\u5165,\u5bfc\u5165\u6570\u636e\u65b9\u6848\u4e0e\u53c2\u6570\u8868\u6570\u636e\u65b9\u6848\u4e0d\u4e00\u81f4\uff01"),
    DS_IO_003("IO003", "\u6570\u636e\u65b9\u6848\u5bfc\u5165,\u672a\u77e5\u5f02\u5e38\uff01"),
    DS_IO_101("IO101", "\u6570\u636e\u65b9\u6848\u5bfc\u51fa,\u5206\u6790\u6570\u636e\u5f02\u5e38\uff01"),
    DS_IO_102("IO102", "\u6570\u636e\u65b9\u6848\u5bfc\u51fa,\u751f\u4ea7\u538b\u7f29\u6587\u4ef6\u5f02\u5e38\uff01"),
    DS_IO_103("IO102", "\u6587\u4ef6\u8def\u5f84\u5f02\u5e38\uff01");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DataSchemeIOErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

