/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.ParameterizedPreparedStatementSetter
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.base.common.jdbc.dao;

import com.jiuqi.dc.base.common.jdbc.sqlhandler.IDbSqlHandler;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface BaseDataCenterDao {
    public Connection getConnection();

    public void closeConnection(Connection var1);

    public JdbcTemplate getJdbcTemplate();

    public JdbcTemplate getJdbcTemplate(String var1);

    public IDbSqlHandler getDbSqlHandler();

    public IDbSqlHandler getDbSqlHandler(String var1);

    public int update(String var1, Object ... var2);

    public int[] batchUpdate(String var1, List<Object[]> var2);

    public int[] batchUpdate(String var1, BatchPreparedStatementSetter var2);

    public <T> int[][] batchUpdate(String var1, Collection<T> var2, int var3, ParameterizedPreparedStatementSetter<T> var4);

    public <T> List<T> query(String var1, RowMapper<T> var2, Object ... var3);

    public <T> T query(String var1, ResultSetExtractor<T> var2, Object ... var3);

    public int update(String var1, String var2, Object[] var3);

    public int[] batchUpdate(String var1, String var2, List<Object[]> var3);

    public int[] batchUpdate(String var1, String var2, BatchPreparedStatementSetter var3);

    public <T> int[][] batchUpdate(String var1, String var2, Collection<T> var3, int var4, ParameterizedPreparedStatementSetter<T> var5);

    public <T> List<T> query(String var1, String var2, RowMapper<T> var3, Object ... var4);

    public <T> T query(String var1, String var2, ResultSetExtractor<T> var3, Object ... var4);
}

