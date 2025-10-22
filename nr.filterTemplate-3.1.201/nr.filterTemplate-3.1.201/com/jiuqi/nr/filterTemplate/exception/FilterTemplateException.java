/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.filterTemplate.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum FilterTemplateException implements ErrorEnum
{
    FILTER_TEMPLATE_INSERT_FAILED("3001", "\u65b0\u589e\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25"),
    FILTER_TEMPLATE_DELETE_FAILED("3002", "\u5220\u9664\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25"),
    FILTER_TEMPLATE_GET_FAILED("3003", "\u83b7\u53d6\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25"),
    FILTER_TEMPLATE_UPDATE_FAILED("3004", "\u66f4\u65b0\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25"),
    FILTER_TEMPLATE_QUERY_FAILED("3005", "\u67e5\u8be2\u8fc7\u6ee4\u6a21\u677f\u5931\u8d25"),
    FILTER_TEMPLATE_QUERY_REL_FAILED("3006", "\u67e5\u8be2\u5f15\u7528\u8be6\u60c5\u5931\u8d25"),
    FILTER_TEMPLATE_COPY_FAILED("3007", "\u590d\u5236\u5931\u8d25");

    private String code;
    private String message;

    private FilterTemplateException(String code, String message) {
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

