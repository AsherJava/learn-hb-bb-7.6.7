/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.tag.management.daoimpl;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.tag.management.dao.TagMappingDao;
import com.jiuqi.nr.tag.management.entity.ITagMapping;
import com.jiuqi.nr.tag.management.entityimpl.TagCount;
import com.jiuqi.nr.tag.management.entityimpl.TagMapping;
import com.jiuqi.nr.tag.management.enumeration.TagMappingTableEnum;
import com.jiuqi.nr.tag.management.util.NamedParameterSqlBuilder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TagMappingDaoImpl
implements TagMappingDao {
    private static final String TABLE_NAME = "NR_TAG_MAPPING";
    private static final String COUNT_COLUMN = "count_total_number";
    private static final String[] columns = new String[]{"TM_TAGKEY", "TM_ENTITYDATA", "TM_ORDER"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ITagMapping> queryTagMappingRowsByTagKey(String tagKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(TagMappingTableEnum.TM_TAGKEY.column);
        sqlBuilder.orderBy(TagMappingTableEnum.TM_ORDER.column);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagMappingTableEnum.TM_TAGKEY.column, (Object)tagKey);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<ITagMapping> queryTagMappingRowsByEntityData(String entityData) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(TagMappingTableEnum.TM_ENTITYDATA.column);
        sqlBuilder.orderBy(TagMappingTableEnum.TM_ORDER.column);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagMappingTableEnum.TM_ENTITYDATA.column, (Object)entityData);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] insertTagMappingRows(List<ITagMapping> tagMappings) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource[] batchMapSource = this.buildBatchMapSource(tagMappings);
        return this.executeBatchUpdate(sqlBuilder.toString(), batchMapSource);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] deleteTagMappingRowByTagKeys(String ... tagKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(TagMappingTableEnum.TM_TAGKEY.column);
        MapSqlParameterSource[] batchDeleteMapSource = new MapSqlParameterSource[tagKeys.length];
        for (int i = 0; i < tagKeys.length; ++i) {
            String tagKey = tagKeys[i];
            batchDeleteMapSource[i] = new MapSqlParameterSource();
            batchDeleteMapSource[i].addValue(TagMappingTableEnum.TM_TAGKEY.column, (Object)tagKey);
        }
        return this.executeBatchUpdate(sqlBuilder.toString(), batchDeleteMapSource);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] deleteTagMappingRowByEntityData(String ... entityDatas) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(TagMappingTableEnum.TM_ENTITYDATA.column);
        MapSqlParameterSource[] batchDeleteMapSource = new MapSqlParameterSource[entityDatas.length];
        for (int i = 0; i < entityDatas.length; ++i) {
            String entityData = entityDatas[i];
            batchDeleteMapSource[i] = new MapSqlParameterSource();
            batchDeleteMapSource[i].addValue(TagMappingTableEnum.TM_ENTITYDATA.column, (Object)entityData);
        }
        return this.executeBatchUpdate(sqlBuilder.toString(), batchDeleteMapSource);
    }

    @Override
    public List<TagCount> countTagsUnits(List<String> tagKeys) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        MapSqlParameterSource source = new MapSqlParameterSource();
        sqlBuilder.countSQL(COUNT_COLUMN, TagMappingTableEnum.TM_TAGKEY.column).inWhere(TagMappingTableEnum.TM_TAGKEY.column).groupBy(TagMappingTableEnum.TM_TAGKEY.column);
        source.addValue(TagMappingTableEnum.TM_TAGKEY.column, tagKeys);
        return template.query(sqlBuilder.toString(), (SqlParameterSource)source, this::readTagCountMap);
    }

    private List<ITagMapping> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readTagMapping);
    }

    private int[] executeBatchUpdate(String sql, MapSqlParameterSource[] sources) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])sources);
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private MapSqlParameterSource[] buildBatchMapSource(List<ITagMapping> rows) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[rows.size()];
        for (int i = 0; i < rows.size(); ++i) {
            ITagMapping row = rows.get(i);
            sources[i] = new MapSqlParameterSource();
            sources[i].addValue(TagMappingTableEnum.TM_TAGKEY.column, (Object)row.getTagKey());
            sources[i].addValue(TagMappingTableEnum.TM_ENTITYDATA.column, (Object)row.getEntityData());
            sources[i].addValue(TagMappingTableEnum.TM_ORDER.column, (Object)row.getOrder());
        }
        return sources;
    }

    private TagMapping readTagMapping(ResultSet rs, int rowIdx) throws SQLException {
        return new TagMapping(rs.getString(TagMappingTableEnum.TM_TAGKEY.column), rs.getString(TagMappingTableEnum.TM_ENTITYDATA.column), rs.getString(TagMappingTableEnum.TM_ORDER.column));
    }

    private TagCount readTagCountMap(ResultSet rs, int rowIdx) throws SQLException {
        return new TagCount(rs.getString(TagMappingTableEnum.TM_TAGKEY.column), rs.getInt(COUNT_COLUMN));
    }
}

