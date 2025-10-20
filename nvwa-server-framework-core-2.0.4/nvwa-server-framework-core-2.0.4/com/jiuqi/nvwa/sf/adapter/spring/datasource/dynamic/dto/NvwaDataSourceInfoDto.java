/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.dto;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;

public class NvwaDataSourceInfoDto {
    private String key;
    private String name;
    private String title;
    private String category;
    private String dbType;
    private NvwaDataSourceGroup group;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDbType() {
        return this.dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public NvwaDataSourceGroup getGroup() {
        return this.group;
    }

    public void setGroup(NvwaDataSourceGroup group) {
        this.group = group;
    }

    public String toString() {
        return "NvwaDataSourceInfoDto{key='" + this.key + '\'' + ", name='" + this.name + '\'' + ", title='" + this.title + '\'' + ", category='" + this.category + '\'' + ", dbType='" + this.dbType + '\'' + ", group=" + this.group + '}';
    }
}

