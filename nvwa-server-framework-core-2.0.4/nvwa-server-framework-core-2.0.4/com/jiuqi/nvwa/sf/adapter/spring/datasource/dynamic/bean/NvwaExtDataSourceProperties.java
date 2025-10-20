/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceGroup;
import java.util.Properties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

public class NvwaExtDataSourceProperties
extends DataSourceProperties {
    private String title;
    private NvwaDataSourceGroup group;
    private String category;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxIdleTime;
    private int maxWaitTime;
    private Properties extProperties;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NvwaDataSourceGroup getGroup() {
        return this.group;
    }

    public void setGroup(NvwaDataSourceGroup group) {
        this.group = group;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getMinPoolSize() {
        return this.minPoolSize;
    }

    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMaxIdleTime() {
        return this.maxIdleTime;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public int getMaxWaitTime() {
        return this.maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public Properties getExtProperties() {
        return this.extProperties;
    }

    public void setExtProperties(Properties extProperties) {
        this.extProperties = extProperties;
    }

    public String toString() {
        return "NvwaExtDataSourceProperties{title='" + this.title + '\'' + ", group=" + this.group + ", category='" + this.category + '\'' + ", minPoolSize=" + this.minPoolSize + ", maxPoolSize=" + this.maxPoolSize + ", maxIdleTime=" + this.maxIdleTime + ", maxWaitTime=" + this.maxWaitTime + ", extProperties=" + this.extProperties + "} " + super.toString();
    }
}

