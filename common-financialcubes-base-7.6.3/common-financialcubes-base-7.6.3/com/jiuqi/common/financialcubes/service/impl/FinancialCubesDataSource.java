/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.common.financialcubes.service.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.financialcubes.properties.FinancialCubesDataSourceProperties;
import com.jiuqi.common.financialcubes.service.impl.FinancialCubesCurrentDataSourceGather;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.exception.DataSourceNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesDataSource {
    @Resource
    private JdbcTemplate jdbcTemplate;
    private final Map<String, JdbcTemplate> dbCodeToJdbcMap = new ConcurrentHashMap<String, JdbcTemplate>();
    @Resource
    private FinancialCubesCurrentDataSourceGather currentDataSourceGather;
    @Autowired
    private FinancialCubesDataSourceProperties dataSourceProperties;

    public JdbcTemplate getJdbcTemplateByCode(String dbCode) {
        if (this.currentDataSourceGather.containsDataSourceCode(dbCode)) {
            return this.jdbcTemplate;
        }
        if (this.dbCodeToJdbcMap.containsKey(dbCode)) {
            return this.dbCodeToJdbcMap.get(dbCode);
        }
        DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringContextUtils.getBean(DynamicDataSource.class);
        try {
            DataSource dataSource = dynamicDataSource.getDataSource(dbCode);
            this.dbCodeToJdbcMap.put(dbCode, new JdbcTemplate(dataSource));
            new JdbcTemplate();
        }
        catch (DataSourceNotFoundException e) {
            throw new RuntimeException("\u6839\u636e\u6570\u636e\u6e90code\u3010" + dbCode + "\u6ca1\u80fd\u627e\u5230\u5bf9\u5e94\u7684\u52a8\u6001\u6570\u636e\u6e90\u3011", e);
        }
        return this.dbCodeToJdbcMap.get(dbCode);
    }

    public String getDbType(String dbCode) {
        if (this.currentDataSourceGather.containsDataSourceCode(dbCode)) {
            return this.dataSourceProperties.getCurrentDbType();
        }
        if (!StringUtils.isEmpty((String)this.dataSourceProperties.getGcDbType())) {
            return this.dataSourceProperties.getGcDbType();
        }
        return this.dataSourceProperties.getCurrentDbType();
    }
}

