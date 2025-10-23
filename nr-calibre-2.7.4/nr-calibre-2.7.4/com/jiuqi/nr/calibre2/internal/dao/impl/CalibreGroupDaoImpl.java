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

import com.jiuqi.nr.calibre2.domain.CalibreGroupDO;
import com.jiuqi.nr.calibre2.internal.dao.ICalibreGroupDao;
import com.jiuqi.nr.calibre2.internal.dao.mapper.CalibreGroupMapper;
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
public class CalibreGroupDaoImpl
implements ICalibreGroupDao {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplates;

    @Override
    public List<CalibreGroupDO> query() {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s", "CG_KEY", "CG_NAME", "CG_PARENT", "CG_ORDER", "NR_CALIBRE_GROUP");
        return this.jdbcTemplate.query(querySql, (RowMapper)new CalibreGroupMapper());
    }

    @Override
    public List<CalibreGroupDO> queryByParent(String id) {
        if (id != null) {
            return this.list(id, "CG_PARENT");
        }
        return this.list("CG_PARENT");
    }

    @Override
    public List<CalibreGroupDO> queryByName(String name) {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE LOWER( %s ) LIKE LOWER( :%s )", "CG_KEY", "CG_NAME", "CG_PARENT", "CG_ORDER", "NR_CALIBRE_GROUP", "CG_NAME", "CG_NAME");
        MapSqlParameterSource source = new MapSqlParameterSource().addValue("CG_NAME", (Object)"%".concat(name).concat("%"));
        return this.jdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreGroupMapper());
    }

    @Override
    public CalibreGroupDO get(String id) {
        List<CalibreGroupDO> groups = this.list(id, "CG_KEY");
        if (!CollectionUtils.isEmpty(groups)) {
            return groups.get(0);
        }
        return null;
    }

    @Override
    public int insert(CalibreGroupDO calibreGroup) {
        String insertSql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", "NR_CALIBRE_GROUP", "CG_KEY", "CG_NAME", "CG_PARENT", "CG_ORDER");
        Object[] arg = new Object[]{calibreGroup.getKey(), calibreGroup.getName(), calibreGroup.getParent(), calibreGroup.getOrder()};
        return this.jdbcTemplates.update(insertSql, arg);
    }

    @Override
    public int update(CalibreGroupDO calibreGroup) {
        String updateSql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", "NR_CALIBRE_GROUP", "CG_NAME", "CG_PARENT", "CG_KEY");
        Object[] arg = new Object[]{calibreGroup.getName(), calibreGroup.getParent(), calibreGroup.getKey()};
        return this.jdbcTemplates.update(updateSql, arg);
    }

    @Override
    public int delete(String calibreKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", "NR_CALIBRE_GROUP", "CG_KEY");
        return this.jdbcTemplates.update(deleteSql, new Object[]{calibreKey});
    }

    private List<CalibreGroupDO> list(String id, String field) {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = :%s", "CG_KEY", "CG_NAME", "CG_PARENT", "CG_ORDER", "NR_CALIBRE_GROUP", field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(field, (Object)id);
        return this.jdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreGroupMapper());
    }

    private List<CalibreGroupDO> list(String field) {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = :%s", "CG_KEY", "CG_NAME", "CG_PARENT", "CG_ORDER", "NR_CALIBRE_GROUP", field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(field, (Object)"00000000-0000-0000-0000-000000000000");
        return this.jdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new CalibreGroupMapper());
    }
}

