/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.system.check.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Service;

@Service
public class TableParamFixService {
    private static final String FIELD_SQL1 = "UPDATE %s T1 SET T1.DF_AGGRTYPE = 1 WHERE T1.DF_KIND = 4 AND (T1.DF_DATATYPE = 5 OR T1.DF_DATATYPE = 10) AND T1.DF_DT_KEY IN (SELECT T2.DT_KEY FROM %s T2 INNER JOIN %s T3 ON T2.DT_DS_KEY = T3.DS_KEY AND T3.DS_KEY = ? WHERE T2.DT_GATHER_TYPE = 8 AND T2.DT_TYPE = 4)";
    private static final String UPDATE_NVWA_CM_SQL = "UPDATE %S C SET C.FIELD_AGGRTYPE = 1 WHERE C.FIELD_ID IN (SELECT DS_CM_KEY FROM NR_DATASCHEME_DEPLOY_INFO WHERE DS_DF_KEY IN (SELECT DF_KEY FROM %S WHERE DF_DS_KEY = ? AND DF_DT_KEY IN (SELECT DT_KEY FROM %S WHERE DT_TYPE = 4 AND DT_GATHER_TYPE = 8 AND DT_DS_KEY = ?) AND DF_KIND = 4 AND (DF_DATATYPE = 5 OR DF_DATATYPE = 10)))";
    private static final String NR_DATASCHEME_TABLE = "NR_DATASCHEME_TABLE";
    private static final String NR_DATASCHEME_TABLE_DES = "NR_DATASCHEME_TABLE_DES";
    private static final String NR_DATASCHEME_FIELD_DES = "NR_DATASCHEME_FIELD_DES";
    private static final String NR_DATASCHEME_FIELD = "NR_DATASCHEME_FIELD";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String FIELD_SQL2 = "SELECT T1.DF_KEY, T4.DT_KEY, T4.DT_BIZKEYS FROM %s T1 INNER JOIN (SELECT T2.DT_KEY, T2.DT_BIZKEYS FROM %s T2 WHERE T2.DT_DS_KEY = ? AND T2.DT_GATHER_TYPE = 8) T4 ON T1.DF_DT_KEY = T4.DT_KEY WHERE T1.DF_KIND = 4 AND T1.DF_DATATYPE != 12 AND T1.DF_DATATYPE != 5006 AND T1.DF_DATATYPE != 5 AND T1.DF_DATATYPE != 10";
    private static final String TABLE_SQL = "UPDATE %s T1 SET T1.DT_GATHERFIELDKEYS = ? WHERE T1.DT_GATHER_TYPE = 8 AND T1.DT_KEY = ?";

    public void fixField(String key) {
        this.updateNrField(key, NR_DATASCHEME_FIELD_DES, NR_DATASCHEME_TABLE_DES, "NR_DATASCHEME_SCHEME_DES");
        this.updateNrField(key, NR_DATASCHEME_FIELD, NR_DATASCHEME_TABLE, "NR_DATASCHEME_SCHEME");
        this.updateNvwaColumnModel(key, "NVWA_COLUMNMODEL", NR_DATASCHEME_FIELD, NR_DATASCHEME_TABLE);
        this.updateNvwaColumnModel(key, "NVWA_COLUMNMODEL_DES", NR_DATASCHEME_FIELD_DES, NR_DATASCHEME_TABLE_DES);
    }

    private void updateNrField(final String key, Object ... args) {
        this.jdbcTemplate.execute(String.format(FIELD_SQL1, args), (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, key);
            }
        });
    }

    private void updateNvwaColumnModel(final String key, Object ... args) {
        this.jdbcTemplate.execute(String.format(UPDATE_NVWA_CM_SQL, args), (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                ps.setString(1, key);
                ps.setString(2, key);
            }
        });
    }

    public void fixTable(String key) {
        String sql1 = String.format(FIELD_SQL2, NR_DATASCHEME_FIELD_DES, NR_DATASCHEME_TABLE_DES);
        String sql2 = String.format(FIELD_SQL2, NR_DATASCHEME_FIELD, NR_DATASCHEME_TABLE);
        String sql3 = String.format(TABLE_SQL, NR_DATASCHEME_TABLE_DES);
        String sql4 = String.format(TABLE_SQL, NR_DATASCHEME_TABLE);
        this.doFixTable(sql1, sql3, key);
        this.doFixTable(sql2, sql4, key);
    }

    private void doFixTable(String sql1, String sql3, String key) {
        Object[] arg = new Object[]{key};
        List fieldQuery = (List)this.jdbcTemplate.query(sql1, arg, rs -> {
            ArrayList<QueryResult> list = new ArrayList<QueryResult>();
            while (rs.next()) {
                QueryResult result = new QueryResult();
                result.setFieldKey(rs.getString(1));
                result.setTableKey(rs.getString(2));
                result.setBizKeys(rs.getString(3));
                list.add(result);
            }
            return list;
        });
        HashMap<String, List> modify = new HashMap<String, List>();
        for (QueryResult queryResult : fieldQuery) {
            if (queryResult.getBizKeys().contains(queryResult.getFieldKey())) continue;
            List fields = modify.computeIfAbsent(queryResult.getTableKey(), k -> new ArrayList());
            fields.add(queryResult.getFieldKey());
        }
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        Set tableKeys = modify.keySet();
        for (String tableKey : tableKeys) {
            List fields = (List)modify.get(tableKey);
            String fieldsValue = String.join((CharSequence)";", fields);
            Object[] newArg = new Object[]{fieldsValue, tableKey};
            args.add(newArg);
        }
        this.jdbcTemplate.batchUpdate(sql3, args);
    }

    class QueryResult {
        private String fieldKey;
        private String tableKey;
        private String bizKeys;

        QueryResult() {
        }

        public String getFieldKey() {
            return this.fieldKey;
        }

        public void setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
        }

        public String getTableKey() {
            return this.tableKey;
        }

        public void setTableKey(String tableKey) {
            this.tableKey = tableKey;
        }

        public String getBizKeys() {
            return this.bizKeys;
        }

        public void setBizKeys(String bizKeys) {
            this.bizKeys = bizKeys;
        }
    }
}

