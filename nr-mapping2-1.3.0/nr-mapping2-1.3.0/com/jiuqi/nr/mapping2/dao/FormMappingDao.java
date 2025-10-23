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
package com.jiuqi.nr.mapping2.dao;

import com.jiuqi.nr.mapping2.bean.FormMappingDO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
public class FormMappingDao {
    private static final String TABLE_NAME = "NVWA_MAPPING_NR_FORM";
    private static final String FIELD_KEY = "MF_KEY";
    private static final String FIELD_SCHEME = "MF_SCHEME";
    private static final String FIELD_SR_KEY = "MF_SR_KEY";
    private static final String FIELD_TR_CODE = "MF_TR_CODE";
    private static final String FIELD_TR_TITLE = "MF_TR_TITLE";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    static final RowMapper<FormMappingDO> ROW_MAPPER = new RowMapper<FormMappingDO>(){

        public FormMappingDO mapRow(ResultSet rs, int rowNum) throws SQLException {
            FormMappingDO form = new FormMappingDO();
            form.setKey(rs.getString(FormMappingDao.FIELD_KEY));
            form.setSchemeKey(rs.getString(FormMappingDao.FIELD_SCHEME));
            form.setSourceKey(rs.getString(FormMappingDao.FIELD_SR_KEY));
            form.setTargetCode(rs.getString(FormMappingDao.FIELD_TR_CODE));
            form.setTargetTitle(rs.getString(FormMappingDao.FIELD_TR_TITLE));
            return form;
        }
    };

    public List<FormMappingDO> list(String schemeKey) {
        String sql = String.format("SELECT * FROM %s where %s = ?", TABLE_NAME, FIELD_SCHEME);
        Object[] arg = new Object[]{schemeKey};
        return this.jdbcTemplate.query(sql, ROW_MAPPER, arg);
    }

    public List<FormMappingDO> list(String schemeKey, List<String> keys) {
        String sql = String.format("SELECT * FROM %s where %s = :%s AND %s in (:%s)", TABLE_NAME, FIELD_SCHEME, FIELD_SCHEME, FIELD_SR_KEY, FIELD_SR_KEY);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        HashMap<String, Object> params = new HashMap<String, Object>(1);
        params.put(FIELD_SCHEME, schemeKey);
        params.put(FIELD_SR_KEY, keys);
        return namedParameterJdbcTemplate.query(sql, params, ROW_MAPPER);
    }

    public void batchInsert(List<FormMappingDO> mappings) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)", TABLE_NAME, FIELD_KEY, FIELD_SCHEME, FIELD_SR_KEY, FIELD_TR_CODE, FIELD_TR_TITLE);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (FormMappingDO mapping : mappings) {
            Object[] arg = new Object[]{mapping.getKey(), mapping.getSchemeKey(), mapping.getSourceKey(), mapping.getTargetCode(), mapping.getTargetTitle()};
            args.add(arg);
        }
        this.jdbcTemplate.batchUpdate(sql, args);
    }

    public void batchUpdate(List<FormMappingDO> mappings) {
        String sql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", TABLE_NAME, FIELD_TR_CODE, FIELD_TR_TITLE, FIELD_KEY);
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (FormMappingDO mapping : mappings) {
            Object[] arg = new Object[]{mapping.getTargetCode(), mapping.getTargetTitle(), mapping.getKey()};
            args.add(arg);
        }
        this.jdbcTemplate.batchUpdate(sql, args);
    }

    public void delete(String schemeKey) {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TABLE_NAME, FIELD_SCHEME);
        Object[] arg = new Object[]{schemeKey};
        this.jdbcTemplate.update(sql, arg);
    }

    public void delete(String schemeKey, String ... keys) {
        String sql = String.format("DELETE FROM %s WHERE %s = :%s and %s in (:%s)", TABLE_NAME, FIELD_SCHEME, FIELD_SCHEME, FIELD_SR_KEY, FIELD_SR_KEY);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        MapSqlParameterSource[] params = new MapSqlParameterSource[keys.length];
        for (int i = 0; i < keys.length; ++i) {
            MapSqlParameterSource param = new MapSqlParameterSource();
            param.addValue(FIELD_SCHEME, (Object)schemeKey);
            param.addValue(FIELD_SR_KEY, (Object)keys[i]);
            params[i] = param;
        }
        namedParameterJdbcTemplate.batchUpdate(sql, (SqlParameterSource[])params);
    }
}

