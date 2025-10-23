/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.web.facade;

public class BaseDataVO {
    protected String key;
    protected String code;
    protected String title;
    protected String desc;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BaseDataVO() {
    }

    public BaseDataVO(String key, String code, String title, String desc) {
        this.key = key;
        this.code = code;
        this.title = title;
        this.desc = desc;
    }
}

