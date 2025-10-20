/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.common;

public enum MonitorOrgTypeEnum {
    ALL_ORG(0, "\u6240\u6709\u7ec4\u7ec7\u54cd\u5e94"),
    LEAF_ORG(1, "\u4ec5\u672b\u7ea7\u7ec4\u7ec7\u673a\u6784\u54cd\u5e94"),
    PARENT_ORG(-1, "\u4ec5\u5408\u5e76\u5355\u4f4d\u54cd\u5e94");

    private int code;
    private String title;

    private MonitorOrgTypeEnum(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public int getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static MonitorOrgTypeEnum getInstance(Integer code) {
        MonitorOrgTypeEnum[] types;
        if (code == null) {
            return ALL_ORG;
        }
        for (MonitorOrgTypeEnum tempType : types = MonitorOrgTypeEnum.values()) {
            if (tempType.getCode() != code.intValue()) continue;
            return tempType;
        }
        return null;
    }
}

