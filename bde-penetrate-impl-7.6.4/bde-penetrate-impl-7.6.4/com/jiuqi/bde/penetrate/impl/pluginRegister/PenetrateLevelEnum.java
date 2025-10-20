/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bde.penetrate.impl.pluginRegister;

import com.jiuqi.bi.util.StringUtils;

public enum PenetrateLevelEnum {
    BALANCE("\u4f59\u989d\u8868", "BALANCE"),
    DETAIL_LEDGER("\u660e\u7ec6\u8d26", "DETAIL_LEDGER"),
    VOUCHER("\u51ed\u8bc1", "VOUCHER");

    private String name;
    private String code;

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    private PenetrateLevelEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static PenetrateLevelEnum getEnumByCode(String code) {
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (PenetrateLevelEnum levelEnum : PenetrateLevelEnum.values()) {
            if (!levelEnum.code.equalsIgnoreCase(code)) continue;
            return levelEnum;
        }
        return null;
    }
}

