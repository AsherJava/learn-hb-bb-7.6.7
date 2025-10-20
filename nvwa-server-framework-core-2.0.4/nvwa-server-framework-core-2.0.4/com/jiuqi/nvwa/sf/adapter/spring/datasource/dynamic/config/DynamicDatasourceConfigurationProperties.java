/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.config;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceProperties;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nvwa", ignoreInvalidFields=true)
public class DynamicDatasourceConfigurationProperties {
    private Map<String, NvwaDataSourceProperties> datasources;

    public Map<String, NvwaDataSourceProperties> getDatasources() {
        return this.datasources;
    }

    public void setDatasources(Map<String, NvwaDataSourceProperties> datasources) {
        this.datasources = datasources;
    }

    public String toString() {
        return "DynamicDatasourceConfigurationProperties{datasources=" + this.datasources + '}';
    }
}

