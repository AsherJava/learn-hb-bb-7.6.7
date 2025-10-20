/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gc.financial.status.enums;

import com.jiuqi.common.base.BusinessRuntimeException;

public enum FinancialStatusEnum {
    INIT("0", "\u672a\u5f00\u8d26"),
    OPEN("1", "\u5df2\u5f00\u8d26"),
    CLOSE("2", "\u5df2\u5173\u8d26"),
    PARTIAL_OPEN("3", "\u90e8\u5206\u5f00\u8d26");

    private String code;
    private String name;

    private FinancialStatusEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static FinancialStatusEnum getStatusByCode(String code) {
        for (FinancialStatusEnum type : FinancialStatusEnum.values()) {
            if (!type.getCode().equals(code)) continue;
            return type;
        }
        throw new BusinessRuntimeException("\u672a\u77e5\u7684\u5f00\u5173\u8d26\u72b6\u6001: " + code);
    }
}

