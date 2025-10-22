/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.system.check.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SCErrorEnum implements ErrorEnum
{
    SYSTEM_CHECK_EXCEPTION_001("001", "\u83b7\u53d6\u4efb\u52a1\u5f02\u5e38"),
    SYSTEM_CHECK_EXCEPTION_002("002", "\u6e05\u9664\u6570\u636e\u5f02\u5e38"),
    SYSTEM_CHECK_EXCEPTION_003("003", "\u6e05\u9664\u6761\u4ef6\u9519\u8bef"),
    SYSTEM_CHECK_EXCEPTION_004("004", "\u672a\u542f\u7528\u4e34\u65f6\u8868\u529f\u80fd");

    private String code;
    private String message;

    private SCErrorEnum(String code, String message) {
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

