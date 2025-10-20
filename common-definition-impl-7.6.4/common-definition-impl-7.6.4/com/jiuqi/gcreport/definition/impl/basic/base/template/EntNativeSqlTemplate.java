/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  org.springframework.jdbc.core.BatchPreparedStatementSetter
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.gcreport.definition.impl.basic.base.template;

import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.EntSqlTool;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlBatchSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dml.EntDmlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.sql.dql.EntDqlSql;
import com.jiuqi.gcreport.definition.impl.basic.base.template.wapper.EntPreparedStatementWrapper;
import com.jiuqi.gcreport.definition.impl.basic.base.template.wapper.EntResultSetWrapper;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.definition.impl.exception.DefinitionSqlException;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class EntNativeSqlTemplate
implements FEntSqlTemplate {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private IDatabase database;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    public IDatabase getDatabase() throws SQLException {
        if (this.database == null) {
            try (Connection connection = this.getJdbcTemplate().getDataSource().getConnection();){
                this.database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            }
        }
        return this.database;
    }

    @Override
    public <K> List<K> queryByPaging(EntDqlSql<K> sql, int begin, int end) throws DefinitionSqlException {
        if (sql.getResultSetExtractor() == null) {
            return null;
        }
        return this.getJdbcTemplate().query(this.formatPagingSql(sql.getSql(), begin, end), ps -> sql.getPreStatementSetter().setValues(new EntPreparedStatementWrapper(ps)), (rse, num) -> sql.getResultSetExtractor().extractData(new EntResultSetWrapper(rse)));
    }

    @Override
    public <K> K query(EntDqlSql<K> sql) throws DefinitionSqlException {
        if (sql.getResultSetExtractor() == null) {
            return null;
        }
        return (K)this.getJdbcTemplate().query(sql.getSql(), ps -> sql.getPreStatementSetter().setValues(new EntPreparedStatementWrapper(ps)), rse -> {
            if (!rse.next()) {
                return null;
            }
            return sql.getResultSetExtractor().extractData(new EntResultSetWrapper(rse));
        });
    }

    @Override
    public int execute(EntDmlSql sql) throws DefinitionSqlException {
        try {
            int rs = this.getJdbcTemplate().update(sql.getSql(), ps -> sql.getPreStatementSetter().setValues(new EntPreparedStatementWrapper(ps)));
            return rs;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DefinitionSqlException(e);
        }
    }

    @Override
    public int[] executeBatch(final EntDmlBatchSql sql) throws DefinitionSqlException {
        if (sql.getBatchPreStatementSetter() == null) {
            return new int[0];
        }
        final int batchSize = sql.getBatchPreStatementSetter().getBatchSize();
        if (batchSize < 1) {
            return new int[0];
        }
        try {
            BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter(){

                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    sql.getBatchPreStatementSetter().setValues(new EntPreparedStatementWrapper(ps), i);
                }

                public int getBatchSize() {
                    return batchSize;
                }
            };
            int[] rs = this.getJdbcTemplate().batchUpdate(sql.getSql(), setter);
            return rs;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DefinitionSqlException(e);
        }
    }

    @Override
    public void executeCustomSql(String sql) throws DefinitionSqlException {
        try {
            this.getJdbcTemplate().execute(sql);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new DefinitionSqlException(e);
        }
    }

    private String formatPagingSql(String sql, int begin, int end) {
        try {
            if (begin < 0) {
                return sql;
            }
            IPagingSQLBuilder pageBuilder = this.getDatabase().createPagingSQLBuilder();
            pageBuilder.setRawSQL(sql);
            return pageBuilder.buildSQL(begin, end);
        }
        catch (Exception e) {
            Object[] i18Args = new String[]{sql};
            throw new DefinitionSqlException(GcI18nUtil.getMessage((String)"ent.definetion.template.sql.paging.error", (Object[])i18Args), e);
        }
    }

    @Override
    public <T> List<T> queryFirstColumn(Class<T> clz, String sql, int firstResult, int maxResults, Object ... params) {
        ArrayList<Object> ps = new ArrayList<Object>();
        if (params != null) {
            Collections.addAll(ps, params);
        }
        return this.queryByPaging(EntSqlTool.newDqlInstance(sql, ps, rs -> rs.getObject(1, clz)), firstResult, maxResults);
    }
}

