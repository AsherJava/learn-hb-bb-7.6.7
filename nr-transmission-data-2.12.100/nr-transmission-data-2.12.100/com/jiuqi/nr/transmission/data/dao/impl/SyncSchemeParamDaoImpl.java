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

import com.jiuqi.nr.transmission.data.dao.ISyncSchemeParamDao;
import com.jiuqi.nr.transmission.data.dao.mapper.SyncSchemeParamMapper;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeParamDO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class SyncSchemeParamDaoImpl
implements ISyncSchemeParamDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int[] batchAdd(List<SyncSchemeParamDO> paramDOs) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", "NR_TRANS_PARAM", "TP_KEY", "TP_SCHEME_KEY", "TP_TASK", "TP_PERIOD", "TP_PERIOD_VALUE", "TP_ENTITY_TYPE", "TP_ENTITY", "TP_FORM_TYPE", "TP_FORM", "TP_IS_UPLOAD", "TP_FORCE_UPLOAD", "TP_DESC", "TP_DIM_KEYS", "TP_DIM_VALUES", "TP_ADJUST_PERIOD", "TP_MAP_KEY", "TP_DATA_MESSAGE");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (SyncSchemeParamDO paramDO : paramDOs) {
            Object[] arg = new Object[]{paramDO.getKey(), paramDO.getSchemeKey(), paramDO.getTask(), paramDO.getPeriod(), paramDO.getPeriodValue(), paramDO.getEntityType(), paramDO.getEntity(), paramDO.getFormType(), paramDO.getForm(), paramDO.getIsUpload(), paramDO.getAllowForceUpload(), paramDO.getDescription(), paramDO.getDimKeys(), paramDO.getDimValues(), paramDO.getAdjustPeriod(), paramDO.getMappingSchemeKey(), paramDO.getDataMessage()};
            args.add(arg);
        }
        int[] result = this.jdbcTemplate.batchUpdate(addSql, args);
        return result;
    }

    @Override
    public int add(SyncSchemeParamDO paramDOs) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", "NR_TRANS_PARAM", "TP_KEY", "TP_SCHEME_KEY", "TP_TASK", "TP_PERIOD", "TP_PERIOD_VALUE", "TP_ENTITY_TYPE", "TP_ENTITY", "TP_FORM_TYPE", "TP_FORM", "TP_IS_UPLOAD", "TP_FORCE_UPLOAD", "TP_DESC", "TP_DIM_KEYS", "TP_DIM_VALUES", "TP_ADJUST_PERIOD", "TP_MAP_KEY", "TP_DATA_MESSAGE");
        Object[] arg = new Object[]{paramDOs.getKey(), paramDOs.getSchemeKey(), paramDOs.getTask(), paramDOs.getPeriod(), paramDOs.getPeriodValue(), paramDOs.getEntityType(), paramDOs.getEntity(), paramDOs.getFormType(), paramDOs.getForm(), paramDOs.getIsUpload(), paramDOs.getAllowForceUpload(), paramDOs.getDescription(), paramDOs.getDimKeys(), paramDOs.getDimValues(), paramDOs.getAdjustPeriod(), paramDOs.getMappingSchemeKey(), paramDOs.getDataMessage()};
        int result = this.jdbcTemplate.update(addSql, arg);
        return result;
    }

    @Override
    public int delete(String schemeKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s", "NR_TRANS_PARAM", "TP_SCHEME_KEY", "TP_SCHEME_KEY");
        int update = this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)new MapSqlParameterSource("TP_SCHEME_KEY", (Object)schemeKey));
        return update;
    }

    @Override
    public int update(SyncSchemeParamDO paramDO) {
        String updateSql = String.format("UPDATE %s SET %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s, %s = :%s WHERE %s = :%s ", "NR_TRANS_PARAM", "TP_TASK", "TP_TASK", "TP_PERIOD", "TP_PERIOD", "TP_PERIOD_VALUE", "TP_PERIOD_VALUE", "TP_ENTITY_TYPE", "TP_ENTITY_TYPE", "TP_ENTITY", "TP_ENTITY", "TP_FORM_TYPE", "TP_FORM_TYPE", "TP_FORM", "TP_FORM", "TP_IS_UPLOAD", "TP_IS_UPLOAD", "TP_FORCE_UPLOAD", "TP_FORCE_UPLOAD", "TP_DESC", "TP_DESC", "TP_DIM_KEYS", "TP_DIM_KEYS", "TP_DIM_VALUES", "TP_DIM_VALUES", "TP_ADJUST_PERIOD", "TP_ADJUST_PERIOD", "TP_MAP_KEY", "TP_MAP_KEY", "TP_DATA_MESSAGE", "TP_DATA_MESSAGE", "TP_KEY", "TP_KEY");
        int update = this.namedParameterJdbcTemplate.update(updateSql, paramDO.toMap());
        return update;
    }

    @Override
    public int[] batchUpdate(List<SyncSchemeParamDO> paramDOs) {
        String updateSql = String.format("UPDATE %s SET %s = ?,%s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ? ", "NR_TRANS_PARAM", "TP_TASK", "TP_PERIOD", "TP_PERIOD_VALUE", "TP_ENTITY_TYPE", "TP_ENTITY", "TP_FORM_TYPE", "TP_FORM", "TP_IS_UPLOAD", "TP_FORCE_UPLOAD", "TP_DESC", "TP_KEY");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (SyncSchemeParamDO paramDO : paramDOs) {
            Object[] arg = new Object[]{paramDO.getTask(), paramDO.getPeriod(), paramDO.getPeriodValue(), paramDO.getEntityType(), paramDO.getEntity(), paramDO.getFormType(), paramDO.getForm(), paramDO.getIsUpload(), paramDO.getAllowForceUpload(), paramDO.getDescription(), paramDO.getKey()};
            args.add(arg);
        }
        int[] result = this.jdbcTemplate.batchUpdate(updateSql, args);
        return result;
    }

    @Override
    public SyncSchemeParamDO get(String schemeKey) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_PARAM", "TP_SCHEME_KEY", "TP_SCHEME_KEY");
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)new MapSqlParameterSource("TP_SCHEME_KEY", (Object)schemeKey), (RowMapper)new SyncSchemeParamMapper());
        if (query.size() > 0) {
            return (SyncSchemeParamDO)query.get(0);
        }
        return null;
    }

    @Override
    public List<SyncSchemeParamDO> list() {
        String querySql = String.format("SELECT * FROM %s", "NR_TRANS_PARAM");
        return this.namedParameterJdbcTemplate.query(querySql, (RowMapper)new SyncSchemeParamMapper());
    }

    @Override
    public List<SyncSchemeParamDO> listByGroup(List<String> schemeKeys) {
        String querySql = String.format("SELECT * FROM %s WHERE  %s in (:%s)", "NR_TRANS_PARAM", "TP_SCHEME_KEY", "TP_SCHEME_KEY");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("TP_SCHEME_KEY", schemeKeys);
        List query = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate).query(querySql, (SqlParameterSource)source, (RowMapper)new SyncSchemeParamMapper());
        return query;
    }

    private List<SyncSchemeParamDO> list(String key, String field) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_TRANS_SCHEME", field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(field, (Object)key);
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SyncSchemeParamMapper());
    }
}

