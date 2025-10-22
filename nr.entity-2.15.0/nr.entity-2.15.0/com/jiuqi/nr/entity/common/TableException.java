/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.entity.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum TableException implements ErrorEnum
{
    NULL_VIEW("T-01", "\u4e0d\u5b58\u5728\u7684\u89c6\u56fe"),
    NULL_TABLE("T-02", "\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868"),
    QUERY_TABLE_ERROR("T-03", "\u5b58\u50a8\u8868\u67e5\u8be2\u5f02\u5e38"),
    QUERY_FIELD_ERROR("T-04", "\u5b58\u50a8\u8868\u6307\u6807\u67e5\u8be2\u5f02\u5e38"),
    NULL_FIELD("T-05", "\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\u6307\u6807"),
    DEPLOY_ERROR("T-06", "\u5b58\u50a8\u8868\u53d1\u5e03\u5931\u8d25"),
    QUERY_DATA_ERROR("T-07", "\u6784\u5efa\u67e5\u8be2\u7ed3\u679c\u96c6\u5931\u8d25"),
    DIMENSION_ERROR_01("T-08", "\u6784\u5efa\u7ef4\u5ea6\u540d\u79f0\u5f02\u5e38");

    private String code;
    private String message;

    private TableException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getMessage(String arg) {
        return new StringBuffer(this.message).append(arg).toString();
    }
}

