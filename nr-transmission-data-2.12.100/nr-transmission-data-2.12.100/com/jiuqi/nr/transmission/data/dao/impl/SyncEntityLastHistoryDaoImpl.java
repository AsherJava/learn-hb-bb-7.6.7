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

import com.jiuqi.nr.transmission.data.dao.ISyncEntityLastHistoryDao;
import com.jiuqi.nr.transmission.data.dao.mapper.SyncEntityLastHistoryMapper;
import com.jiuqi.nr.transmission.data.domain.SyncEntityLastHistoryDO;
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
public class SyncEntityLastHistoryDaoImpl
implements ISyncEntityLastHistoryDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s)", "NR_TRANS_ENTITY_HISTORY", "TEH_KEY", "TEH_TASK", "TEH_PERIOD", "TEH_FORM", "TEH_ENTITY", "TEH_USERID", "TEH_TIME", "TEH_KEY", "TEH_TASK", "TEH_PERIOD", "TEH_FORM", "TEH_ENTITY", "TEH_USERID", "TEH_TIME");
        int update = this.namedParameterJdbcTemplate.update(addSql, syncEntityLastHistoryDO.toMap());
        return update;
    }

    @Override
    public int[] betchAdd(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s)", "NR_TRANS_ENTITY_HISTORY", "TEH_KEY", "TEH_TASK", "TEH_PERIOD", "TEH_FORM", "TEH_ENTITY", "TEH_USERID", "TEH_TIME", "TEH_KEY", "TEH_TASK", "TEH_PERIOD", "TEH_FORM", "TEH_ENTITY", "TEH_USERID", "TEH_TIME");
        Map[] maps = new Map[syncEntityLastHistoryDOS.size()];
        for (int i = 0; i < syncEntityLastHistoryDOS.size(); ++i) {
            SyncEntityLastHistoryDO syncEntityLastHistoryDO = syncEntityLastHistoryDOS.get(i);
            maps[i] = syncEntityLastHistoryDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(addSql, maps);
        return ints;
    }

    @Override
    public int delete(String key) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s", "NR_TRANS_ENTITY_HISTORY", "TEH_KEY", "TEH_KEY");
        int update = this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)new MapSqlParameterSource("TH_KEY", (Object)key));
        return update;
    }

    @Override
    public int[] betchdeletes(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s", "NR_TRANS_ENTITY_HISTORY", "TEH_KEY", "TEH_KEY");
        Map[] maps = new Map[syncEntityLastHistoryDOS.size()];
        for (int i = 0; i < syncEntityLastHistoryDOS.size(); ++i) {
            SyncEntityLastHistoryDO syncEntityLastHistoryDO = syncEntityLastHistoryDOS.get(i);
            maps[i] = syncEntityLastHistoryDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(deleteSql, maps);
        return ints;
    }

    @Override
    public SyncEntityLastHistoryDO get(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND %s = :%s AND %s = :%s AND %s = :%s", "NR_TRANS_ENTITY_HISTORY", "TEH_TASK", "TEH_TASK", "TEH_PERIOD", "TEH_PERIOD", "TEH_ENTITY", "TEH_ENTITY", "TEH_FORM", "TEH_FORM");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("TEH_TASK", (Object)syncEntityLastHistoryDO.getTaskKey());
        source.addValue("TEH_PERIOD", (Object)syncEntityLastHistoryDO.getPeriod());
        source.addValue("TEH_ENTITY", (Object)syncEntityLastHistoryDO.getEntity());
        source.addValue("TEH_FORM", (Object)syncEntityLastHistoryDO.getFormKey());
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SyncEntityLastHistoryMapper());
        if (query.size() > 0) {
            return (SyncEntityLastHistoryDO)query.get(0);
        }
        return null;
    }

    @Override
    public List<SyncEntityLastHistoryDO> list(SyncEntityLastHistoryDO syncEntityLastHistoryDO) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND %s = :%s AND %s = :%s", "NR_TRANS_ENTITY_HISTORY", "TEH_TASK", "TEH_TASK", "TEH_PERIOD", "TEH_PERIOD", "TEH_ENTITY", "TEH_ENTITY");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("TEH_TASK", (Object)syncEntityLastHistoryDO.getTaskKey());
        source.addValue("TEH_PERIOD", (Object)syncEntityLastHistoryDO.getPeriod());
        source.addValue("TEH_ENTITY", (Object)syncEntityLastHistoryDO.getEntity());
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SyncEntityLastHistoryMapper());
        return query;
    }

    @Override
    public List<SyncEntityLastHistoryDO> lists(SyncEntityLastHistoryDO syncEntityLastHistoryDO, List<String> entitys) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND %s = :%s AND %s in (:%s)", "NR_TRANS_ENTITY_HISTORY", "TEH_TASK", "TEH_TASK", "TEH_PERIOD", "TEH_PERIOD", "TEH_ENTITY", "TEH_ENTITY");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("TEH_TASK", (Object)syncEntityLastHistoryDO.getTaskKey());
        source.addValue("TEH_PERIOD", (Object)syncEntityLastHistoryDO.getPeriod());
        source.addValue("TEH_ENTITY", entitys);
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SyncEntityLastHistoryMapper());
        return query;
    }

    @Override
    public int[] batchUpdateWithForm(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        String updateSql = String.format("UPDATE %s SET %s = :%s WHERE %s = :%s AND %s = :%s AND %s = :%s AND %s = :%s", "NR_TRANS_ENTITY_HISTORY", "TEH_USERID", "TEH_USERID", "TEH_TASK", "TEH_TASK", "TEH_PERIOD", "TEH_PERIOD", "TEH_ENTITY", "TEH_ENTITY", "TEH_FORM", "TEH_FORM");
        Map[] maps = new Map[syncEntityLastHistoryDOS.size()];
        for (int i = 0; i < syncEntityLastHistoryDOS.size(); ++i) {
            SyncEntityLastHistoryDO syncEntityLastHistoryDO = syncEntityLastHistoryDOS.get(i);
            maps[i] = syncEntityLastHistoryDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(updateSql, maps);
        return ints;
    }

    @Override
    public int[] batchUpdateWithEntity(List<SyncEntityLastHistoryDO> syncEntityLastHistoryDOS) {
        String updateSql = String.format("UPDATE %s SET %s = :%s WHERE %s = :%s AND %s = :%s AND %s = :%s", "NR_TRANS_ENTITY_HISTORY", "TEH_USERID", "TEH_USERID", "TEH_TASK", "TEH_TASK", "TEH_PERIOD", "TEH_PERIOD", "TEH_ENTITY", "TEH_ENTITY");
        Map[] maps = new Map[syncEntityLastHistoryDOS.size()];
        for (int i = 0; i < syncEntityLastHistoryDOS.size(); ++i) {
            SyncEntityLastHistoryDO syncEntityLastHistoryDO = syncEntityLastHistoryDOS.get(i);
            maps[i] = syncEntityLastHistoryDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(updateSql, maps);
        return ints;
    }
}

