/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.efdc.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum EFDCCheckErrorEnum implements ErrorEnum
{
    EFDCCHECK_EXCEPTION_001("001", "EFDC\u670d\u52a1\u5730\u5740\u4e3a\u7a7a!"),
    EFDCCHECK_EXCEPTION_002("002", "EFDC\u516c\u5f0f\u6821\u9a8c\u6570\u5730\u5740\u670d\u52a1\u65e0\u6cd5\u8bbf\u95ee!"),
    EFDCCHECK_EXCEPTION_003("003", "EFDC\u516c\u5f0f\u6821\u9a8c\u53c2\u6570\u6709\u95ee\u9898,\u8bf7\u8054\u7cfb\u7ba1\u7406\u5458\u67e5\u770b\u540e\u53f0\u4fe1\u606f!"),
    QUERY_ENTTTY("004", "\u65e0\u6cd5\u83b7\u53d6\u8be5\u62a5\u8868\u65b9\u6848\u4e0a\u7684\u4e3b\u4f53");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private EFDCCheckErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

