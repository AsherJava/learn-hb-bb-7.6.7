/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.db;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="spring.datasource")
public class DataSourceConfig {
    private String dbVersion;

    public String getDbVersion() {
        return this.dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }
}

