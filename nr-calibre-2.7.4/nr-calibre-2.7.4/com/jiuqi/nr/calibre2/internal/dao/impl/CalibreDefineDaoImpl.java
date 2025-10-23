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

import com.jiuqi.nr.calibre2.domain.CalibreDefineDO;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreDefineDao;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreDefineMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class CalibreDefineDaoImpl
implements ICalibreDefineDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplates;

    @Override
    public List<CalibreDefineDO> query() {
        String querySql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s ,%s FROM %s", "CD_KEY", "CD_CODE", "CD_NAME", "CD_GROUP", "CD_TYPE", "CD_STRUCT_TYPE", "CD_EXPRESSION_VALUES", "CD_ENTITYID", "CD_ORDER", "NR_CALIBRE_DEFINE");
        return this.jdbcTemplate.query(querySql, (RowMapper)new CalibreDefineMapper());
    }

    @Override
    public List<CalibreDefineDO> queryByGroup(String groupKey) {
        return this.list(groupKey, "CD_GROUP");
    }

    @Override
    public List<CalibreDefineDO> queryByRefer(String refer) {
        return this.list(refer, "CD_ENTITYID");
    }

    @Override
    public List<CalibreDefineDO> searchByNameOrCode(String name, String group) {
        String querySql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s ,%s FROM %s WHERE %s = :%s AND (LOWER( %s ) LIKE LOWER( :%s ) OR LOWER( %s ) LIKE LOWER( :%s )) ", "CD_KEY", "CD_CODE", "CD_NAME", "CD_GROUP", "CD_TYPE", "CD_STRUCT_TYPE", "CD_EXPRESSION_VALUES", "CD_ENTITYID", "CD_ORDER", "NR_CALIBRE_DEFINE", "CD_GROUP", "CD_GROUP", "CD_NAME", "CD_NAME", "CD_CODE", "CD_CODE");
        MapSqlParameterSource source = new MapSqlParameterSource().addValue("CD_GROUP", (Object)group).addValue("CD_NAME", (Object)"%".concat(name).concat("%")).addValue("CD_CODE", (Object)"%".concat(name).concat("%"));
        return this.jdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDefineMapper());
    }

    @Override
    public List<CalibreDefineDO> searchByNameOrCode(String name) {
        String querySql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s ,%s FROM %s WHERE LOWER( %s ) LIKE LOWER( :%s ) OR LOWER( %s ) LIKE LOWER( :%s )", "CD_KEY", "CD_CODE", "CD_NAME", "CD_GROUP", "CD_TYPE", "CD_STRUCT_TYPE", "CD_EXPRESSION_VALUES", "CD_ENTITYID", "CD_ORDER", "NR_CALIBRE_DEFINE", "CD_NAME", "CD_NAME", "CD_CODE", "CD_CODE");
        MapSqlParameterSource source = new MapSqlParameterSource().addValue("CD_NAME", (Object)"%".concat(name).concat("%")).addValue("CD_CODE", (Object)"%".concat(name).concat("%"));
        return this.jdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDefineMapper());
    }

    @Override
    public CalibreDefineDO get(String key) {
        List<CalibreDefineDO> groups = this.list(key, "CD_KEY");
        if (!CollectionUtils.isEmpty(groups)) {
            return groups.get(0);
        }
        return null;
    }

    @Override
    public CalibreDefineDO getByCode(String code) {
        List<CalibreDefineDO> groups = this.list(code, "CD_CODE");
        if (!CollectionUtils.isEmpty(groups)) {
            return groups.get(0);
        }
        return null;
    }

    @Override
    public int insert(CalibreDefineDO calibreGroup) {
        String insertSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s ,%s ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", "NR_CALIBRE_DEFINE", "CD_KEY", "CD_CODE", "CD_NAME", "CD_GROUP", "CD_TYPE", "CD_STRUCT_TYPE", "CD_EXPRESSION_VALUES", "CD_ENTITYID", "CD_ORDER");
        Object[] arg = new Object[]{calibreGroup.getKey(), calibreGroup.getCode(), calibreGroup.getName(), calibreGroup.getGroup(), calibreGroup.getType(), calibreGroup.getStructType(), calibreGroup.getExpression_Values(), calibreGroup.getEntityId(), calibreGroup.getOrder()};
        return this.jdbcTemplates.update(insertSql, arg);
    }

    @Override
    public int update(CalibreDefineDO calibreGroup) {
        String updateSql = String.format("UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?", "NR_CALIBRE_DEFINE", "CD_NAME", "CD_GROUP", "CD_TYPE", "CD_STRUCT_TYPE", "CD_EXPRESSION_VALUES", "CD_ENTITYID", "CD_KEY");
        Object[] arg = new Object[]{calibreGroup.getName(), calibreGroup.getGroup(), calibreGroup.getType(), calibreGroup.getStructType(), calibreGroup.getExpression_Values(), calibreGroup.getEntityId(), calibreGroup.getKey()};
        return this.jdbcTemplates.update(updateSql, arg);
    }

    @Override
    public int delete(String calibreKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", "NR_CALIBRE_DEFINE", "CD_KEY");
        return this.jdbcTemplates.update(deleteSql, new Object[]{calibreKey});
    }

    @Override
    public int[] batchDelete(List<Object[]> batchArgs) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", "NR_CALIBRE_DEFINE", "CD_KEY");
        return this.jdbcTemplates.batchUpdate(deleteSql, batchArgs);
    }

    @Override
    public int[] batchUpdateOrder(List<Object[]> batchArgs) {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", "NR_CALIBRE_DEFINE", "CD_ORDER", "CD_KEY");
        return this.jdbcTemplates.batchUpdate(updateSql, batchArgs);
    }

    private List<CalibreDefineDO> list(String key, String field) {
        String querySql = String.format("SELECT %s, %s, %s, %s, %s, %s, %s, %s ,%s FROM %s WHERE %s = :%s", "CD_KEY", "CD_CODE", "CD_NAME", "CD_GROUP", "CD_TYPE", "CD_STRUCT_TYPE", "CD_EXPRESSION_VALUES", "CD_ENTITYID", "CD_ORDER", "NR_CALIBRE_DEFINE", field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(field, (Object)key);
        return this.jdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreDefineMapper());
    }
}

