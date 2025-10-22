/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.nlpr;

import java.io.Serializable;

public class NlprDataentryParam
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String app;
    private String type;
    private String value;

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

