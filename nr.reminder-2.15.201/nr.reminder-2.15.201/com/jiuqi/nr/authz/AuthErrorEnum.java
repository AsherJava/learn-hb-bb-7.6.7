/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.authz;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum AuthErrorEnum implements ErrorEnum
{
    AUTH_ROLE_001("", "\u89d2\u8272\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    AUTH_ROLE_002("", "\u89d2\u8272\u5206\u7ec4\u540d\u79f0\u91cd\u590d\uff01"),
    AUTH_ROLE_003("", "\u89d2\u8272\u6807\u8bc6\u4e0d\u80fd\u4e3a\u7a7a\uff01"),
    AUTH_ROLE_004("", "\u89d2\u8272\u6807\u8bc6\u91cd\u590d\uff01"),
    AUTH_ROLE_005("", "\u6ca1\u6709\u5f53\u524d\u64cd\u4f5c\u6743\u9650\uff01"),
    AUTH_ROLE_006("", "\u5bc6\u7801\u4e0d\u7b26\u5408\u89c4\u5219\uff01"),
    AUTH_ROLE_007("", "\u5bfc\u5165\u6a21\u677f\u6709\u8bef\uff01"),
    AUTH_ROLE_008("", "\u5220\u9664\u7528\u6237\u65f6\uff0c\u8bf7\u4e0d\u8981\u9009\u62e9\u81ea\u5df1\uff01"),
    AUTH_ROLE_009("", "\u65e0\u6cd5\u627e\u5230\u8981\u5220\u9664\u7684\u7528\u6237"),
    AUTH_ROLE_0010("", "\u65e7\u5bc6\u7801\u8ba4\u8bc1\u5931\u8d25\uff01"),
    AUTH_ROLE_0011("", "\u65b0\u5bc6\u7801\u4e0d\u80fd\u4e0e\u6700\u8fd1N\u6b21\u8bbe\u7f6e\u7684\u5bc6\u7801\u76f8\u540c\uff01"),
    AUTH_ROLE_0012("", "\u89d2\u8272\u5206\u7ec4\u540d\u79f0\u4e0d\u80fd\u8d85\u8fc750\u4f4d\uff01"),
    AUTH_ROLE_0013("", "\u89d2\u8272\u540d\u79f0\u6216\u8005\u4ee3\u7801\u4e0d\u80fd\u8d85\u8fc750\u4f4d\uff01"),
    AUTH_ROLE_0014("", "\u89e3\u5bc6\u5bc6\u7801\u65f6\u53d1\u751f\u4e86\u9519\u8bef\uff01"),
    AUTH_ROLE_0016("", "\u4e0d\u5b58\u5728\u7684\u7528\u6237\uff01"),
    AUTH("UN", ""),
    AUTH_ROLE_0015("", "\u5bf9\u7528\u6237\u6240\u5c5e\u4e3b\u4f53\u6ca1\u6709\u8bbf\u95ee\u6743\u9650\u65e0\u6cd5\u64cd\u4f5c");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private AuthErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

