/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 *  org.springframework.jdbc.datasource.lookup.DataSourceLookup
 *  org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException
 *  org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.config;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.config.DynamicDatasourceConfigurationProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.IDataSourceLifecycleAdaptor;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DynamicDataSourceInitFailedException;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.NvwaDataSourceDelegator;
import com.zaxxer.hikari.HikariDataSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.jdbc.datasource.lookup.DataSourceLookupFailureException;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nvwa.sf.adapter.spring.datasource"})
@EnableConfigurationProperties(value={DynamicDatasourceConfigurationProperties.class})
@PropertySource(value={"classpath:nvwa-dynamic-datasource.properties"})
public class DynamicDataSourceAutoConfiguration {
    private final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAutoConfiguration.class);
    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Autowired(required=false)
    private DynamicDatasourceConfigurationProperties dynamicDataSourceConfigurationProperties;
    @Autowired(required=false)
    private List<IDataSourceLifecycleAdaptor> dataSourceConfigCreators;
    @Autowired
    private NvwaDataSourceDelegator nvwaDataSourceDelegator;
    private final DataSourceLookup dataSourceLookup = new JndiDataSourceLookup();

    @Bean
    @Primary
    public DynamicDataSource dynamicDataSource() throws DynamicDataSourceInitFailedException {
        Map<String, DataSource> dataSourceMap = this.initDataSourceMap();
        try {
            DataSource mainDataSource = this.createMainDataSource();
            return new DynamicDataSource(mainDataSource, dataSourceMap);
        }
        catch (DataSourceCreationException e) {
            this.logger.error("\u521b\u5efa\u4e3b\u6570\u636e\u6e90\u65f6\u51fa\u73b0\u5f02\u5e38\uff0c\u670d\u52a1\u65e0\u6cd5\u542f\u52a8. \u5f02\u5e38\u4fe1\u606f: {}", (Object)e.getMessage());
            throw new DynamicDataSourceInitFailedException(e.getMessage(), e);
        }
    }

    private DataSource createMainDataSource() throws DataSourceCreationException {
        NvwaDataSourceProperties properties = new NvwaDataSourceProperties();
        BeanUtils.copyProperties(this.dataSourceProperties, properties);
        properties.setTitle("\u4e3b\u6570\u636e\u6e90");
        return this.createDataSource(null, properties);
    }

    private Map<String, DataSource> initDataSourceMap() {
        Map<String, NvwaDataSourceProperties> dataSourcePropertiesMap = this.dynamicDataSourceConfigurationProperties.getDatasources();
        ConcurrentHashMap<String, DataSource> dataSources = new ConcurrentHashMap<String, DataSource>();
        if (dataSourcePropertiesMap == null) {
            return dataSources;
        }
        for (Map.Entry<String, NvwaDataSourceProperties> entry : dataSourcePropertiesMap.entrySet()) {
            String key = entry.getKey();
            NvwaDataSourceProperties properties = entry.getValue();
            Class<? extends DataSource> type = properties.getType();
            if (type != null && !type.equals(this.dataSourceProperties.getType())) {
                this.logger.error("\u6570\u636e\u6e90 {} \u7684\u8fde\u63a5\u6c60\u4e0d\u4e3a\u7a7a, \u4e14\u4e0e\u4e3b\u6570\u636e\u6e90\u4e0d\u540c", (Object)key);
                continue;
            }
            if (type == null) {
                if (this.dataSourceProperties.getType() != null) {
                    this.logger.info("\u6570\u636e\u6e90 {} \u672a\u914d\u7f6e\u8fde\u63a5\u6c60\u7c7b\u578b, \u4f7f\u7528\u4e3b\u6570\u636e\u6e90\u7684\u8fde\u63a5\u6c60\u7c7b\u578b: {}", (Object)key, (Object)this.dataSourceProperties.getType().getName());
                    properties.setType(this.dataSourceProperties.getType());
                } else {
                    properties.setType(HikariDataSource.class);
                }
            }
            DataSource ds = null;
            try {
                ds = this.createDataSource(key, properties);
            }
            catch (DataSourceCreationException e) {
                this.logger.error("\u521b\u5efa\u6570\u636e\u6e90 {} \u65f6\u53d1\u751f\u5f02\u5e38. \u5f02\u5e38\u4fe1\u606f: {}", (Object)key, (Object)e.getMessage());
                this.logger.error(e.getMessage(), e);
            }
            if (ds == null) continue;
            this.logger.info("\u6b63\u5728\u6ce8\u518c\u6570\u636e\u6e90: {}", (Object)key);
            dataSources.put(key, ds);
        }
        return dataSources;
    }

    private DataSource createDataSource(String dataSourceKey, NvwaDataSourceProperties properties) throws DataSourceCreationException {
        String jndiName = properties.getJndiName();
        if (StringUtils.hasLength(jndiName)) {
            DataSource dataSource = this.doCreateJndiDataSource(jndiName);
            return this.nvwaDataSourceDelegator.doDelegate(dataSource);
        }
        Class<? extends DataSource> type = properties.getType();
        if (this.dataSourceConfigCreators == null || this.dataSourceConfigCreators.isEmpty()) {
            throw new DataSourceCreationException("\u6570\u636e\u6e90\u521b\u5efa\u5668\u5217\u8868\u4e3a\u7a7a\uff0c\u65e0\u6cd5\u521b\u5efa\u6570\u636e\u6e90");
        }
        IDataSourceLifecycleAdaptor creator = null;
        for (IDataSourceLifecycleAdaptor configProcessor : this.dataSourceConfigCreators) {
            if (!configProcessor.supports(type)) continue;
            creator = configProcessor;
            break;
        }
        if (creator == null) {
            throw new DataSourceCreationException("\u627e\u4e0d\u5230\u6570\u636e\u6e90\u7c7b\u578b: " + type.getName() + " \u7684\u521b\u5efa\u5668");
        }
        this.logger.info("\u6b63\u5728\u521b\u5efa\u6570\u636e\u6e90, DataSourceKey: {}.", (Object)dataSourceKey);
        try {
            DataSource systemDataSource = creator.createSystemDataSource(dataSourceKey, properties);
            return this.nvwaDataSourceDelegator.doDelegate(systemDataSource);
        }
        catch (DataSourceCreationException e) {
            this.logger.error("\u6570\u636e\u6e90 {} \u521b\u5efa\u5931\u8d25", (Object)dataSourceKey, (Object)e);
            throw e;
        }
    }

    private DataSource doCreateJndiDataSource(String jndiName) throws DataSourceCreationException {
        try {
            return this.dataSourceLookup.getDataSource(jndiName);
        }
        catch (DataSourceLookupFailureException err) {
            throw new DataSourceCreationException("\u521b\u5efa JNDI \u6570\u636e\u6e90: " + jndiName + " \u5931\u8d25, " + err.getMessage(), err);
        }
    }
}

