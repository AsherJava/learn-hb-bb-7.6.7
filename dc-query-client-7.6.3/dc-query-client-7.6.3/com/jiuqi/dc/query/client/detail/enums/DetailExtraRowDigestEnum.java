/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.enums;

public enum DetailExtraRowDigestEnum {
    NC("NC", "\u5e74\u521d\u4f59\u989d"),
    CLJ("CLJ", "\u671f\u521d\u7d2f\u8ba1"),
    CYE("CYE", "\u671f\u521d\u4f59\u989d"),
    BQ("BQ", "\u672c\u671f\u5408\u8ba1"),
    BNLJ("BNLJ", "\u672c\u5e74\u7d2f\u8ba1");

    private String code;
    private String name;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    private DetailExtraRowDigestEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DetailExtraRowDigestEnum getPreDataByCode(String code) {
        for (DetailExtraRowDigestEnum detailExtraRowDigestEnum : DetailExtraRowDigestEnum.values()) {
            if (!detailExtraRowDigestEnum.getCode().equals(code)) continue;
            return detailExtraRowDigestEnum;
        }
        return null;
    }

    public static DetailExtraRowDigestEnum getPreDataByName(String name) {
        for (DetailExtraRowDigestEnum detailExtraRowDigestEnum : DetailExtraRowDigestEnum.values()) {
            if (!detailExtraRowDigestEnum.getName().equals(name)) continue;
            return detailExtraRowDigestEnum;
        }
        return null;
    }
}

