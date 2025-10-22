/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.etl.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ETLConfigErrorEnum implements ErrorEnum
{
    ETL_CONFIG_ERROR_ENUM("201", "\u672a\u914d\u7f6eETL\u670d\u52a1."),
    ETL_TREE_NODE_ERROR_ENUM("202", "\u6ca1\u6709\u9700\u8981\u7ed1\u5b9a\u6d41\u7a0b\u7684\u4efb\u52a1.");

    private String code;
    private String message;

    private ETLConfigErrorEnum(String code, String message) {
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

