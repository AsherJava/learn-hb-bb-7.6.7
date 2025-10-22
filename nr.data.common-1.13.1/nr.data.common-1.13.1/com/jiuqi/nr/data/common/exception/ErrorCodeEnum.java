/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.common.exception;

import com.jiuqi.nr.data.common.exception.ErrorCode;

public enum ErrorCodeEnum implements ErrorCode
{
    UNSPECIFIED("500", "\u7f51\u7edc\u5f02\u5e38\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5"),
    NO_SERVICE("404", "\u7f51\u7edc\u5f02\u5e38, \u670d\u52a1\u5668\u7194\u65ad"),
    REQUEST_ERROR("400", "\u5165\u53c2\u5f02\u5e38,\u8bf7\u68c0\u67e5\u5165\u53c2\u540e\u518d\u6b21\u8c03\u7528"),
    PAGE_NUM_IS_NULL("4001", "\u9875\u7801\u4e0d\u80fd\u4e3a\u7a7a"),
    PAGE_SIZE_IS_NULL("4002", "\u9875\u6570\u4e0d\u80fd\u4e3a\u7a7a"),
    ID_IS_NULL("4003", "ID\u4e0d\u80fd\u4e3a\u7a7a"),
    SEARCH_IS_NULL("4004", "\u641c\u7d22\u6761\u4ef6\u4e0d\u80fd\u4e3a\u7a7a"),
    METHOD_IS_DEPRECATED("4005", "\u65b9\u6cd5\u5df2\u5e9f\u5f03");

    private final String code;
    private final String description;

    private ErrorCodeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static ErrorCodeEnum getByCode(String code) {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return value;
        }
        return UNSPECIFIED;
    }

    public static Boolean contains(String code) {
        for (ErrorCodeEnum value : ErrorCodeEnum.values()) {
            if (!value.getCode().equals(code)) continue;
            return true;
        }
        return false;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String bizName() {
        return "\u5bfc\u5165\u3001\u5bfc\u51fa\u901a\u7528\u5f02\u5e38";
    }
}

