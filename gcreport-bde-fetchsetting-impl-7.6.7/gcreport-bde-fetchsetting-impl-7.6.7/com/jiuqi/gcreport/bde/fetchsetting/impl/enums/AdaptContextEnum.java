/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.enums;

public enum AdaptContextEnum {
    MD_ORG("MD_ORG", "\u5355\u4f4d"),
    DATATIME("DATATIME", "\u65f6\u671f"),
    MD_GCORGTYPE("MD_GCORGTYPE", "\u5355\u4f4d\u7c7b\u578b");

    private String key;
    private String name;

    private AdaptContextEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }
}

