/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
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
import com.jiuqi.nr.tag.manager.bean.TagNodeImpl;
import com.jiuqi.nr.tag.manager.dao.TagNodeDao;
import com.jiuqi.nr.tag.manager.dao.impl.TagEntityIDHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TagNodeDaoImpl
implements TagNodeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final String QUERY_TEMPLATE = String.format("select TN_TGKEY,TN_ENTKEY,TN_VIEWKEY from %s ", "ITREE_TAG_NODE");

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] batchInsert(@NonNull List<TagNodeImpl> impls) {
        MapSqlParameterSource[] sources = this.buildBatchSqlParameterSource(impls);
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("ITREE_TAG_NODE");
        return insertActor.executeBatch((SqlParameterSource[])sources);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int delEntityDataOfTag(@NonNull String tgKey) {
        String sql = String.format("delete from %s where TN_TGKEY =:%s ", "ITREE_TAG_NODE", "tn_tgkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_tgkey", (Object)tgKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int delTagOfEntityData(@NonNull String viewkey, @NonNull String entKey) {
        viewkey = TagEntityIDHelper.getEntityId(viewkey);
        String sql = String.format("delete from %s where TN_ENTKEY =:%s and TN_VIEWKEY =:%s ", "ITREE_TAG_NODE", "tn_entkey", "tn_viewkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_entkey", (Object)entKey);
        parameterSource.addValue("tn_viewkey", (Object)viewkey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)parameterSource);
    }

    @Override
    public int batchDelEntityDataOfTag(@NonNull List<String> tgKeys) {
        if (!tgKeys.isEmpty()) {
            String sql = String.format("delete from %s where TN_TGKEY in (:%s)", "ITREE_TAG_NODE", "tn_tgkey");
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("tn_tgkey", tgKeys);
            NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
            return template.update(sql, (SqlParameterSource)parameterSource);
        }
        return 0;
    }

    @Override
    public int sumEntityDatasOfTag(@NonNull String tgKey) {
        String sql = String.format("select count(*) from %s where tn_tgkey = :%s ", "ITREE_TAG_NODE", "tn_tgkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_tgkey", (Object)tgKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (Integer)template.queryForObject(sql, (SqlParameterSource)parameterSource, Integer.TYPE);
    }

    @Override
    public List<TagNodeImpl> countOfTag(String tgKey) {
        String sql = String.format(QUERY_TEMPLATE + " where tn_tgkey = :%s ", "tn_tgkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_tgkey", (Object)tgKey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query != null ? query : new ArrayList();
    }

    @Override
    public List<TagNodeImpl> countOfTags(List<String> tgKeys) {
        String sql = String.format(QUERY_TEMPLATE + "where tn_tgkey in (:%s)", "tn_tgkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_tgkey", tgKeys);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query != null ? query : new ArrayList();
    }

    @Override
    public List<TagNodeImpl> countOfEntityData(String viewkey, String entKey) {
        viewkey = TagEntityIDHelper.getEntityId(viewkey);
        String sql = String.format(QUERY_TEMPLATE + " where TN_ENTKEY =:%s and TN_VIEWKEY =:%s ", "tn_entkey", "tn_viewkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_entkey", (Object)entKey);
        parameterSource.addValue("tn_viewkey", (Object)viewkey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query != null ? query : new ArrayList();
    }

    @Override
    public List<TagNodeImpl> countOfEntityDatas(String viewkey, List<String> entkeys) {
        viewkey = TagEntityIDHelper.getEntityId(viewkey);
        List<TagNodeImpl> tgImpls = new ArrayList<TagNodeImpl>(0);
        if (entkeys != null && !entkeys.isEmpty()) {
            if (entkeys.size() <= 500) {
                tgImpls = this.doQueryByEntkeys(viewkey, entkeys);
            } else {
                int pagesize = 500;
                int totalCount = entkeys.size();
                int totalPage = totalCount % pagesize == 0 ? totalCount / pagesize : totalCount / pagesize + 1;
                for (int currentPage = 0; currentPage < totalPage; ++currentPage) {
                    int fromIndex = currentPage * pagesize;
                    int toIndex = fromIndex + pagesize >= entkeys.size() ? entkeys.size() : fromIndex + pagesize;
                    List<String> subList = entkeys.subList(fromIndex, toIndex);
                    List<TagNodeImpl> rs = this.doQueryByEntkeys(viewkey, subList);
                    tgImpls.addAll(rs);
                }
            }
        }
        return tgImpls;
    }

    @Override
    public List<TagNodeImpl> findAllTagNodes() {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(QUERY_TEMPLATE, (rs, row) -> this.create(rs));
        return query.size() > 0 ? query : new ArrayList();
    }

    private List<TagNodeImpl> doQueryByEntkeys(String viewkey, List<String> entkeys) {
        String sql = String.format(QUERY_TEMPLATE + "where TN_VIEWKEY =:%s and TN_ENTKEY in (:%s)", "tn_viewkey", "tn_entkey");
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("tn_entkey", entkeys);
        parameterSource.addValue("tn_viewkey", (Object)viewkey);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        List query = template.query(sql, (SqlParameterSource)parameterSource, (rs, row) -> this.create(rs));
        return query != null ? query : new ArrayList();
    }

    private MapSqlParameterSource[] buildBatchSqlParameterSource(List<TagNodeImpl> impls) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[impls.size()];
        for (int i = 0; i < sources.length; ++i) {
            TagNodeImpl tagNode = impls.get(i);
            sources[i] = this.buildSqlParameterSource(tagNode);
        }
        return sources;
    }

    private MapSqlParameterSource buildSqlParameterSource(TagNodeImpl tagNode) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("tn_tgkey", (Object)tagNode.getTgKey()).addValue("tn_entkey", (Object)tagNode.getEntKey()).addValue("tn_viewkey", (Object)tagNode.getViewkey());
        return source;
    }

    private TagNodeImpl create(ResultSet rs) throws SQLException {
        TagNodeImpl tagNode = new TagNodeImpl();
        tagNode.setTgKey(rs.getString("tn_tgkey"));
        tagNode.setEntKey(rs.getString("tn_entkey"));
        tagNode.setViewkey(rs.getString("tn_viewkey"));
        return tagNode;
    }
}

