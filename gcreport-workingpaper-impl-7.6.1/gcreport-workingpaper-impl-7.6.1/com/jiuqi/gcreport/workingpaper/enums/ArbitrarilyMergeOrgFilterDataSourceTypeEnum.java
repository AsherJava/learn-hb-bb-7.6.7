/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.enums;

import java.util.HashMap;
import java.util.Map;

public enum ArbitrarilyMergeOrgFilterDataSourceTypeEnum {
    BASE_DATA("1", "\u57fa\u7840\u6570\u636e"),
    CUSTOM_OPTIONS("2", "\u81ea\u5b9a\u4e49\u9009\u9879");

    private final String type;
    private final String desc;

    private ArbitrarilyMergeOrgFilterDataSourceTypeEnum(String type, String desc) {
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
        for (ArbitrarilyMergeOrgFilterDataSourceTypeEnum status : ArbitrarilyMergeOrgFilterDataSourceTypeEnum.values()) {
            map.put(status.getType(), status.getDesc());
        }
        return map;
    }

    public static String getDescByType(String code) {
        for (ArbitrarilyMergeOrgFilterDataSourceTypeEnum value : ArbitrarilyMergeOrgFilterDataSourceTypeEnum.values()) {
            if (!value.getType().equals(code)) continue;
            return value.getDesc();
        }
        return "";
    }
}

