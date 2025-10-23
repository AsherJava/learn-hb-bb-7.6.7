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

import com.jiuqi.nr.transmission.data.dao.ISchemeGroupDao;
import com.jiuqi.nr.transmission.data.dao.mapper.SchemeGroupMapper;
import com.jiuqi.nr.transmission.data.domain.SyncSchemeGroupDO;
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
public class SchemeGroupDaoImpl
implements ISchemeGroupDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int add(SyncSchemeGroupDO schemeGroupDO) {
        String insertSql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", "NR_TRANS_GROUP", "TG_KEY", "TG_TITLE", "TG_PARENT", "TG_ORDER");
        Object[] arg = new Object[]{schemeGroupDO.getKey(), schemeGroupDO.getTitle(), schemeGroupDO.getParent(), schemeGroupDO.getOrder()};
        return this.jdbcTemplate.update(insertSql, arg);
    }

    @Override
    public int delete(String groupKey) {
        String deleteSql = String.format("DELETE FROM %s WHERE %s = ?", "NR_TRANS_GROUP", "TG_KEY");
        return this.jdbcTemplate.update(deleteSql, new Object[]{groupKey});
    }

    @Override
    public int update(SyncSchemeGroupDO schemeGroupDO) {
        String updateSql = String.format("UPDATE %s SET %s = ?, %s = ? WHERE %s = ?", "NR_TRANS_GROUP", "TG_TITLE", "TG_PARENT", "TG_KEY");
        Object[] arg = new Object[]{schemeGroupDO.getTitle(), schemeGroupDO.getParent(), schemeGroupDO.getKey()};
        return this.jdbcTemplate.update(updateSql, arg);
    }

    @Override
    public int updates(String oldStr, String newStr, String field) {
        String updateSql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", "NR_TRANS_GROUP", field, field);
        Object[] arg = new Object[]{newStr, oldStr};
        return this.jdbcTemplate.update(updateSql, arg);
    }

    @Override
    public SyncSchemeGroupDO get(String groupKey) {
        List<SyncSchemeGroupDO> groups = this.list(groupKey, "TG_KEY");
        if (!CollectionUtils.isEmpty(groups)) {
            return groups.get(0);
        }
        return null;
    }

    @Override
    public SyncSchemeGroupDO getByTitle(String grouptitle) {
        MapSqlParameterSource source;
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = :%s", "TG_KEY", "TG_TITLE", "TG_PARENT", "TG_ORDER", "NR_TRANS_GROUP", "TG_TITLE", "TG_TITLE");
        List query = this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)(source = new MapSqlParameterSource().addValue("TG_TITLE", (Object)grouptitle)), (RowMapper)new SchemeGroupMapper());
        if (!CollectionUtils.isEmpty(query)) {
            return (SyncSchemeGroupDO)query.get(0);
        }
        return null;
    }

    @Override
    public List<SyncSchemeGroupDO> list() {
        return null;
    }

    @Override
    public List<SyncSchemeGroupDO> listByParent(String parentKey) {
        return this.list(parentKey, "TG_PARENT");
    }

    @Override
    public List<SyncSchemeGroupDO> listByName(String name) {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE LOWER( %s ) LIKE LOWER( :%s )", "TG_KEY", "TG_TITLE", "TG_PARENT", "TG_ORDER", "NR_TRANS_GROUP", "TG_TITLE", "TG_TITLE");
        MapSqlParameterSource source = new MapSqlParameterSource().addValue("TG_TITLE", (Object)"%".concat(name).concat("%"));
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SchemeGroupMapper());
    }

    private List<SyncSchemeGroupDO> list(String id, String field) {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = :%s", "TG_KEY", "TG_TITLE", "TG_PARENT", "TG_ORDER", "NR_TRANS_GROUP", field, field);
        MapSqlParameterSource source = new MapSqlParameterSource().addValue(field, (Object)id);
        return this.namedParameterJdbcTemplate.query(querySql, (SqlParameterSource)source, (RowMapper)new SchemeGroupMapper());
    }

    private List<SyncSchemeGroupDO> listRootGroup(String field) {
        String querySql = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = %s", "TG_KEY", "TG_TITLE", "TG_PARENT", "TG_ORDER", "NR_TRANS_GROUP", "TG_PARENT", "00000000-0000-0000-0000-000000000000");
        return this.namedParameterJdbcTemplate.query(querySql, (RowMapper)new SchemeGroupMapper());
    }
}

