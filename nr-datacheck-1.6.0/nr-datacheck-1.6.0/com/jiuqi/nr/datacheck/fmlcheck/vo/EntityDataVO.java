/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.fmlcheck.vo;

import java.io.Serializable;

public class EntityDataVO
implements Serializable {
    private static final long serialVersionUID = -6421795419364924637L;
    private String value;
    private String title;

    public EntityDataVO(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

