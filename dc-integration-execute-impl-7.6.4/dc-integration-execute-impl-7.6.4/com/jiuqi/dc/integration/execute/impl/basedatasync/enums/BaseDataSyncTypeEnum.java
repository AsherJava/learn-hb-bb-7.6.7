/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.basedatasync.enums;

public enum BaseDataSyncTypeEnum {
    ALL("ALL", "\u5168\u91cf"),
    ADD("ADD", "\u589e\u91cf");

    private String code;
    private String name;

    private BaseDataSyncTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}

