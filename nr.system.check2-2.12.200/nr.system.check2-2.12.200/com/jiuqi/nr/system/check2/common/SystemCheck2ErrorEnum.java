/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.system.check2.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SystemCheck2ErrorEnum implements ErrorEnum
{
    SYSTEM_CHECK2_001("001", "\u67e5\u8be2\u8d44\u6e90\u5206\u7ec4\u5931\u8d25\uff01"),
    SYSTEM_CHECK2_002("002", "\u8d44\u6e90\u6267\u884c\u5931\u8d25\uff01"),
    SYSTEM_CHECK2_003("003", "\u67e5\u8be2\u6267\u884c\u5931\u8d25\uff01");

    private String code;
    private String message;

    private SystemCheck2ErrorEnum(String code, String message) {
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

