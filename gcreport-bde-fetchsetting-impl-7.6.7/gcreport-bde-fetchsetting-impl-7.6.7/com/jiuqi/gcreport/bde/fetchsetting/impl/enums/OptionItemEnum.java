/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.enums;

@Deprecated
public enum OptionItemEnum {
    ACCTYEAR("acctYear", "\u6307\u5b9a\u5e74\u5ea6"),
    ACCTPERIOD("acctPeriod", "\u6307\u5b9a\u671f\u95f4"),
    ORGCODE("orgCode", "\u6307\u5b9a\u5355\u4f4d"),
    CURRENCYCODE("currencyCode", "\u6307\u5b9a\u5e01\u522b");

    private String code;
    private String name;

    private OptionItemEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static OptionItemEnum getEnumByCode(String code) {
        for (OptionItemEnum optionItemEnum : OptionItemEnum.values()) {
            if (!optionItemEnum.getCode().equals(code)) continue;
            return optionItemEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684OptionItemEnum\u679a\u4e3e code=" + code);
    }
}

