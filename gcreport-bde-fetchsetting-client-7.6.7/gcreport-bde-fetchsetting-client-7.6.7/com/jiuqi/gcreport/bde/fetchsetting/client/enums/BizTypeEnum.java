/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.gcreport.bde.fetchsetting.client.enums;

import com.jiuqi.common.base.BusinessRuntimeException;

public enum BizTypeEnum {
    NR("NR", "\u62a5\u8868"),
    BUDGET("BUDGET", "\u9884\u7b97"),
    BILL("BILL", "\u5355\u636e");

    private final String code;
    private final String title;

    private BizTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static BizTypeEnum getEnumByCode(String code) {
        for (BizTypeEnum bizTypeEnum : BizTypeEnum.values()) {
            if (!bizTypeEnum.getCode().equals(code)) continue;
            return bizTypeEnum;
        }
        throw new BusinessRuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684BizTypeEnum\u679a\u4e3e code=" + code);
    }

    public static BizTypeEnum findEnumByCode(String code) {
        for (BizTypeEnum bizTypeEnum : BizTypeEnum.values()) {
            if (!bizTypeEnum.getCode().equals(code)) continue;
            return bizTypeEnum;
        }
        return null;
    }
}

