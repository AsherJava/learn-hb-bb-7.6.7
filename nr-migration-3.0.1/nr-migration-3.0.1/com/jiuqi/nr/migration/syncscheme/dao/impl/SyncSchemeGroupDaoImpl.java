/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 */
package com.jiuqi.nr.migration.syncscheme.dao.impl;

import com.jiuqi.nr.migration.syncscheme.bean.SyncSchemeGroup;
import com.jiuqi.nr.migration.syncscheme.dao.ISyncSchemeGroupDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class SyncSchemeGroupDaoImpl
implements ISyncSchemeGroupDao {
    private static final String INSERT_SQL = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)", "NR_SYNC_SCHEME_GROUP", "SSG_KEY", "SSG_TITLE", "SSG_PARENT", "SSG_UPDATE_TIME", "SSG_ORDER");
    private static final String UPDATE_SQL = String.format("UPDATE %s SET %s=?,%s=?,%s=?,%s=? WHERE %s=?", "NR_SYNC_SCHEME_GROUP", "SSG_TITLE", "SSG_PARENT", "SSG_UPDATE_TIME", "SSG_ORDER", "SSG_KEY");
    private static final String DELETE_SQL = String.format("DELETE FROM %s WHERE %s=?", "NR_SYNC_SCHEME_GROUP", "SSG_KEY");
    private static final String GET_SQL = String.format("SELECT * FROM %s WHERE %s=?", "NR_SYNC_SCHEME_GROUP", "SSG_KEY");
    private static final String GET_BY_PARENT_SQL = String.format("SELECT * FROM %s WHERE %s=? ORDER BY %s", "NR_SYNC_SCHEME_GROUP", "SSG_PARENT", "SSG_ORDER");
    private static final String GET_ROOT_SQL = String.format("SELECT * FROM %s WHERE %s IS NULL ORDER BY %s", "NR_SYNC_SCHEME_GROUP", "SSG_PARENT", "SSG_ORDER");
    private static final String GET_BY_TITLE_AND_GROUP_SQL = String.format("SELECT * FROM %s WHERE %s=? AND %s=? ORDER BY %s", "NR_SYNC_SCHEME_GROUP", "SSG_TITLE", "SSG_PARENT", "SSG_ORDER");
    private static final String GET_BY_TITLE_ROOT_SQL = String.format("SELECT * FROM %s WHERE %s=? AND %s IS NULL ORDER BY %s", "NR_SYNC_SCHEME_GROUP", "SSG_TITLE", "SSG_PARENT", "SSG_ORDER");
    private static final RowMapper<SyncSchemeGroup> rowMapper = new SyncSchemeGroupRowMapper();
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void add(SyncSchemeGroup schemeGroup) {
        this.jdbcTemplate.update(INSERT_SQL, new Object[]{schemeGroup.getKey(), schemeGroup.getTitle(), schemeGroup.getParent(), Timestamp.from(Instant.ofEpochMilli(schemeGroup.getUpdateTime())), schemeGroup.getOrder()});
    }

    @Override
    public void update(SyncSchemeGroup schemeGroup) {
        this.jdbcTemplate.update(UPDATE_SQL, new Object[]{schemeGroup.getTitle(), schemeGroup.getParent(), Timestamp.from(Instant.ofEpochMilli(schemeGroup.getUpdateTime())), schemeGroup.getOrder(), schemeGroup.getKey()});
    }

    @Override
    public void delete(String key) {
        this.jdbcTemplate.update(DELETE_SQL, new Object[]{key});
    }

    @Override
    public SyncSchemeGroup get(String key) {
        List result = this.jdbcTemplate.query(GET_SQL, rowMapper, new Object[]{key});
        return result.isEmpty() ? null : (SyncSchemeGroup)result.get(0);
    }

    @Override
    public SyncSchemeGroup getByTitleAndGroup(String title, String group) {
        Object[] objectArray;
        String sql;
        String string = sql = StringUtils.hasLength(group) ? GET_BY_TITLE_AND_GROUP_SQL : GET_BY_TITLE_ROOT_SQL;
        if (StringUtils.hasLength(group)) {
            Object[] objectArray2 = new Object[2];
            objectArray2[0] = title;
            objectArray = objectArray2;
            objectArray2[1] = group;
        } else {
            Object[] objectArray3 = new Object[1];
            objectArray = objectArray3;
            objectArray3[0] = title;
        }
        Object[] params = objectArray;
        return this.jdbcTemplate.query(sql, rowMapper, params).stream().findFirst().orElse(null);
    }

    @Override
    public List<SyncSchemeGroup> getByParent(String parent) {
        Object[] objectArray;
        String sql;
        String string = sql = StringUtils.hasLength(parent) ? GET_BY_PARENT_SQL : GET_ROOT_SQL;
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

    static class SyncSchemeGroupRowMapper
    implements RowMapper<SyncSchemeGroup> {
        SyncSchemeGroupRowMapper() {
        }

        public SyncSchemeGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            SyncSchemeGroup group = new SyncSchemeGroup();
            group.setKey(rs.getString("SSG_KEY"));
            group.setTitle(rs.getString("SSG_TITLE"));
            group.setParent(rs.getString("SSG_PARENT"));
            group.setUpdateTime(rs.getTimestamp("SSG_UPDATE_TIME").toInstant().toEpochMilli());
            group.setOrder(rs.getString("SSG_ORDER"));
            return group;
        }
    }
}

