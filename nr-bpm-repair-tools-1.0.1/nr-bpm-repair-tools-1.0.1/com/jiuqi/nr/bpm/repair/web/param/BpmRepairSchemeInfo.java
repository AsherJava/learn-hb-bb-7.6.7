/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.repair.web.param;

public class BpmRepairSchemeInfo {
    private String code;
    private String title;

    public BpmRepairSchemeInfo(String code, String title) {
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

