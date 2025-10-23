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
package com.jiuqi.nr.calibre2.internal.dao.impl;

import com.jiuqi.nr.calibre2.domain.CalibreDataDO;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreDataDao;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDataMapper;
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
public class CalibreDataDaoImpl
implements ICalibreDataDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CalibreDataDO> queryByCalibreCode(String calibreCode) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode);
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDataMapper());
    }

    @Override
    public List<CalibreDataDO> query(String calibreCode, String key, String value) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND %s = :%s", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, key, key);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode).addValue(key, (Object)value);
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDataMapper());
    }

    @Override
    public List<CalibreDataDO> queryRoot(String calibreCode) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND (%s = :%s OR %s IS NULL)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_PARENT);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode).addValue(CalibreDataMapper.FIELD_PARENT, (Object)"");
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDataMapper());
    }

    @Override
    public List<CalibreDataDO> fuzzyQuery(String calibreCode, String keyWord) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND ( LOWER( %s ) LIKE LOWER( :%s ) OR LOWER( %s ) LIKE LOWER( :%s ) )", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_NAME);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode).addValue(CalibreDataMapper.FIELD_CODE, (Object)"%".concat(keyWord).concat("%")).addValue(CalibreDataMapper.FIELD_NAME, (Object)"%".concat(keyWord).concat("%"));
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDataMapper());
    }

    @Override
    public List<CalibreDataDO> batchQuery(String calibreCode, String key, List<String> values) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND %s in (:%s)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, key, key);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode).addValue(key, values);
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDataMapper());
    }

    @Override
    public int add(CalibreDataDO calibreDataDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE);
        Map<String, Object> stringObjectMap = calibreDataDO.toMap();
        int update = this.namedParameterJdbcTemplate.update(addSql, stringObjectMap);
        return update;
    }

    @Override
    public int[] batchAdd(List<CalibreDataDO> calibreDataDOS) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s, :%s)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE);
        Map[] maps = new Map[calibreDataDOS.size()];
        for (int i = 0; i < calibreDataDOS.size(); ++i) {
            CalibreDataDO calibreDataDO = calibreDataDOS.get(i);
            maps[i] = calibreDataDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(addSql, maps);
        return ints;
    }

    @Override
    public int addWithoutParent(CalibreDataDO calibreDataDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE);
        Map<String, Object> stringObjectMap = calibreDataDO.toMap();
        int update = this.namedParameterJdbcTemplate.update(addSql, stringObjectMap);
        return update;
    }

    @Override
    public int[] batchAddWithoutParent(List<CalibreDataDO> calibreDataDOS) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (:%s, :%s, :%s, :%s, :%s, :%s)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_ORDER, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE);
        Map[] maps = new Map[calibreDataDOS.size()];
        for (int i = 0; i < calibreDataDOS.size(); ++i) {
            CalibreDataDO calibreDataDO = calibreDataDOS.get(i);
            maps[i] = calibreDataDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(addSql, maps);
        return ints;
    }

    @Override
    public int delete(String calibreCode, String field, String value) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s AND %s = :%s", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode).addValue(field, (Object)value);
        return this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)source);
    }

    @Override
    public int deleteAll(String calibreCode) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s ", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode);
        return this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)source);
    }

    @Override
    public int batchDelete(String calibreCode, String field, List<String> batchArgs) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = :%s AND %s in (:%s)", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(CalibreDataMapper.FIELD_CALIBRE_CODE, (Object)calibreCode).addValue(field, batchArgs);
        int update = this.namedParameterJdbcTemplate.update(deleteSql, (SqlParameterSource)source);
        return update;
    }

    @Override
    public int update(CalibreDataDO calibreDataDO) {
        String updateSql = String.format("UPDATE %s SET %s = :%s, %s = :%s, %s = :%s WHERE %s = :%s AND %s = :%s", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_CODE);
        int update = this.namedParameterJdbcTemplate.update(updateSql, calibreDataDO.toMap());
        return update;
    }

    @Override
    public int[] batchUpdate(List<CalibreDataDO> calibreDataDOS) {
        String updateSql = String.format("UPDATE %s SET %s = :%s, %s = :%s, %s = :%s WHERE %s = :%s AND %s = :%s", "NR_CALIBRE_DATA", CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_NAME, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_PARENT, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_VALUE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CALIBRE_CODE, CalibreDataMapper.FIELD_CODE, CalibreDataMapper.FIELD_CODE);
        Map[] maps = new Map[calibreDataDOS.size()];
        for (int i = 0; i < calibreDataDOS.size(); ++i) {
            CalibreDataDO calibreDataDO = calibreDataDOS.get(i);
            maps[i] = calibreDataDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(updateSql, maps);
        return ints;
    }

    @Override
    public int[] batchChangeOrder(List<CalibreDataDO> calibreDataDOS, String field) {
        String updateSql = String.format("UPDATE %s SET %s = :%s WHERE %s = :%s ", "NR_CALIBRE_DATA", field, field, CalibreDataMapper.FIELD_KEY, CalibreDataMapper.FIELD_KEY);
        Map[] maps = new Map[calibreDataDOS.size()];
        for (int i = 0; i < calibreDataDOS.size(); ++i) {
            CalibreDataDO calibreDataDO = calibreDataDOS.get(i);
            maps[i] = calibreDataDO.toMap();
        }
        int[] ints = this.namedParameterJdbcTemplate.batchUpdate(updateSql, maps);
        return ints;
    }
}

