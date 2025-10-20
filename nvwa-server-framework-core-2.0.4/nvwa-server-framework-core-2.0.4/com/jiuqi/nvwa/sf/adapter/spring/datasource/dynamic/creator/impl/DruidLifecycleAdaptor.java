/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.druid.pool.DruidDataSource
 *  com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceWrapper;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.IDataSourceLifecycleAdaptor;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util.DataSourceConfigBinder;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConditionalOnClass(value={DruidDataSourceWrapper.class})
public class DruidLifecycleAdaptor
implements IDataSourceLifecycleAdaptor {
    private final Logger logger = LoggerFactory.getLogger(DruidLifecycleAdaptor.class);
    private static final String DRUID_PREFIX = ".druid";
    private static final String SPRING_DATASOURCE_DRUID_PREFIX = "spring.datasource.druid";
    @Autowired
    private Environment environment;

    @Override
    public DataSource createSystemDataSource(String datasourceKey, DataSourceProperties properties) throws DataSourceCreationException {
        try {
            DruidDataSource druidDataSource = this.buildBasicDataSource(properties);
            DataSourceConfigBinder<DruidDataSource> configBinder = new DataSourceConfigBinder<DruidDataSource>(this.environment);
            try {
                configBinder.bindProperties(SPRING_DATASOURCE_DRUID_PREFIX, druidDataSource);
            }
            catch (NoSuchElementException ignored) {
                this.logger.info("\u4e3b\u6570\u636e\u6e90\u6ca1\u6709\u81ea\u5b9a\u4e49 Druid \u8fde\u63a5\u6c60. \u5c06\u4f7f\u7528\u8fde\u63a5\u6c60\u7684\u9ed8\u8ba4\u914d\u7f6e");
            }
            if (StringUtils.hasLength(datasourceKey)) {
                String subDataSourceDruidConfigPrefix = "jiuqi.nvwa.datasources." + datasourceKey + DRUID_PREFIX;
                try {
                    configBinder.bindProperties(subDataSourceDruidConfigPrefix, druidDataSource);
                }
                catch (NoSuchElementException ignored) {
                    this.logger.info("\u6570\u636e\u6e90: {} \u6ca1\u6709\u5355\u72ec\u914d\u7f6e Druid \u8fde\u63a5\u6c60. \u5c06\u4f7f\u7528\u4e3b\u6570\u636e\u6e90\u7684\u76f8\u5173\u914d\u7f6e", (Object)datasourceKey);
                }
            }
            if (properties.getName() != null) {
                druidDataSource.setName(properties.getName());
            }
            return druidDataSource;
        }
        catch (Exception e) {
            throw new DataSourceCreationException(e);
        }
    }

    @Override
    public DataSource createExtDataSource(String datasourceKey, NvwaExtDataSourceProperties properties) throws DataSourceCreationException {
        DruidDataSource druidDataSource = this.buildBasicDataSource(properties);
        druidDataSource.setMaxActive(properties.getMaxPoolSize());
        druidDataSource.setMinIdle(properties.getMinPoolSize());
        druidDataSource.setMaxWait((long)properties.getMaxWaitTime());
        druidDataSource.setMinIdle(properties.getMinPoolSize());
        druidDataSource.configFromPropety(properties.getExtProperties());
        return druidDataSource;
    }

    @Override
    public void terminate(DataSource dataSource) {
        if (dataSource instanceof DruidDataSource) {
            try {
                ((DruidDataSource)dataSource).close();
            }
            catch (Exception e) {
                this.logger.error("\u5173\u95ed Druid \u6570\u636e\u6e90\u5931\u8d25", e);
            }
        }
    }

    private DruidDataSource buildBasicDataSource(DataSourceProperties properties) {
        DruidDataSourceWrapper dataSourceWrapper = new DruidDataSourceWrapper();
        dataSourceWrapper.setUsername(properties.getUsername());
        dataSourceWrapper.setPassword(properties.getPassword());
        dataSourceWrapper.setUrl(properties.getUrl());
        dataSourceWrapper.setDriverClassName(properties.getDriverClassName());
        return dataSourceWrapper;
    }

    @Override
    public boolean supports(Class<? extends DataSource> type) {
        return "com.alibaba.druid.pool.DruidDataSource".equals(type.getName());
    }
}

