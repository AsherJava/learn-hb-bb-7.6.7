/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.web.bean;

public class BaseSelectItemVO {
    private String key;
    private String code;
    private String title;

    public BaseSelectItemVO(String key, String code, String title) {
        this.key = key;
        this.code = code;
        this.title = title;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

