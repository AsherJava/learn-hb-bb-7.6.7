/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.exception.CheckRuntimeException
 */
package com.jiuqi.bde.common.constant;

import com.jiuqi.dc.base.common.exception.CheckRuntimeException;

public enum RequestSourceTypeEnum {
    NR_FETCH("NR_FETCH", "\u62a5\u8868\u53d6\u6570"),
    BUDGET_FETCH("BUDGET_FETCH", "\u9884\u7b97\u53d6\u6570"),
    BILL_FETCH("BILL_FETCH", "\u5355\u636e\u53d6\u6570"),
    TEST("TEST", "\u53d6\u6570\u6d4b\u8bd5"),
    PENETRATE("PENETRATE", "\u7a7f\u900f"),
    FETCH("FETCH", "\u6b63\u5e38\u53d6\u6570"),
    DATACHECK_FETCH("DATACHECK_FETCH", "\u6570\u636e\u7a3d\u6838\u53d6\u6570"),
    FINANCIAL_AUTO_FETCH("FINANCIAL_AUTO_FETCH", "\u591a\u7ef4\u81ea\u52a8\u53d6\u6570");

    private final String code;
    private final String title;

    private RequestSourceTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static RequestSourceTypeEnum fromCode(String code) {
        for (RequestSourceTypeEnum sourceType : RequestSourceTypeEnum.values()) {
            if (!sourceType.getCode().equals(code)) continue;
            return sourceType;
        }
        throw new CheckRuntimeException("\u672a\u8bc6\u522b\u7684\u53d6\u6570\u6765\u6e90\u7c7b\u578b\u3010" + code + "\u3011");
    }
}

