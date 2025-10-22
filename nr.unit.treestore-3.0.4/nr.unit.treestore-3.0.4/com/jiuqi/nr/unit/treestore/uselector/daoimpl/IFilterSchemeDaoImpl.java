/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.unit.treestore.uselector.daoimpl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.unit.treestore.enumeration.NamedParameterSqlBuilder;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterScheme;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterSchemeImpl;
import com.jiuqi.nr.unit.treestore.uselector.bean.USFilterTemplateImpl;
import com.jiuqi.nr.unit.treestore.uselector.dao.IFilterSchemeDao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class IFilterSchemeDaoImpl
implements IFilterSchemeDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public String insert(USFilterScheme scheme) {
        String[] columns = new String[]{"fc_key", "fc_title", "fc_entity", "fc_owner", "fc_shared", "fc_template", "fc_createtime"};
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("nr_uselector_filter_scheme");
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = this.buildSqlParameterMapSource(scheme);
        source.addValue("fc_createtime", (Object)Timestamp.valueOf(LocalDateTime.now()));
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        int execute = template.update(sqlBuilder.toString(), (SqlParameterSource)source);
        return execute == 1 ? scheme.getKey() : null;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public boolean update(USFilterScheme scheme) {
        String sql = String.format("update %s set fc_title = :%s,fc_shared = :%s,fc_template = :%s where fc_key= :%s ", "nr_uselector_filter_scheme", "fc_title", "fc_shared", "fc_template", "fc_key");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fc_key", (Object)scheme.getKey());
        source.addValue("fc_title", (Object)scheme.getTitle());
        source.addValue("fc_shared", (Object)scheme.isShared());
        source.addValue("fc_template", (Object)scheme.getTemplate().toJSON());
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        int update = template.update(sql, (SqlParameterSource)source);
        return update == 1;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public boolean updateShared(String schemeKey, boolean isShared) {
        String sql = String.format("update %s set fc_shared = :%s where fc_key= :%s ", "nr_uselector_filter_scheme", "fc_shared", "fc_key");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fc_key", (Object)schemeKey);
        source.addValue("fc_shared", (Object)isShared);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        int update = template.update(sql, (SqlParameterSource)source);
        return update == 1;
    }

    @Override
    public USFilterScheme find(String key) {
        if (StringUtils.isNotEmpty((String)key)) {
            String sql = String.format("SELECT fc_key,fc_title,fc_entity,fc_owner,fc_shared,fc_template,fc_createtime FROM %s WHERE fc_key = :%s ORDER BY fc_createtime ", "nr_uselector_filter_scheme", "fc_key");
            MapSqlParameterSource source = new MapSqlParameterSource();
            source.addValue("fc_key", (Object)key);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            List query = template.query(sql, (SqlParameterSource)source, this::readUSFilterScheme);
            return !query.isEmpty() ? (USFilterScheme)query.get(0) : null;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int remove(String key) {
        String sql = String.format("DELETE FROM %s WHERE fc_key = :%s", "nr_uselector_filter_scheme", "fc_key");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fc_key", (Object)key);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    @Override
    public List<USFilterScheme> find(String owner, String entityId) {
        String sql = String.format("SELECT fc_key,fc_title,fc_entity,fc_owner,fc_shared,fc_template,fc_createtime FROM %s WHERE fc_owner = :%s AND fc_entity = :%s ORDER BY fc_createtime ", "nr_uselector_filter_scheme", "fc_owner", "fc_entity");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fc_owner", (Object)owner);
        source.addValue("fc_entity", (Object)entityId);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readUSFilterScheme);
    }

    @Override
    public List<USFilterScheme> find(Set<String> owners, String entityId) {
        String sql = String.format("SELECT fc_key,fc_title,fc_entity,fc_owner,fc_shared,fc_template,fc_createtime FROM %s WHERE fc_owner IN (:%s) AND fc_entity = :%s ORDER BY fc_owner, fc_createtime ", "nr_uselector_filter_scheme", "fc_owner", "fc_entity");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fc_owner", owners);
        source.addValue("fc_entity", (Object)entityId);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readUSFilterScheme);
    }

    private MapSqlParameterSource buildSqlParameterMapSource(USFilterScheme scheme) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("fc_key", (Object)scheme.getKey());
        source.addValue("fc_title", (Object)scheme.getTitle());
        source.addValue("fc_entity", (Object)scheme.getEntityId());
        source.addValue("fc_owner", (Object)scheme.getOwner());
        source.addValue("fc_shared", (Object)scheme.isShared());
        if (scheme.getTemplate() != null) {
            source.addValue("fc_template", (Object)scheme.getTemplate().toJSON());
        } else {
            source.addValue("fc_template", null);
        }
        return source;
    }

    private USFilterScheme readUSFilterScheme(ResultSet rs, int rowIdx) throws SQLException {
        USFilterSchemeImpl impl = new USFilterSchemeImpl();
        impl.setKey(rs.getString("fc_key"));
        impl.setTitle(rs.getString("fc_title"));
        impl.setEntityId(rs.getString("fc_entity"));
        impl.setOwner(rs.getString("fc_owner"));
        impl.setShared(rs.getBoolean("fc_shared"));
        impl.setTemplate(new USFilterTemplateImpl(rs.getString("fc_template")));
        impl.setCreateTime(rs.getTimestamp("fc_createtime"));
        return impl;
    }
}

