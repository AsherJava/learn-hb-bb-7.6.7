/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.bql.web;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DSVErrorEnum implements ErrorEnum
{
    DSV_CHILDRENS("DSV_CHILDRENS", "\u83b7\u53d6\u4e0b\u7ea7\u8282\u70b9\u5931\u8d25"),
    DSV_BEAN("DSV_BEAN", "\u83b7\u53d6\u6570\u636e\u65b9\u6848\u4fe1\u606f\u5931\u8d25"),
    DSV_SEARCH("DSV_SEARCH", "\u641c\u7d22\u6570\u636e\u65b9\u6848\u4fe1\u606f\u5931\u8d25"),
    DSV_METADATA("DSV_METADATA", "\u83b7\u53d6\u6570\u636e\u65b9\u6848\u5143\u6570\u636e\u5931\u8d25"),
    DSV_QUERY("DSV_QUERY", "\u83b7\u53d6\u62a5\u8868\u6570\u636e\u65b9\u6848\u67e5\u8be2\u7ed3\u679c\u5931\u8d25"),
    DSV_OPTIONS("DSV_OPTIONS", "\u83b7\u53d6\u62a5\u8868\u6570\u636e\u65b9\u6848\u67e5\u8be2\u6240\u652f\u6301\u7684\u9009\u9879");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DSVErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

