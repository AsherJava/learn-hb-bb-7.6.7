/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.multcheck2.dao;

import com.jiuqi.nr.multcheck2.bean.MCHistoryScheme;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MCHistorySchemeDao {
    private static final String TABLE_NAME = "NR_MULTCHECK_HISTORY_SCHEME";
    private static final String KEY = "MHS_KEY";
    private static final String TITLE = "MHS_TITLE";
    private static final String TASK = "MHS_TASK";
    private static final String PERIOD = "MHS_PERIOD";
    private static final String SOURCE = "MS_KEY";
    private static final String CONFIG = "MHS_CONFIG";
    private static final String ORG = "MHS_ORG";
    private static final String ITEM = "MHS_ITEM";
    private static final String USER = "MHS_USER";
    private static final String UPDATE_TIME = "MHS_UPDATE_TIME";
    private static final String MULTCHECK_HISTORY_SCHEME;
    private static final Function<ResultSet, MCHistoryScheme> ENTITY_READER_MULTCHECK_HISTORY_SCHEME;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(MCHistoryScheme scheme) {
        String SQL_INSERT = String.format("INSERT INTO %s (%s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", TABLE_NAME, MULTCHECK_HISTORY_SCHEME + "," + CONFIG);
        this.jdbcTemplate.update(SQL_INSERT, new Object[]{scheme.getKey(), scheme.getTitle(), scheme.getTask(), scheme.getPeriod(), scheme.getSource(), scheme.getOrg(), scheme.getItem(), scheme.getUser(), scheme.getUpdateTime(), scheme.getConfig()});
    }

    public void deleteByKey(String key) {
        String SQL_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, KEY);
        this.jdbcTemplate.update(SQL_DELETE, new Object[]{key});
    }

    public MCHistoryScheme getByKey(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", MULTCHECK_HISTORY_SCHEME, TABLE_NAME, KEY);
        return (MCHistoryScheme)this.jdbcTemplate.query(SQL_QUERY, rs -> {
            if (rs.next()) {
                return ENTITY_READER_MULTCHECK_HISTORY_SCHEME.apply(rs);
            }
            return null;
        }, new Object[]{key});
    }

    public void batchDeleteHistory(List<String> keys) {
        String SQL_BATCH_DELETE = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (String key : keys) {
            Object[] param = new Object[]{key};
            args.add(param);
        }
        this.jdbcTemplate.batchUpdate(SQL_BATCH_DELETE, args);
    }

    public String getConfigByKey(String key) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ?", CONFIG, TABLE_NAME, KEY);
        return (String)this.jdbcTemplate.queryForObject(SQL_QUERY, String.class, new Object[]{key});
    }

    public List<MCHistoryScheme> getByUserSource(String user, String source) {
        String SQL_QUERY = String.format("SELECT %s FROM %s WHERE %s = ? AND %s = ? ORDER BY %s DESC,%s DESC", MULTCHECK_HISTORY_SCHEME, TABLE_NAME, USER, SOURCE, PERIOD, UPDATE_TIME);
        return this.jdbcTemplate.query(SQL_QUERY, (rs, row) -> ENTITY_READER_MULTCHECK_HISTORY_SCHEME.apply(rs), new Object[]{user, source});
    }

    static {
        StringBuilder builder = new StringBuilder();
        MULTCHECK_HISTORY_SCHEME = builder.append(KEY).append(",").append(TITLE).append(",").append(TASK).append(",").append(PERIOD).append(",").append(SOURCE).append(",").append(ORG).append(",").append(ITEM).append(",").append(USER).append(",").append(UPDATE_TIME).toString();
        ENTITY_READER_MULTCHECK_HISTORY_SCHEME = rs -> {
            MCHistoryScheme mcHistoryScheme = new MCHistoryScheme();
            int index = 1;
            try {
                mcHistoryScheme.setKey(rs.getString(index++));
                mcHistoryScheme.setTitle(rs.getString(index++));
                mcHistoryScheme.setTask(rs.getString(index++));
                mcHistoryScheme.setPeriod(rs.getString(index++));
                mcHistoryScheme.setSource(rs.getString(index++));
                mcHistoryScheme.setOrg(rs.getInt(index++));
                mcHistoryScheme.setItem(rs.getInt(index++));
                mcHistoryScheme.setUser(rs.getString(index++));
                mcHistoryScheme.setUpdateTime(rs.getTimestamp(index));
            }
            catch (SQLException e) {
                throw new RuntimeException("read mcHistoryScheme error.", e);
            }
            return mcHistoryScheme;
        };
    }
}

