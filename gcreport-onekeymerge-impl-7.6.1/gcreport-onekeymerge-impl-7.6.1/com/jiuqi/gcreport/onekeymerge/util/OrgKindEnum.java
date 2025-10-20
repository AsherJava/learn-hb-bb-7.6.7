/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.onekeymerge.util;

public enum OrgKindEnum {
    MERGE("MERGE", "\u5408\u5e76\u5c42\u7ea7"),
    SINGLE("SINGLE", "\u5355\u6237\u5c42\u7ea7");

    private String code;
    private String title;

    private OrgKindEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

