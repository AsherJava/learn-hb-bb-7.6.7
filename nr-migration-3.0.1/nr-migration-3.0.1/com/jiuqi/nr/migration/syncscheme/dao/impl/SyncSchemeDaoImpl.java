/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.dao.DataAccessException
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.PreparedStatementCallback
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback
 *  org.springframework.jdbc.support.lob.DefaultLobHandler
 *  org.springframework.jdbc.support.lob.LobCreator
 *  org.springframework.jdbc.support.lob.LobHandler
 */
package com.jiuqi.nr.migration.syncscheme.dao.impl;

import com.jiuqi.nr.migration.syncscheme.bean.SyncScheme;
import com.jiuqi.nr.migration.syncscheme.dao.ISyncSchemeDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class SyncSchemeDaoImpl
implements ISyncSchemeDao {
    private static final String INSERT_SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s,%s,%s) VALUES (?,?,?,?,?,?,?)", "NR_SYNC_SCHEME", "SS_KEY", "SS_CODE", "SS_TITLE", "SS_GROUP", "SS_DATA", "SS_UPDATE_TIME", "SS_ORDER");
    private static final String UPDATEBASE_SQL = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=? WHERE %s=?", "NR_SYNC_SCHEME", "SS_CODE", "SS_TITLE", "SS_GROUP", "SS_UPDATE_TIME", "SS_ORDER", "SS_KEY");
    private static final String UPDATEDATA_SQL = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=?,%s=?,%s=? WHERE %s=?", "NR_SYNC_SCHEME", "SS_CODE", "SS_TITLE", "SS_GROUP", "SS_DATA", "SS_UPDATE_TIME", "SS_ORDER", "SS_KEY");
    private static final String DELETE_SQL = String.format("DELETE FROM %s WHERE %s in (?)", "NR_SYNC_SCHEME", "SS_KEY");
    private static final String GET_SQL = String.format("SELECT * FROM %s WHERE %s=?", "NR_SYNC_SCHEME", "SS_KEY");
    private static final String GET_BY_CODE_SQL = String.format("SELECT * FROM %s WHERE %s=?", "NR_SYNC_SCHEME", "SS_CODE");
    private static final String GET_BY_TITLE_SQL = String.format("SELECT * FROM %s WHERE %s=?", "NR_SYNC_SCHEME", "SS_TITLE");
    private static final String GET_BY_PARENT_SQL = String.format("SELECT * FROM %s WHERE %s=? ORDER BY %s DESC", "NR_SYNC_SCHEME", "SS_GROUP", "SS_ORDER");
    private static final String GET_ALL_SQL = String.format("SELECT * FROM %s ORDER BY %s DESC", "NR_SYNC_SCHEME", "SS_ORDER");
    private static final RowMapper<SyncScheme> rowMapper = new SyncSchemeRowMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(SyncScheme scheme) {
        this.jdbcTemplate.update(INSERT_SQL, new Object[]{scheme.getKey(), scheme.getCode(), scheme.getTitle(), scheme.getGroup(), scheme.getData(), Timestamp.from(Instant.ofEpochMilli(scheme.getUpdateTime())), scheme.getOrder()});
    }

    @Override
    public void update(SyncScheme scheme) {
        this.jdbcTemplate.update(UPDATEBASE_SQL, new Object[]{scheme.getCode(), scheme.getTitle(), scheme.getGroup(), Timestamp.from(Instant.ofEpochMilli(scheme.getUpdateTime())), scheme.getOrder(), scheme.getKey()});
    }

    @Override
    public void batchDelete(List<String> keys) {
        StringBuilder sqlBuf = new StringBuilder();
        sqlBuf.append("DELETE FROM ").append("NR_SYNC_SCHEME").append(" WHERE SS_KEY IN(");
        for (int i = 0; i < keys.size(); ++i) {
            sqlBuf.append("?");
            if (i != keys.size() - 1) {
                sqlBuf.append(",");
                continue;
            }
            sqlBuf.append(")");
        }
        this.jdbcTemplate.update(sqlBuf.toString(), keys.toArray());
    }

    @Override
    public SyncScheme get(String key) {
        List syncSchemes = this.jdbcTemplate.query(GET_SQL, rowMapper, new Object[]{key});
        return syncSchemes.isEmpty() ? null : (SyncScheme)syncSchemes.get(0);
    }

    @Override
    public SyncScheme getByCode(String code) {
        List syncSchemes = this.jdbcTemplate.query(GET_BY_CODE_SQL, rowMapper, new Object[]{code});
        return syncSchemes.isEmpty() ? null : (SyncScheme)syncSchemes.get(0);
    }

    @Override
    public SyncScheme getByTitle(String title) {
        List syncSchemes = this.jdbcTemplate.query(GET_BY_TITLE_SQL, rowMapper, new Object[]{title});
        return syncSchemes.isEmpty() ? null : (SyncScheme)syncSchemes.get(0);
    }

    @Override
    public List<SyncScheme> getByParent(String parent) {
        Object[] objectArray;
        String sql;
        String string = sql = StringUtils.hasLength(parent) ? GET_BY_PARENT_SQL : GET_ALL_SQL;
        if (StringUtils.hasLength(parent)) {
            Object[] objectArray2 = new Object[1];
            objectArray = objectArray2;
            objectArray2[0] = parent;
        } else {
            objectArray = new Object[]{};
        }
        Object[] params = objectArray;
        return this.jdbcTemplate.query(sql, rowMapper, params);
    }

    @Override
    public void updateData(SyncScheme scheme) {
        this.addHasExtData(scheme);
    }

    public int addHasExtData(final SyncScheme scheme) {
        Integer result = (Integer)this.jdbcTemplate.execute(UPDATEDATA_SQL, (PreparedStatementCallback)new AbstractLobCreatingPreparedStatementCallback((LobHandler)new DefaultLobHandler()){

            protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                int index = 1;
                ps.setString(index++, scheme.getCode());
                ps.setString(index++, scheme.getTitle());
                ps.setString(index++, scheme.getGroup());
                lobCreator.setClobAsString(ps, index++, scheme.getData());
                ps.setTimestamp(index++, new Timestamp(System.currentTimeMillis()));
                ps.setString(index++, scheme.getOrder());
                ps.setString(index, scheme.getKey());
            }
        });
        return result == null ? 0 : result;
    }

    static class SyncSchemeRowMapper
    implements RowMapper<SyncScheme> {
        SyncSchemeRowMapper() {
        }

        public SyncScheme mapRow(ResultSet rs, int rowNum) throws SQLException {
            SyncScheme scheme = new SyncScheme();
            scheme.setKey(rs.getString("SS_KEY"));
            scheme.setCode(rs.getString("SS_CODE"));
            scheme.setTitle(rs.getString("SS_TITLE"));
            scheme.setGroup(rs.getString("SS_GROUP"));
            scheme.setData(rs.getString("SS_DATA"));
            scheme.setUpdateTime(rs.getTimestamp("SS_UPDATE_TIME").getTime());
            scheme.setOrder(rs.getString("SS_ORDER"));
            return scheme;
        }
    }
}

