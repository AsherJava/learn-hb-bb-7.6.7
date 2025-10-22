/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.conditionalstyle.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum CSErrorEnum implements ErrorEnum
{
    CS_EXCEPTION_001("001", "\u6761\u4ef6\u6837\u5f0f\u63d2\u5165\u5931\u8d25\uff01"),
    CS_EXCEPTION_002("002", "\u6761\u4ef6\u6837\u5f0f\u66f4\u65b0\u5931\u8d25\uff01"),
    CS_EXCEPTION_003("003", "\u6761\u4ef6\u6837\u5f0f\u5220\u9664\u5931\u8d25\uff01");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private CSErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

