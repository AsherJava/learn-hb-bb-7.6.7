/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.enums;

import com.jiuqi.dc.base.common.intf.IMoudle;

public enum Moudle implements IMoudle
{
    VOUCHER("voucher", "\u51ed\u8bc1", 1, "\u4ec5\u4e00\u672c\u8d26\u6807\u51c6\u51ed\u8bc1\u5206\u5f55\u8868"),
    BALANCE("balance", "\u4f59\u989d\u8868", 2, "\u5305\u62ec\u51ed\u8bc1\u5206\u5f55\u548c\u4e00\u672c\u8d26\u4f59\u989d\u8868"),
    XJLL("xjll", "\u73b0\u91d1\u6d41\u91cf", 3, "\u5305\u62ec\u51ed\u8bc1\u5206\u5f55, \u9002\u7528\u4e8e\u73b0\u91d1\u6d41\u91cf\u4e1a\u52a1"),
    AGING("aging", "\u8d26\u9f84", 4, "\u9002\u7528\u4e8e\u8d26\u9f84\u5c55\u793a"),
    RECLASSIFY("reclassify", "\u91cd\u5206\u7c7b", 5, "\u9002\u7528\u4e8e\u91cd\u5206\u7c7b"),
    ADJUSTVCHR("adjustVchr", "\u8c03\u6574\u51ed\u8bc1", 6, "\u5305\u62ec\u4e00\u672c\u8d26\u51ed\u8bc1\u8c03\u6574\u3001\u5355\u6237\u8c03\u6574");

    private final String code;
    private final String name;
    private final int order;
    private final String desc;

    private Moudle(String code, String name, int order, String desc) {
        this.code = code;
        this.name = name;
        this.order = order;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static Moudle fromCode(String code) {
        for (Moudle moudle : Moudle.values()) {
            if (!moudle.getCode().equals(code)) continue;
            return moudle;
        }
        return null;
    }
}

