/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.query.datasource.enumerate.DataSourceEnum
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.dc.mappingscheme.impl.service;

import com.jiuqi.va.query.datasource.enumerate.DataSourceEnum;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface DataSourceService {
    public static final String CURRENT = DataSourceEnum.CURRENT.getName();

    public String getDbType(String var1);

    public Connection getConnection(String var1);

    public void closeConnection(String var1, Connection var2);

    public void releaseResource(String var1, Connection var2, Statement var3, ResultSet var4);

    public <T> T query(String var1, String var2, Object[] var3, ResultSetExtractor<T> var4);

    public <T> List<T> query(String var1, String var2, Object[] var3, RowMapper<T> var4);

    public <T> T pageQuery(String var1, String var2, int var3, int var4, Object[] var5, ResultSetExtractor<T> var6);

    public <T> List<T> pageQuery(String var1, String var2, int var3, int var4, Object[] var5, RowMapper<T> var6);

    public int[] batchUpdate(String var1, String var2, List<Object[]> var3);

    public void execute(String var1, String var2);
}

