/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.datasource;

public class DataSourceCandidateFieldInfo {
    private String name;
    private String title;

    public DataSourceCandidateFieldInfo() {
    }

    public DataSourceCandidateFieldInfo(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

