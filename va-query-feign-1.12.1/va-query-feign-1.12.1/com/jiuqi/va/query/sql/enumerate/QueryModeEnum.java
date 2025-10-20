/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.sql.enumerate;

public enum QueryModeEnum {
    singleData("singleData", "\u5355\u503c\u67e5\u8be2"),
    mutileData("mutileData", "\u591a\u503c\u67e5\u8be2"),
    scope("scope", "\u8303\u56f4");

    private String modeName;
    private String modeSign;

    private QueryModeEnum(String modeSign, String modeName) {
        this.modeSign = modeSign;
        this.modeName = modeName;
    }

    public String getModeName() {
        return this.modeName;
    }

    public String getModeSign() {
        return this.modeSign;
    }

    public static QueryModeEnum getQueryModeEnum(String modeSign) {
        for (QueryModeEnum queryModeEnum : QueryModeEnum.values()) {
            if (!queryModeEnum.getModeSign().equals(modeSign)) continue;
            return queryModeEnum;
        }
        return null;
    }
}

