/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.task.form.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExceptionEnum implements ErrorEnum
{
    EXCEPTION_ENUM_01("EXCEPTION_ENUM_001", "\u4fdd\u5b58\u8868\u5355\u5931\u8d25!"),
    EXCEPTION_ENUM_02("EXCEPTION_ENUM_002", "\u67e5\u8be2\u5931\u8d25!"),
    EXCEPTION_ENUM_03("EXCEPTION_ENUM_003", "\u53c2\u6570\u68c0\u67e5\u5931\u8d25"),
    EXCEPTION_ENUM_004("EXCEPTION_ENUM_004", "\u5206\u6790\u8868\u67e5\u8be2\u5931\u8d25"),
    EXCEPTION_ENUM_005("EXCEPTION_ENUM_005", "\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868\u65b0\u589e\u5931\u8d25"),
    EXCEPTION_ENUM_006("EXCEPTION_ENUM_006", "\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868\u66f4\u65b0\u5931\u8d25"),
    EXCEPTION_ENUM_007("EXCEPTION_ENUM_007", "\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868\u8868\u6837\u67e5\u8be2\u5931\u8d25"),
    EXCEPTION_ENUM_008("EXCEPTION_ENUM_008", "\u5206\u6790\u8868\u7c7b\u578b\u62a5\u8868\u5206\u6790\u8868\u6811\u578b\u67e5\u8be2\u5931\u8d25"),
    EXCEPTION_ENUM_0001("EXCEPTION_ENUM_0001", "\u8868\u6837\u5bfc\u51fa\u5931\u8d25\uff01"),
    EXCEPTION_ENUM_0002("EXCEPTION_ENUM_0002", "\u5bfc\u51fa\u540e\u7684\u8868\u6837\u4e0b\u8f7d\u5931\u8d25\uff01"),
    EXCEPTION_ENUM_0003("EXCEPTION_ENUM_0003", "\u6587\u4ef6\u4e0a\u4f20\u5931\u8d25"),
    EXCEPTION_ENUM_0004("EXCEPTION_ENUM_0004", "\u6587\u4ef6\u5bfc\u5165\u5931\u8d25"),
    EXCEPTION_ENUM_0005("EXCEPTION_ENUM_0005", "\u83b7\u53d6\u6743\u9650\u6a21\u5757\u6216\u529f\u80fd\u70b9ID\u5931\u8d25"),
    EXCEPTION_ENUM_0006("EXCEPTION_ENUM_0006", "\u5220\u9664\u9ed8\u8ba4\u6a21\u7248\u5931\u8d25"),
    EXCEPTION_ENUM_0007("EXCEPTION_ENUM_0007", "\u83b7\u53d6\u9644\u4ef6\u5931\u8d25"),
    EXCEPTION_ENUM_0008("EXCEPTION_ENUM_0008", "\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25"),
    EXCEPTION_ENUM_0009("EXCEPTION_ENUM_0009", "\u6821\u9a8c\u5931\u8d25\uff01"),
    EXCEPTION_ENUM_0010("EXCEPTION_ENUM_0010", "\u5b9e\u4f53\u67e5\u8be2\u5931\u8d25\uff01");

    private String code;
    private String message;

    private ExceptionEnum(String code, String message) {
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

