/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.va.query.datasource.vo;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataSourceTempInfo {
    private JdbcTemplate jdbcTemplate;
    private Long createTime;

    public DataSourceTempInfo() {
    }

    public DataSourceTempInfo(HikariDataSource dataSource, Long createTime) {
        this.jdbcTemplate = new JdbcTemplate((DataSource)dataSource);
        this.createTime = createTime;
    }

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}

