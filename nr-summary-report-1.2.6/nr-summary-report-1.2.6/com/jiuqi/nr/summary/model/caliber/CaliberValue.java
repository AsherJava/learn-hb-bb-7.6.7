/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.model.caliber;

import java.io.Serializable;

public class CaliberValue
implements Serializable {
    private String value;
    private String title;

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

