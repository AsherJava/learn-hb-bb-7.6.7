/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.ResultSetExtractor
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.np.core.application;

import com.jiuqi.np.core.model.InSqlModel;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public interface NpApplicationSqlHelp {
    public InSqlModel buildInSql(String var1, String var2, List<String> var3, String var4, String var5, boolean var6, String var7);

    default public InSqlModel buildInSql(String alias, List<String> values, String column, String arg) {
        return this.buildInSql("TMP_CORE", alias, values, column, arg, true, null);
    }

    default public InSqlModel buildInSql(String alias, List<String> values, String column, String arg, boolean addWhere) {
        return this.buildInSql("TMP_CORE", alias, values, column, arg, addWhere, null);
    }

    public <T> T query(String var1, Map<String, Object> var2, InSqlModel var3, ResultSetExtractor<T> var4) throws DataAccessException;

    public <T> List<T> query(String var1, Map<String, Object> var2, InSqlModel var3, RowMapper<T> var4) throws DataAccessException;

    public int update(String var1, Map<String, Object> var2, InSqlModel var3);
}

