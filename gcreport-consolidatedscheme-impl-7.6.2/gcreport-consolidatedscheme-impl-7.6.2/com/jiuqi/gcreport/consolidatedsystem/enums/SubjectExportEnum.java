/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.enums;

public enum SubjectExportEnum {
    CODE("code", "\u79d1\u76ee\u7f16\u7801"),
    TITLE("title", "\u79d1\u76ee\u540d\u79f0"),
    PARENTCODE("parentCode", "\u4e0a\u7ea7\u79d1\u76ee\u7f16\u7801"),
    STATUS("status", "\u542f\u7528/\u505c\u7528"),
    CONSOLIDATIONTYPE("consolidationType", "\u5408\u5e76\u65b9\u5f0f"),
    ORIENT("orient", "\u501f\u8d37\u65b9\u5411"),
    ATTRI("attri", "\u9879\u76ee\u5927\u7c7b"),
    BOUNDINDEXPATH("boundIndexPath", "\u5173\u8054\u6307\u6807");

    private String code;
    private String title;

    private SubjectExportEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

