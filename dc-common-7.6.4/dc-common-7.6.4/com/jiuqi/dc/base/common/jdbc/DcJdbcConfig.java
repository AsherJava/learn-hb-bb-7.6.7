/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.dc.base.common.jdbc;

import com.jiuqi.dc.base.common.jdbc.datasource.IDynamicDataSourceProvider;
import com.jiuqi.dc.base.common.jdbc.datasource.impl.DefaultDynamicDataSourceProvider;
import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DcJdbcConfig {
    @Bean
    @ConditionalOnClass(value={JdbcTemplate.class, IDbSqlHandler.class})
    public IDynamicDataSourceProvider dynamicDataSourceProvider(JdbcTemplate jdbcTemplate, IDbSqlHandler dbSqlHandler) {
        return new DefaultDynamicDataSourceProvider(jdbcTemplate, dbSqlHandler);
    }
}

