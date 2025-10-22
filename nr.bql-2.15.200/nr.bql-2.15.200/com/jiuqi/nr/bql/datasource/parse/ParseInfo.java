/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bql.datasource.parse;

public class ParseInfo {
    private boolean isEntityFilter;
    private boolean isBiSyntax;

    public boolean isEntityFilter() {
        return this.isEntityFilter;
    }

    public void setEntityFilter(boolean isEntityFilter) {
        this.isEntityFilter = isEntityFilter;
    }

    public boolean isBiSyntax() {
        return this.isBiSyntax;
    }

    public void setBiSyntax(boolean isBiSyntax) {
        this.isBiSyntax = isBiSyntax;
    }
}

