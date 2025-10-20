/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.constant;

public enum DataRegionTypeEnum {
    FIXED("FIXED", "\u56fa\u5b9a\u533a\u57df"),
    FLOAT("FLOAT", "\u6d6e\u52a8\u533a\u57df"),
    COL("COL", "\u5217\u65b9\u5411"),
    ROW("ROW", "\u884c\u65b9\u5411");

    private String code;
    private String name;

    private DataRegionTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static DataRegionTypeEnum getEnumByCode(String dataRegionTypeCode) {
        for (DataRegionTypeEnum dataRegionTypeEnum : DataRegionTypeEnum.values()) {
            if (!dataRegionTypeEnum.getCode().equals(dataRegionTypeCode)) continue;
            return dataRegionTypeEnum;
        }
        throw new RuntimeException("\u65e0\u6b64\u7c7b\u578b\u7684DataRegionTypeEnum\u679a\u4e3e code=" + dataRegionTypeCode);
    }
}

