/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public class NvwaDataSourceProperties
extends DataSourceProperties {
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String toString() {
        return "NvwaDataSourceProperties{title='" + this.title + '\'' + '}';
    }
}

