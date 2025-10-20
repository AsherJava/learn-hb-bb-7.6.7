/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.definition.impl.basic.base.sql;

import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.EntDqlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntBatchPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntPreparedStatementSetter;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.EntResultSetExtractor;
import com.jiuqi.gcreport.definition.impl.basic.base.template.intf.PsSetterFactory;
import java.util.List;

public class EntSqlTool {
    public static EntDmlSql newDmlInstance(final String sql, final List<Object> param) {
        return new EntDmlSql(){

            @Override
            public String getSql() {
                return sql;
            }

            @Override
            public EntPreparedStatementSetter getPreStatementSetter() {
                return PsSetterFactory.newSetter(param);
            }
        };
    }

    public static EntDmlBatchSql newDmlBatchInstance(final String sql, final List<List<Object>> param) {
        return new EntDmlBatchSql(){

            @Override
            public EntBatchPreparedStatementSetter getBatchPreStatementSetter() {
                return PsSetterFactory.newBatchSetter(param, param.size());
            }

            @Override
            public String getSql() {
                return sql;
            }

            @Override
            public EntPreparedStatementSetter getPreStatementSetter() {
                return null;
            }
        };
    }

    public static <K> EntDqlSql<K> newDqlInstance(String sql, List<Object> param, EntResultSetExtractor<K> resultSetExtractor) {
        return EntSqlTool.newDqlInstance(sql, PsSetterFactory.newSetter(param), resultSetExtractor);
    }

    public static <K> EntDqlSql<K> newDqlInstance(String sql, EntPreparedStatementSetter ps, EntResultSetExtractor<K> resultSetExtractor) {
        return EntSqlTool.newEntDqlSql(sql, ps, resultSetExtractor);
    }

    private static <R> EntDqlSql<R> newEntDqlSql(final String sql, final EntPreparedStatementSetter ps, final EntResultSetExtractor<R> resultSetExtractor) {
        return new EntDqlSql<R>(){

            @Override
            public EntPreparedStatementSetter getPreStatementSetter() {
                return ps;
            }

            @Override
            public EntResultSetExtractor<R> getResultSetExtractor() {
                return resultSetExtractor;
            }

            @Override
            public String getSql() {
                return sql;
            }
        };
    }

    public static EntDqlSql<Integer> newDqlCountInstance(String sql, List<Object> param) {
        return EntSqlTool.newDqlCountInstance(sql, PsSetterFactory.newSetter(param));
    }

    private static EntDqlSql<Integer> newDqlCountInstance(String sql, EntPreparedStatementSetter ps) {
        return EntSqlTool.newEntDqlSql(sql, ps, EntSqlTool.newSingleFieldSetter(Integer.class));
    }

    public static <S> EntResultSetExtractor<S> newSingleFieldSetter(Class<S> type) {
        return rs -> rs.getObject(1, type);
    }
}

