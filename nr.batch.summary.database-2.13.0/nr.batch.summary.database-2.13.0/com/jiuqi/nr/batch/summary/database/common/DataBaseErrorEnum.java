/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.batch.summary.database.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum DataBaseErrorEnum implements ErrorEnum
{
    NRSUMMARY_EXCEPTION_000("000", "\u5224\u65ad\u6c47\u603b\u5206\u5e93\u4e2d\u662f\u5426\u5b58\u5728\u6570\u636e\u5931\u8d25\uff01");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private DataBaseErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

