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
package com.jiuqi.nr.calibre2.internal.dao.impl;

import com.jiuqi.nr.calibre2.internal.dao.ICalibreSubListDao;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreSubListMapper;
import com.jiuqi.nr.calibre2.internal.domain.BatchCalibreSubListDO;
import com.jiuqi.nr.calibre2.internal.domain.CalibreSubListDO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class CalibreSubListDaoImpl
implements ICalibreSubListDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CalibreSubListDO> query(CalibreSubListDO calibreSubListDO) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = ? AND %s = ?", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE", "CS_CODE");
        Object[] args = new Object[]{calibreSubListDO.getCalibreCode(), calibreSubListDO.getCode()};
        return this.jdbcTemplate.query(querySql, (RowMapper)new CalibreSubListMapper(), args);
    }

    @Override
    public List<CalibreSubListDO> batchQuery(BatchCalibreSubListDO batchCalibreSubListDO) {
        String querySql = String.format("SELECT * FROM %s WHERE %s = :%s AND %s in(:%s)", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE", "CS_CALIBRE_CODE", "CS_CODE", "CS_CODE");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("CS_CALIBRE_CODE", (Object)batchCalibreSubListDO.getCalibreDefine());
        List<CalibreSubListDO> calibreSubListDOList = batchCalibreSubListDO.getCalibreSubListDOList();
        List keys = calibreSubListDOList.stream().map(CalibreSubListDO::getCode).collect(Collectors.toList());
        source.addValue("CS_CODE", keys);
        return new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate).query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreSubListMapper());
    }

    @Override
    public int add(CalibreSubListDO calibreSubListDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE", "CS_CODE", "CS_VALUE");
        Object[] arg = new Object[]{calibreSubListDO.getCalibreCode(), calibreSubListDO.getCode(), calibreSubListDO.getValue()};
        return this.jdbcTemplate.update(addSql, arg);
    }

    @Override
    public int update(CalibreSubListDO calibreSubListDO) {
        String updateSql = String.format("UPDATE %s SET %s = ?  WHERE %s = ? AND %s = ?", "NR_CALIBRE_SUBLIST", "CS_VALUE", "CS_CALIBRE_CODE", "CS_CODE");
        Object[] arg = new Object[]{calibreSubListDO.getValue(), calibreSubListDO.getCalibreCode(), calibreSubListDO.getCode()};
        return this.jdbcTemplate.update(updateSql, arg);
    }

    @Override
    public int delete(CalibreSubListDO calibreSubListDO) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE", "CS_CODE");
        Object[] arg = new Object[]{calibreSubListDO.getCalibreCode(), calibreSubListDO.getCode()};
        return this.jdbcTemplate.update(deleteSql, arg);
    }

    @Override
    public int deleteAll(CalibreSubListDO calibreSubListDO) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE");
        Object[] arg = new Object[]{calibreSubListDO.getCalibreCode()};
        return this.jdbcTemplate.update(deleteSql, arg);
    }

    @Override
    public int[] batchAdd(BatchCalibreSubListDO batchCalibreSubListDO) {
        String addSql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE", "CS_CODE", "CS_VALUE");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (CalibreSubListDO subListDO : batchCalibreSubListDO.getCalibreSubListDOList()) {
            Object[] arg = new Object[]{subListDO.getCalibreCode(), subListDO.getCode(), subListDO.getValue()};
            args.add(arg);
        }
        return this.jdbcTemplate.batchUpdate(addSql, args);
    }

    @Override
    public int[] batchUpdate(BatchCalibreSubListDO batchCalibreSubListDO) {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ? ", "NR_CALIBRE_SUBLIST", "CS_VALUE", "CS_CALIBRE_CODE", "CS_CODE");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (CalibreSubListDO subListDO : batchCalibreSubListDO.getCalibreSubListDOList()) {
            Object[] arg = new Object[]{subListDO.getValue(), subListDO.getCalibreCode(), subListDO.getCode()};
            args.add(arg);
        }
        return this.jdbcTemplate.batchUpdate(updateSql, args);
    }

    @Override
    public int[] batchDelete(BatchCalibreSubListDO batchCalibreSubListDO) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ? AND %s = ?", "NR_CALIBRE_SUBLIST", "CS_CALIBRE_CODE", "CS_CODE");
        ArrayList<Object[]> args = new ArrayList<Object[]>();
        for (CalibreSubListDO subListDO : batchCalibreSubListDO.getCalibreSubListDOList()) {
            Object[] arg = new Object[]{subListDO.getCalibreCode(), subListDO.getCode()};
            args.add(arg);
        }
        return this.jdbcTemplate.batchUpdate(deleteSql, args);
    }
}

