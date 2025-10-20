/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.enums;

public enum DetailPreDataEnum {
    NC("NC", "\u5e74\u521d\u672c\u5e01\u4f59\u989d"),
    WNC("WNC", "\u5e74\u521d\u539f\u5e01\u4f59\u989d"),
    CJ("CJ", "\u671f\u521d\u7d2f\u8ba1\u672c\u5e01\u501f\u65b9"),
    WJC("WJC", "\u671f\u521d\u7d2f\u8ba1\u539f\u5e01\u501f\u65b9"),
    CD("CD", "\u671f\u521d\u7d2f\u8ba1\u672c\u5e01\u8d37\u65b9"),
    WDC("WDC", "\u671f\u521d\u7d2f\u8ba1\u539f\u5e01\u8d37\u65b9"),
    JL("JL", "\u672c\u671f\u5408\u8ba1\u672c\u5e01\u501f\u65b9"),
    WJL("WJL", "\u672c\u671f\u5408\u8ba1\u539f\u5e01\u501f\u65b9"),
    DL("DL", "\u672c\u671f\u5408\u8ba1\u672c\u5e01\u8d37\u65b9"),
    WDL("WDL", "\u672c\u671f\u5408\u8ba1\u539f\u5e01\u8d37\u65b9");

    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private DetailPreDataEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DetailPreDataEnum getPreDataByCode(String code) {
        for (DetailPreDataEnum detailPreDataEnum : DetailPreDataEnum.values()) {
            if (!detailPreDataEnum.getCode().equals(code)) continue;
            return detailPreDataEnum;
        }
        return null;
    }

    public static DetailPreDataEnum getPreDataByName(String name) {
        for (DetailPreDataEnum detailPreDataEnum : DetailPreDataEnum.values()) {
            if (!detailPreDataEnum.getName().equals(name)) continue;
            return detailPreDataEnum;
        }
        return null;
    }
}

