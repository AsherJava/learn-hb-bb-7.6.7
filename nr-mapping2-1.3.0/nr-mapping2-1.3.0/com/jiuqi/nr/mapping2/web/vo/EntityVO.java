/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping2.web.vo;

import java.io.Serializable;

public class EntityVO
implements Serializable {
    private String key;
    private String title;
    private String code;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
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

