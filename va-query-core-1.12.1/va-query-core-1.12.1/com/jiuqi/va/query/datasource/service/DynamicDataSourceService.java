/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.va.query.datasource.service;

import java.sql.Connection;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface DynamicDataSourceService {
    public Connection getConnection(String var1);

    public JdbcTemplate getJdbcTemplate(String var1);

    public void closeConnection(String var1, Connection var2);

    public <T> T query(String var1, String var2, Object[] var3, ResultSetExtractor<T> var4);

    public <T> List<T> query(String var1, String var2, Object[] var3, RowMapper<T> var4);

    public String getPageSql(String var1, String var2, int var3, int var4);

    public <T> T pageQuery(String var1, String var2, int var3, int var4, Object[] var5, ResultSetExtractor<T> var6);

    public <T> List<T> pageQuery(String var1, String var2, int var3, int var4, Object[] var5, RowMapper<T> var6);

    public int[] batchUpdate(String var1, String var2, List<Object[]> var3);

    public void execute(String var1, String var2);
}

