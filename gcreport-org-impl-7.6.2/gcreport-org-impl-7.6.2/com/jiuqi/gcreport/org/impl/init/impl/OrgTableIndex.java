/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.impl.init.impl;

public class OrgTableIndex {
    private String name;
    private boolean unique;
    private String[] fields;

    public String[] getFields() {
        return this.fields;
    }

    public String getName() {
        return this.name;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }
}

