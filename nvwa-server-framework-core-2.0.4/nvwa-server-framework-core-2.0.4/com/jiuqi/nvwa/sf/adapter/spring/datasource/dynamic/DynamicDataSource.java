/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  javax.annotation.Nonnull
 *  org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceBasicInfo;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.service.IDynamicDataSourceManager;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util.DynamicDataSourceContextHolder;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DecoratedDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.sql.DataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource
extends AbstractRoutingDataSource
implements InitializingBean {
    public static final String DEFAULT_DATASOURCE_KEY = "_default";
    private final DataSource defaultDataSource;
    private final Map<Object, Object> datasourceMap;
    private final Map<String, DataSource> resolvedDataSourceMap;

    public DynamicDataSource(DataSource defaultDataSource, Map<String, DataSource> customizeDataSources) {
        this.defaultDataSource = defaultDataSource;
        this.datasourceMap = new ConcurrentHashMap<String, DataSource>(customizeDataSources);
        this.resolvedDataSourceMap = new ConcurrentHashMap<String, DataSource>();
        super.setTargetDataSources(this.datasourceMap);
        super.setDefaultTargetDataSource((Object)defaultDataSource);
    }

    @Nonnull
    protected Object determineCurrentLookupKey() {
        String dataSourceKey = DynamicDataSourceContextHolder.getDataSourceKey();
        if (dataSourceKey != null) {
            return dataSourceKey;
        }
        return DEFAULT_DATASOURCE_KEY;
    }

    @Deprecated
    public Set<String> getDataSourceKeySet() {
        return ((IDynamicDataSourceManager)SpringBeanUtils.getBean(IDynamicDataSourceManager.class)).getDatasourceKeys();
    }

    @Deprecated
    public NvwaDataSourceBasicInfo getDataSourceBasicInfo(String key) throws DataSourceNotFoundException {
        return ((IDynamicDataSourceManager)SpringBeanUtils.getBean(IDynamicDataSourceManager.class)).getDatasourceBasicInfo(key);
    }

    public DataSource getDataSource(String key) throws DataSourceNotFoundException {
        if (DEFAULT_DATASOURCE_KEY.equals(key)) {
            return this.defaultDataSource;
        }
        if (!this.datasourceMap.containsKey(key)) {
            throw new DataSourceNotFoundException("\u6570\u636e\u6e90\u4e0d\u5b58\u5728\u3002key: " + key);
        }
        return (DataSource)this.datasourceMap.get(key);
    }

    public Connection getConnection(String key) throws DataSourceNotFoundException, SQLException {
        return this.getDataSource(key).getConnection();
    }

    public void addDataSource(String key, DataSource dataSource) {
        this.datasourceMap.put(key, dataSource);
        this.resolvedDataSourceMap.put(key, dataSource);
    }

    public DataSource removeDataSource(String key) {
        this.datasourceMap.remove(key);
        return this.resolvedDataSourceMap.remove(key);
    }

    @Nonnull
    protected DataSource determineTargetDataSource() {
        Object lookupKeyObj = this.determineCurrentLookupKey();
        String lookupKey = lookupKeyObj.toString();
        if (DEFAULT_DATASOURCE_KEY.equals(lookupKey)) {
            return this.defaultDataSource;
        }
        if (!this.resolvedDataSourceMap.containsKey(lookupKey)) {
            throw new DataSourceNotFoundException("\u6570\u636e\u6e90 {} \u4e0d\u5b58\u5728" + lookupKey);
        }
        return this.resolvedDataSourceMap.get(lookupKey);
    }

    @Nonnull
    public Map<Object, DataSource> getResolvedDataSources() {
        return Collections.unmodifiableMap(this.resolvedDataSourceMap);
    }

    public DataSource getResolvedDefaultDataSource() {
        if (this.defaultDataSource instanceof DecoratedDataSource) {
            return ((DecoratedDataSource)((Object)this.defaultDataSource)).getRealDataSource();
        }
        return this.defaultDataSource;
    }

    @Override
    public void afterPropertiesSet() {
        this.datasourceMap.forEach((key, value) -> {
            DataSource dataSource = this.resolveSpecifiedDataSource(value);
            this.resolvedDataSourceMap.put(key.toString(), dataSource);
        });
    }
}

