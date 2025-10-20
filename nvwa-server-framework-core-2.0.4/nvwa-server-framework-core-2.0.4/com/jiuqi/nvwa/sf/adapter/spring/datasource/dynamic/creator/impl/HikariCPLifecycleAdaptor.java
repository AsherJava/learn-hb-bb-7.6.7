/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 */
package com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.impl;

import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.bean.NvwaExtDataSourceProperties;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.creator.IDataSourceLifecycleAdaptor;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceCreationException;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.util.DataSourceConfigBinder;
import com.zaxxer.hikari.HikariDataSource;
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
@ConditionalOnClass(value={HikariDataSource.class})
public class HikariCPLifecycleAdaptor
implements IDataSourceLifecycleAdaptor {
    private final Logger logger = LoggerFactory.getLogger(HikariCPLifecycleAdaptor.class);
    private static final String HIKARI_PREFIX = ".hikari";
    private static final String SPRING_DATASOURCE_HIKARI_PREFIX = "spring.datasource.hikari";
    @Autowired
    private Environment environment;

    @Override
    public boolean supports(Class<? extends DataSource> type) {
        return "com.zaxxer.hikari.HikariDataSource".equals(type.getName());
    }

    @Override
    public DataSource createSystemDataSource(String datasourceKey, DataSourceProperties properties) throws DataSourceCreationException {
        try {
            HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
            DataSourceConfigBinder<HikariDataSource> configBinder = new DataSourceConfigBinder<HikariDataSource>(this.environment);
            try {
                configBinder.bindProperties(SPRING_DATASOURCE_HIKARI_PREFIX, dataSource);
            }
            catch (NoSuchElementException ignored) {
                this.logger.info("\u4e3b\u6570\u636e\u6e90\u6ca1\u6709\u81ea\u5b9a\u4e49 Hikari \u8fde\u63a5\u6c60. \u5c06\u4f7f\u7528\u8fde\u63a5\u6c60\u7684\u9ed8\u8ba4\u914d\u7f6e");
            }
            if (StringUtils.hasLength(datasourceKey)) {
                String subDataSourceHikariConfigPrefix = "jiuqi.nvwa.datasources." + datasourceKey + HIKARI_PREFIX;
                try {
                    configBinder.bindProperties(subDataSourceHikariConfigPrefix, dataSource);
                }
                catch (NoSuchElementException ignored) {
                    this.logger.info("\u6570\u636e\u6e90: {} \u6ca1\u6709\u5355\u72ec\u914d\u7f6e Hikari \u8fde\u63a5\u6c60. \u5c06\u4f7f\u7528\u4e3b\u6570\u636e\u6e90\u7684\u76f8\u5173\u914d\u7f6e", (Object)datasourceKey);
                }
            }
            if (properties.getName() != null) {
                dataSource.setPoolName(properties.getName());
            }
            return dataSource;
        }
        catch (Exception e) {
            throw new DataSourceCreationException(e);
        }
    }

    @Override
    public DataSource createExtDataSource(String datasourceKey, NvwaExtDataSourceProperties properties) throws DataSourceCreationException {
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        dataSource.setPoolName(properties.getName());
        dataSource.setIdleTimeout((long)properties.getMaxIdleTime());
        dataSource.setMaximumPoolSize(properties.getMaxPoolSize());
        dataSource.setMinimumIdle(properties.getMinPoolSize());
        dataSource.setDataSourceProperties(properties.getExtProperties());
        return dataSource;
    }

    @Override
    public void terminate(DataSource dataSource) {
        if (dataSource instanceof HikariDataSource) {
            try {
                ((HikariDataSource)dataSource).close();
            }
            catch (Exception e) {
                this.logger.error("\u5173\u95ed HikariCP \u6570\u636e\u6e90\u5931\u8d25", e);
            }
        }
    }
}

