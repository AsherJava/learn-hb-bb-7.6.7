/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.transmission.data.dao.impl;

import com.jiuqi.nr.transmission.data.dao.ISyncHistoryDao;
import com.jiuqi.nr.transmission.data.dao.mapper.SyncHistoryMapper;
import com.jiuqi.nr.transmission.data.domain.SyncHistoryDO;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SyncHistoryDaoImpl
implements ISyncHistoryDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SyncHistoryDO historyDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s, :%s)", "NR_TRANS_HISTORY", "TH_KEY", "TH_SCHEME_KEY", "TH_STATUS", "TH_DETAIL", "TH_PARAM", "TH_START_TIME", "TH_END_TIME", "TH_FILE_KEY", "TH_USER_ID", "TH_FINISH_ENTITY", "TH_TYPE", "TH_INSTANCE_ID", "TH_RESULT", "TH_KEY", "TH_SCHEME_KEY", "TH_STATUS", "TH_DETAIL", "TH_PARAM", "TH_START_TIME", "TH_END_TIME", "TH_FILE_KEY", "TH_USER_ID", "TH_FINISH_ENTITY", "TH_TYPE", "TH_INSTANCE_ID", "TH_RESULT");
        Map<String, Object> stringObjectMap = historyDO.toMap();
        int update = this.namedParameterJdbcTemplate.update(addSql, stringObjectMap);
        return update;
    }

    @Override
    public int delete(String historyKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s", "NR_TRANS_HISTORY", "TH_KEY", "TH_KEY");
        int update = this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)new MapSqlParameterSource("TH_KEY", (Object)historyKey));
        return update;
    }

    @Override
    public int deletes(String historyKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s", "NR_TRANS_HISTORY", "TH_SCHEME_KEY", "TH_SCHEME_KEY");
        int update = this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)new MapSqlParameterSource("TH_SCHEME_KEY", (Object)historyKey));
        return update;
    }

    @Override
    public int update(SyncHistoryDO historyDO) {
        String updateSql = String.format("UPDATE %s SET %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s WHERE %s = :%s ", "NR_TRANS_HISTORY", "TH_STATUS", "TH_STATUS", "TH_DETAIL", "TH_DETAIL", "TH_END_TIME", "TH_END_TIME", "TH_FINISH_ENTITY", "TH_FINISH_ENTITY", "TH_RESULT", "TH_RESULT", "TH_KEY", "TH_KEY");
        int update = this.namedParameterJdbcTemplate.update(updateSql, historyDO.toMap());
        return update;
    }

    @Override
    public int updateSyncSchemeParam(SyncHistoryDO historyDO) {
        String updateSql = String.format("UPDATE %s SET %s = :%s WHERE %s = :%s ", "NR_TRANS_HISTORY", "TH_PARAM", "TH_PARAM", "TH_KEY", "TH_KEY");
        int update = this.namedParameterJdbcTemplate.update(updateSql, historyDO.toMap());
        return update;
    }

    @Override
    public int updateResult(SyncHistoryDO historyDO) {
        String updateSql = String.format("UPDATE %s SET %s = :%s WHERE %s = :%s ", "NR_TRANS_HISTORY", "TH_RESULT", "TH_RESULT", "TH_KEY", "TH_KEY");
        int update = this.namedParameterJdbcTemplate.update(updateSql, historyDO.toMap());
        return update;
    }

    @Override
    public int updateField(String executeKey, String field, String value) {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ? ", "NR_TRANS_HISTORY", field, "TH_KEY");
        Object[] arg = new Object[]{value, executeKey};
        int update = this.jdbcTemplate.update(updateSql, arg);
        return update;
    }

    @Override
    public SyncHistoryDO get(String historyKey) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_HISTORY", "TH_KEY", "TH_KEY");
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)new MapSqlParameterSource("TH_KEY", (Object)historyKey), (RowMapper)new SyncHistoryMapper());
        if (query.size() > 0) {
            return (SyncHistoryDO)query.get(0);
        }
        return null;
    }

    @Override
    public List<SyncHistoryDO> getUnComplete() {
        String querySql = String.format("SELECT * FROM %s WHERE %s in (0,1,2)", "NR_TRANS_HISTORY", "TH_STATUS", "TH_STATUS");
        List query = this.namedParameterJdbcTemplate.query(querySql, (RowMapper)new SyncHistoryMapper());
        return query;
    }

    @Override
    public List<SyncHistoryDO> list(String schemeKey) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_HISTORY", "TH_SCHEME_KEY", "TH_SCHEME_KEY");
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)new MapSqlParameterSource("TH_SCHEME_KEY", (Object)schemeKey), (RowMapper)new SyncHistoryMapper());
        return query;
    }

    @Override
    public List<SyncHistoryDO> listImport() {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_HISTORY", "TH_TYPE", "TH_TYPE");
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)new MapSqlParameterSource("TH_TYPE", (Object)1), (RowMapper)new SyncHistoryMapper());
        return query;
    }

    @Override
    public List<SyncHistoryDO> listByGroup(List<String> schemeKeys) {
        String querySql = String.format("SELECT * FROM %s WHERE  %s in (:%s)", "NR_TRANS_HISTORY", "TH_SCHEME_KEY", "TH_SCHEME_KEY");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("TH_SCHEME_KEY", schemeKeys);
        List query = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate).query(querySql, (SqlParameterSource)source, (RowMapper)new SyncHistoryMapper());
        return query;
    }
}

