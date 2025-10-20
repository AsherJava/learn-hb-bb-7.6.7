/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.dbcp2.BasicDataSource
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.impl;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.IDataSourceLifecycleAdaptor;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util.DataSourceConfigBinder;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConditionalOnClass(value={BasicDataSource.class})
public class Dbcp2LifecycleAdaptor
implements IDataSourceLifecycleAdaptor {
    private final Logger logger = LoggerFactory.getLogger(Dbcp2LifecycleAdaptor.class);
    private static final String DBCP2_PREFIX = ".dbcp2";
    private static final String SPRING_DATASOURCE_DBCP2_PREFIX = "spring.datasource.dbcp2";
    @Autowired
    private Environment environment;

    @Override
    public DataSource createSystemDataSource(String datasourceKey, DataSourceProperties properties) throws DataSourceCreationException {
        try {
            BasicDataSource dataSource = properties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
            DataSourceConfigBinder<BasicDataSource> configBinder = new DataSourceConfigBinder<BasicDataSource>(this.environment);
            try {
                configBinder.bindProperties(SPRING_DATASOURCE_DBCP2_PREFIX, dataSource);
            }
            catch (NoSuchElementException ignored) {
                this.logger.info("\u4e3b\u6570\u636e\u6e90\u6ca1\u6709\u81ea\u5b9a\u4e49 Dbcp2 \u8fde\u63a5\u6c60. \u5c06\u4f7f\u7528\u8fde\u63a5\u6c60\u7684\u9ed8\u8ba4\u914d\u7f6e");
            }
            if (StringUtils.hasLength(datasourceKey)) {
                String subDataSourceDbcp2ConfigPrefix = "jiuqi.nvwa.datasources." + datasourceKey + DBCP2_PREFIX;
                try {
                    configBinder.bindProperties(subDataSourceDbcp2ConfigPrefix, dataSource);
                }
                catch (NoSuchElementException ignored) {
                    this.logger.info("\u6570\u636e\u6e90: {} \u6ca1\u6709\u5355\u72ec\u914d\u7f6e Dbcp2 \u8fde\u63a5\u6c60. \u5c06\u4f7f\u7528\u4e3b\u6570\u636e\u6e90\u7684\u76f8\u5173\u914d\u7f6e", (Object)datasourceKey);
                }
            }
            return dataSource;
        }
        catch (Exception e) {
            throw new DataSourceCreationException(e);
        }
    }

    @Override
    public DataSource createExtDataSource(String datasourceKey, NvwaExtDataSourceProperties properties) throws DataSourceCreationException {
        BasicDataSource dataSource = properties.initializeDataSourceBuilder().type(BasicDataSource.class).build();
        dataSource.setMaxIdle(properties.getMaxIdleTime());
        dataSource.setMaxTotal(properties.getMaxPoolSize());
        dataSource.setMaxWaitMillis((long)properties.getMaxWaitTime());
        dataSource.setMinIdle(properties.getMinPoolSize());
        return dataSource;
    }

    @Override
    public void terminate(DataSource dataSource) {
        if (dataSource instanceof BasicDataSource) {
            try {
                ((BasicDataSource)dataSource).close();
            }
            catch (Exception e) {
                this.logger.error("\u5173\u95ed\u6570\u636e\u6e90\u5931\u8d25", e);
            }
        }
    }

    @Override
    public boolean supports(Class<? extends DataSource> type) {
        return "org.apache.commons.dbcp2.BasicDataSource".equals(type.getName());
    }
}

