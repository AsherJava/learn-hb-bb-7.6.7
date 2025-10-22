/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.entity.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum AdapterException implements ErrorEnum
{
    EMPTY_INJECT_CATEGORY("ADAPTER-01", "\u6ca1\u6709\u4efb\u4f55\u5b9e\u4f53\u9002\u914d\u5668\u7684\u5b9e\u73b0"),
    FIND_CATEGORY("ADAPTER-02", "\u672a\u627e\u5230\u5bf9\u5e94\u7c7b\u578b\u7684\u5b9e\u4f53\u63a5\u53e3"),
    EMPTY_ORG_ADAPTOR("ADAPTER-03", "\u6ca1\u6709ORG\u9002\u914d\u5668\u7684\u5b9e\u73b0"),
    ERROR_APPEND_HEADER("ADAPTER-04", "\u8ffd\u52a0\u5b9e\u4f53id\u9519\u8bef"),
    ERROR_READ_HEADER("ADAPTER-05", "\u8bfb\u53d6\u5b9e\u4f53id\u9519\u8bef"),
    ERROR_APPEND_FILE("ADAPTER-06", "\u5199\u5165\u6570\u636e\u5931\u8d25"),
    ERROR_READ_FILE("ADAPTER-07", "\u8bfb\u53d6\u6570\u636e\u5931\u8d25"),
    EMPTY_COLUMN_INFO("ADAPTER-08", "\u539f\u59cb\u67e5\u8be2\u5217\u4fe1\u606f\u4e3a\u7a7a"),
    INVALID_COLUMN_INDEX("ADAPTER-10", "\u975e\u6cd5\u7684\u5217\u53c2\u6570\u4fe1\u606f");

    private String code;
    private String message;

    private AdapterException(String code, String message) {
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

