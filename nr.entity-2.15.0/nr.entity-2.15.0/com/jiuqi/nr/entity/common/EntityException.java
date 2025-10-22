/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.entity.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum EntityException implements ErrorEnum
{
    CREATE_ENTITY("E-01", "\u521b\u5efa\u5b9e\u4f53\u5f02\u5e38"),
    DEPLOY_TABLE("E-02", "\u53d1\u5e03\u5b58\u50a8\u8868\u5b9a\u4e49\u9519\u8bef"),
    UPDATE_ENTITY("E-03", "\u66f4\u65b0\u5b9e\u4f53\u5f02\u5e38"),
    CREATE_TABLE_DEFINE("E-04", "\u521b\u5efa\u5b58\u50a8\u8868\u5b9a\u4e49\u9519\u8bef"),
    EMPTY_TABLE_DEFINE("E-05", "\u4e0d\u5b58\u5728\u7684\u5b58\u50a8\u8868\u5b9a\u4e49"),
    QUERY_BIZ_FIELD_ERROR("F-05", "\u67e5\u8be2\u5b58\u50a8\u8868\u4e3b\u952e\u9519\u8bef");

    private String code;
    private String message;

    private EntityException(String code, String message) {
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
        return this.message + arg;
    }
}

