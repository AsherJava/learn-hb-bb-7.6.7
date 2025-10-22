/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.enums;

import java.util.HashMap;
import java.util.Map;

public enum ArbitrarilyMergeOrgFilterControlTypeEnum {
    DROPDOWN_LIST_SINGLE("1", "\u4e0b\u62c9\u5217\u8868\u5355\u9009"),
    DROPDOWN_LIST_MULTIPLE("2", "\u4e0b\u62c9\u5217\u8868\u591a\u9009"),
    SINGLE_OPTION("3", "\u5355\u9009\u6846"),
    RADIO_OPTION("4", "\u590d\u9009\u6846"),
    BASE_DATA("5", "\u57fa\u7840\u6570\u636e\u9009\u62e9\u63a7\u4ef6"),
    ORG_SINGLE("6", "\u7ec4\u7ec7\u673a\u6784\u591a\u9009\u63a7\u4ef6"),
    DROPDOWN_TREE_SINGLE("7", "\u4e0b\u62c9\u6811\u5f62\u5355\u9009"),
    DROPDOWN_TREE_MULTIPLE("8", "\u4e0b\u62c9\u6811\u5f62\u591a\u9009");

    private final String type;
    private final String desc;

    private ArbitrarilyMergeOrgFilterControlTypeEnum(String type, String desc) {
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
        for (ArbitrarilyMergeOrgFilterControlTypeEnum status : ArbitrarilyMergeOrgFilterControlTypeEnum.values()) {
            map.put(status.getType(), status.getDesc());
        }
        return map;
    }

    public static String getDescByType(String code) {
        for (ArbitrarilyMergeOrgFilterControlTypeEnum value : ArbitrarilyMergeOrgFilterControlTypeEnum.values()) {
            if (!value.getType().equals(code)) continue;
            return value.getDesc();
        }
        return "";
    }
}

