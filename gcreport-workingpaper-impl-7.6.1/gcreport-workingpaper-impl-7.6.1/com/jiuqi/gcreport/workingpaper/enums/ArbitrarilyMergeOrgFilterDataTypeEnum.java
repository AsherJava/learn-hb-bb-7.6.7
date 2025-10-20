/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.enums;

import java.util.HashMap;
import java.util.Map;

public enum ArbitrarilyMergeOrgFilterDataTypeEnum {
    PREINSTALL("1", "\u9884\u8bbe"),
    CUSTOM("2", "\u81ea\u5b9a\u4e49");

    private final String type;
    private final String desc;

    private ArbitrarilyMergeOrgFilterDataTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }

    public String code() {
        return this.type;
    }

    public String message() {
        return this.desc;
    }

    public static Map<String, String> getAllStatusCode() {
        HashMap<String, String> map = new HashMap<String, String>(16);
        for (ArbitrarilyMergeOrgFilterDataTypeEnum status : ArbitrarilyMergeOrgFilterDataTypeEnum.values()) {
            map.put(status.getType(), status.getDesc());
        }
        return map;
    }

    public static String getDescByType(String code) {
        for (ArbitrarilyMergeOrgFilterDataTypeEnum value : ArbitrarilyMergeOrgFilterDataTypeEnum.values()) {
            if (!value.getType().equals(code)) continue;
            return value.getDesc();
        }
        return "";
    }
}

