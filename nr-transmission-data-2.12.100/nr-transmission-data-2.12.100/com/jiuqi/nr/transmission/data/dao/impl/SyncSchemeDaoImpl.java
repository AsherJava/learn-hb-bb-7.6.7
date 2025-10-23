/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.transmission.data.dao.impl;

import com.jiuqi.nr.transmission.data.dao.ISyncSchemeDao;
import com.jiuqi.nr.transmission.data.dao.mapper.SyncSchemeMapper;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeDO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SyncSchemeDaoImpl
implements ISyncSchemeDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SyncSchemeDO syncSchemeDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s,  :%s, :%s, :%s, :%s, :%s)", "NR_TRANS_SCHEME", "TS_KEY", "TS_CODE", "TS_TITLE", "TS_GROUP", "TS_DESC", "TS_UPDATE_TIME", "TS_ORDER", "TS_KEY", "TS_CODE", "TS_TITLE", "TS_GROUP", "TS_DESC", "TS_UPDATE_TIME", "TS_ORDER");
        return this.namedParameterJdbcTemplate.update(addSql, syncSchemeDO.toMap());
    }

    @Override
    public int delete(String schemeKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s", "NR_TRANS_SCHEME", "TS_KEY", "TS_KEY");
        return this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)new MapSqlParameterSource("TS_KEY", (Object)schemeKey));
    }

    @Override
    public int update(SyncSchemeDO syncSchemeDO) {
        String updateSql = String.format("UPDATE %s SET %s = :%s, %s = :%s,%s = :%s, %s = :%s, %s = :%s  WHERE %s = :%s ", "NR_TRANS_SCHEME", "TS_CODE", "TS_CODE", "TS_TITLE", "TS_TITLE", "TS_GROUP", "TS_GROUP", "TS_DESC", "TS_DESC", "TS_UPDATE_TIME", "TS_UPDATE_TIME", "TS_KEY", "TS_KEY");
        Map<String, Object> stringObjectMap = syncSchemeDO.toMap();
        return this.namedParameterJdbcTemplate.update(updateSql, syncSchemeDO.toMap());
    }

    @Override
    public int updates(String oldStr, String newStr, String field) {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", "NR_TRANS_SCHEME", field, field);
        Object[] arg = new Object[]{newStr, oldStr};
        return this.jdbcTemplate.update(updateSql, arg);
    }

    @Override
    public SyncSchemeDO get(String schemeKey) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_SCHEME", "TS_KEY", "TS_KEY");
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)new MapSqlParameterSource("TS_KEY", (Object)schemeKey), (RowMapper)new SyncSchemeMapper());
        if (query.size() > 0) {
            return (SyncSchemeDO)query.get(0);
        }
        return null;
    }

    @Override
    public List<SyncSchemeDO> list() {
        String querySql = String.format("SELECT * FROM %s", "NR_TRANS_SCHEME");
        return this.namedParameterJdbcTemplate.query(querySql, (RowMapper)new SyncSchemeMapper());
    }

    @Override
    public List<SyncSchemeDO> listByGroup(String groupKey) {
        return this.list(groupKey, "TS_GROUP");
    }

    private List<SyncSchemeDO> list(String key, String field) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_SCHEME", field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(field, (Object)key);
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SyncSchemeMapper());
    }
}

