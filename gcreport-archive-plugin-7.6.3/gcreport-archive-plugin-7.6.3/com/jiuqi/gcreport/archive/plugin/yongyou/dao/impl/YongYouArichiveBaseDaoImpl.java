/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.archive.plugin.yongyou.dao.impl;

import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.nvwa.sf.adapter.spring.datasource.dynamic.DynamicDataSource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class YongYouArichiveBaseDaoImpl {
    private JdbcTemplate jdbcTemplate;
    private final String datasource = "yongyouarchive";

    public JdbcTemplate getJdbcTemplate() {
        if (this.jdbcTemplate != null) {
            return this.jdbcTemplate;
        }
        try {
            DynamicDataSource dynamicDataSource = (DynamicDataSource)SpringContextUtils.getBean(DynamicDataSource.class);
            DataSource dataSource = dynamicDataSource.getDataSource("yongyouarchive");
            this.jdbcTemplate = new JdbcTemplate(dataSource);
            return this.jdbcTemplate;
        }
        catch (Exception exception) {
            this.jdbcTemplate = (JdbcTemplate)SpringContextUtils.getBean(JdbcTemplate.class);
            return this.jdbcTemplate;
        }
    }
}

