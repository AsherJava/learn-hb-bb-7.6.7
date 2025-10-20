/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import javax.sql.DataSource;

public class NvwaDataSourceBasicInfo {
    private String title;
    @JsonIgnore
    private String url;
    @JsonIgnore
    private String driverClassName;
    @JsonIgnore
    private Class<? extends DataSource> type;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return this.driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Class<? extends DataSource> getType() {
        return this.type;
    }

    public void setType(Class<? extends DataSource> type) {
        this.type = type;
    }

    public static NvwaDataSourceBasicInfo fromDataSourceProperties(NvwaDataSourceProperties properties) {
        NvwaDataSourceBasicInfo basicInfo = new NvwaDataSourceBasicInfo();
        basicInfo.setTitle(properties.getTitle());
        basicInfo.setType(properties.getType());
        basicInfo.setUrl(properties.getUrl());
        basicInfo.setDriverClassName(properties.getDriverClassName());
        return basicInfo;
    }

    public static NvwaDataSourceBasicInfo fromExtDataSourceProperties(NvwaExtDataSourceProperties properties) {
        NvwaDataSourceBasicInfo basicInfo = new NvwaDataSourceBasicInfo();
        basicInfo.setTitle(properties.getTitle());
        basicInfo.setType(properties.getType());
        basicInfo.setUrl(properties.getUrl());
        basicInfo.setDriverClassName(properties.getDriverClassName());
        return basicInfo;
    }

    public String toString() {
        return "NvwaDataSourceBasicInfo{title='" + this.title + '\'' + ", url='" + this.url + '\'' + ", driverClassName='" + this.driverClassName + '\'' + ", type=" + this.type + '}';
    }
}

