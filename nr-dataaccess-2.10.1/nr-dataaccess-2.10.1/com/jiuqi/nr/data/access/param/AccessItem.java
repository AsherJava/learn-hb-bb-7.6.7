/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.param;

public class AccessItem {
    private String name;
    private Object params;

    public AccessItem(String name, Object params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getParams() {
        return this.params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}

