/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.component.domain;

public class FunParameter {
    private String name;
    private String title;
    private String dataType;
    private boolean omitable;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setOmitable(boolean omitable) {
        this.omitable = omitable;
    }

    public boolean isOmitable() {
        return this.omitable;
    }
}

