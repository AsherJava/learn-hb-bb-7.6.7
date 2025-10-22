/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.authz;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum AuthExceptionEnum implements ErrorEnum
{
    AUTH_10001("AUTH-10001", "\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a!"),
    AUTH_20001("AUTH-20001", "\u65e0\u6cd5\u83b7\u53d6\u5f53\u524d\u64cd\u4f5c\u4eba\u7684\u8eab\u4efd!"),
    AUTH_30001("AUTH-30001", "\u89d2\u8272!"),
    AUTH_40001("AUTH-40001", "\u5355\u4f4d!"),
    AUTH_50001("AUTH-50001", "\u5f53\u524d\u7528\u6237\u6ca1\u6709\u7528\u6237\u7ba1\u7406\u6743\u9650!"),
    AUTH_60001("AUTH-60001", "\u7cfb\u7edf\u9009\u9879\u4e2d\u6b63\u5219\u8868\u8fbe\u5f0f\u8bbe\u7f6e\u9519\u8bef!"),
    AUTH_70001("AUTH-70001", "\u65b0\u589e\u7528\u6237\u5931\u8d25!"),
    AUTH_80001("AUTH-80001", "\u83b7\u53d6\u7528\u6237\u81ea\u5b9a\u4e49\u8868\u5355\u4ea7\u54c1\u7ebf\u5931\u8d25!");

    private String code;
    private String message;

    private AuthExceptionEnum(String code, String message) {
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

