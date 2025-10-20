/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.adjustvchr.impl.enums;

public enum AdjustVchrTypeEnum {
    HANDLE_ADJUST("121", "\u624b\u5de5\u8c03\u6574"),
    BALANCE_ADJUST("122", "\u4f59\u989d\u91cd\u5206\u7c7b"),
    EXPIREDATE_ADJUST("123", "\u5230\u671f\u65e5\u91cd\u5206\u7c7b"),
    DECREASING_ADJUST("124", "\u62b5\u51cf\u91cd\u5206\u7c7b");

    private String code;
    private String name;

    private AdjustVchrTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static AdjustVchrTypeEnum getTypeByCode(Integer code) {
        for (AdjustVchrTypeEnum adjustVchrTypeEnum : AdjustVchrTypeEnum.values()) {
            if (!adjustVchrTypeEnum.getCode().equals(code)) continue;
            return adjustVchrTypeEnum;
        }
        return null;
    }

    public static AdjustVchrTypeEnum getTypeByName(String name) {
        for (AdjustVchrTypeEnum adjustVchrTypeEnum : AdjustVchrTypeEnum.values()) {
            if (!adjustVchrTypeEnum.getName().equals(name)) continue;
            return adjustVchrTypeEnum;
        }
        return null;
    }
}

