/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.validation.constraints.NotNull
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.jdbc.core.simple.SimpleJdbcInsert
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.tag.manager.dao.impl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.tag.manager.bean.TagImpl;
import com.jiuqi.nr.tag.manager.dao.TagDao;
import com.jiuqi.nr.tag.manager.dao.impl.TagEntityIDHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TagDaoImpl
implements TagDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    protected static final String QUERY_TEMPLATE = String.format("select TG_KEY,TG_TITLE,TG_OWNER,TG_VIEWKEY,TG_FORMULA,TG_CATEGORY,TG_SHARED,TG_RANGE_MODIFY,TG_ORDER,TG_DESC from %s ", "ITREE_TAG");

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] batchInsert(@NotNull List<TagImpl> impls) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("ITREE_TAG");
        MapSqlParameterSource[] sources = this.buildBatchSqlParameterSource(impls);
        return insertActor.executeBatch((SqlParameterSource[])sources);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] batchUpdate(@NotNull List<TagImpl> impls) {
        String sql = String.format("update %s set TG_OWNER = :%s,TG_TITLE = :%s,TG_SHARED = :%s,TG_VIEWKEY = :%s,TG_FORMULA = :%s,TG_CATEGORY = :%s,TG_RANGE_MODIFY=:%s,TG_ORDER = :%s,TG_DESC = :%s where TG_KEY = :%s", "ITREE_TAG", "tg_owner", "tg_title", "tg_shared", "tg_viewkey", "tg_formula", "tg_category", "tg_range_modify", "tg_order", "tg_desc", "tg_key");
        MapSqlParameterSource[] sources = this.buildBatchSqlParameterSource(impls);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])sources);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int batchDelete(@NotNull List<String> keys) {
        String sql = String.format("delete from %s where TG_KEY in (:%s)", "ITREE_TAG", "tg_key");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tg_key", keys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public TagImpl findByKey(@NotNull String key) {
        String sql = String.format(QUERY_TEMPLATE + " where tg_key = :%s", "tg_key");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tg_key", (Object)key);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query != null && query.size() == 1 ? (TagImpl)query.get(0) : null;
    }

    @Override
    public List<TagImpl> findAllByOV(@NotNull String owner, @NotNull String viewKey) {
        viewKey = TagEntityIDHelper.getEntityId(viewKey);
        String sql = String.format(QUERY_TEMPLATE + " where (TG_OWNER =:%s or TG_SHARED =:%s)  and TG_VIEWKEY =:%s  order by TG_ORDER", "tg_owner", "tg_shared", "tg_viewkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tg_owner", (Object)owner);
        parameterSource.addValue("tg_viewkey", (Object)viewKey);
        parameterSource.addValue("tg_shared", (Object)1);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query != null ? query : new ArrayList(0);
    }

    @Override
    public boolean checkTitleRepeat(String title) {
        String sql = String.format("select TG_TITLE from %s where TG_TITLE = :%s", "ITREE_TAG", "tg_title");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tg_title", (Object)title);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, this::readTagTitle);
        return !query.isEmpty();
    }

    @Override
    public List<String> findAllTagTitles() {
        String sql = String.format("select TG_TITLE from %s ", "ITREE_TAG");
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, this::readTagTitle);
    }

    @Override
    public List<TagImpl> findAllTags() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(QUERY_TEMPLATE, (rs, row) -> this.create(rs));
        return query.size() > 0 ? query : new ArrayList(0);
    }

    private String readTagTitle(ResultSet rs, int rowIdx) throws SQLException {
        return rs.getString("tg_title");
    }

    private MapSqlParameterSource[] buildBatchSqlParameterSource(List<TagImpl> impls) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[impls.size()];
        for (int i = 0; i < impls.size(); ++i) {
            TagImpl tag = impls.get(i);
            if (tag.getKey() == null) {
                tag.setKey(UUID.randomUUID().toString());
            }
            sources[i] = this.buildSqlParameterSource(tag);
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameterSource(TagImpl tag) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("tg_key", (Object)tag.getKey()).addValue("tg_owner", (Object)tag.getOwner()).addValue("tg_viewkey", (Object)TagEntityIDHelper.getEntityId(tag.getViewKey())).addValue("tg_title", (Object)tag.getTitle()).addValue("tg_category", (Object)tag.getCategory()).addValue("tg_formula", (Object)tag.getFormula()).addValue("tg_shared", (Object)(tag.getShared() != false ? 1 : 0)).addValue("tg_order", (Object)tag.getOrder()).addValue("tg_desc", (Object)tag.getDescription()).addValue("tg_range_modify", (Object)(tag.getRangeModify() != false ? 1 : 0));
        return source;
    }

    private TagImpl create(ResultSet rs) throws SQLException {
        TagImpl tag = new TagImpl();
        tag.setKey(rs.getString("tg_key"));
        tag.setCategory(rs.getString("tg_category"));
        tag.setDescription(rs.getString("tg_desc"));
        tag.setFormula(rs.getString("tg_formula"));
        tag.setOrder(rs.getString("tg_order"));
        tag.setOwner(rs.getString("tg_owner"));
        tag.setShared(rs.getBoolean("tg_shared"));
        tag.setTitle(rs.getString("tg_title"));
        tag.setViewKey(rs.getString("tg_viewkey"));
        tag.setRangeModify(rs.getBoolean("tg_range_modify"));
        return tag;
    }
}

