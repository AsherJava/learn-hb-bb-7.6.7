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
import com.jiuqi.nr.tag.management.dao.TagDefineDao;
import com.jiuqi.nr.tag.management.entity.ITagDefine;
import com.jiuqi.nr.tag.management.entityimpl.TagDefine;
import com.jiuqi.nr.tag.management.enumeration.TagDefineTableEnum;
import com.jiuqi.nr.tag.management.response.TagManagerShowData;
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
public class TagDefineDaoImpl
implements TagDefineDao {
    private static final String TABLE_NAME = "NR_TAG_DEFINE";
    private static final String[] columns = new String[]{"TD_KEY", "TD_OWNER", "TD_ENTITY", "TD_TITLE", "TD_ICON", "TD_CATEGORY", "TD_FORMULA", "TD_SHARED", "TD_ORDER", "TD_DESC", "TD_RANGE_MODIFY"};
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ITagDefine> queryTagDefineRows(String owner, String unitEntity) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(TagDefineTableEnum.TD_OWNER.column, TagDefineTableEnum.TD_ENTITY.column);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagDefineTableEnum.TD_OWNER.column, (Object)owner);
        source.addValue(TagDefineTableEnum.TD_ENTITY.column, (Object)unitEntity);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public List<ITagDefine> queryAdminTagDefineRows(String unitEntity) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(TagDefineTableEnum.TD_ENTITY.column, TagDefineTableEnum.TD_SHARED.column);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagDefineTableEnum.TD_ENTITY.column, (Object)unitEntity);
        source.addValue(TagDefineTableEnum.TD_SHARED.column, (Object)1);
        return this.executeQuery(sqlBuilder.toString(), source);
    }

    @Override
    public ITagDefine queryTagDefineRowByKey(String tagKey) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.selectSQL(columns).andWhere(TagDefineTableEnum.TD_KEY.column);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagDefineTableEnum.TD_KEY.column, (Object)tagKey);
        List<ITagDefine> tagDefines = this.executeQuery(sqlBuilder.toString(), source);
        return tagDefines.isEmpty() ? null : tagDefines.get(0);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int insertTagDefineRow(ITagDefine tagDefine) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.insertSQL(columns);
        MapSqlParameterSource source = this.buildUpdateMapSource(tagDefine);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    public int updateTagDefineFormula(String tagKey, String formula) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(TagDefineTableEnum.TD_FORMULA.column).andWhere(TagDefineTableEnum.TD_KEY.column);
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagDefineTableEnum.TD_FORMULA.column, (Object)formula);
        source.addValue(TagDefineTableEnum.TD_KEY.column, (Object)tagKey);
        return this.executeUpdate(sqlBuilder.toString(), source);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] deleteTagDefineRow(String ... tagKeys) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.deleteSQL().andWhere(TagDefineTableEnum.TD_KEY.column);
        MapSqlParameterSource[] batchDeleteMapSource = new MapSqlParameterSource[tagKeys.length];
        for (int i = 0; i < tagKeys.length; ++i) {
            String tagKey = tagKeys[i];
            batchDeleteMapSource[i] = new MapSqlParameterSource();
            batchDeleteMapSource[i].addValue(TagDefineTableEnum.TD_KEY.column, (Object)tagKey);
        }
        return this.executeBatchOperation(sqlBuilder.toString(), batchDeleteMapSource);
    }

    @Override
    public int[] updateTagDefineRow(List<TagManagerShowData> tagDataRows) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder(TABLE_NAME);
        sqlBuilder.updateSQL(TagDefineTableEnum.TD_TITLE.column, TagDefineTableEnum.TD_CATEGORY.column, TagDefineTableEnum.TD_ICON.column, TagDefineTableEnum.TD_ORDER.column, TagDefineTableEnum.TD_DESC.column, TagDefineTableEnum.TD_RANGE_MODIFY.column).andWhere(TagDefineTableEnum.TD_KEY.column);
        MapSqlParameterSource[] batchUpdateMapSource = new MapSqlParameterSource[tagDataRows.size()];
        for (int i = 0; i < tagDataRows.size(); ++i) {
            TagManagerShowData tagDataRow = tagDataRows.get(i);
            batchUpdateMapSource[i] = new MapSqlParameterSource();
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_TITLE.column, (Object)tagDataRow.getTitle());
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_CATEGORY.column, (Object)tagDataRow.getCategory());
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_ICON.column, (Object)tagDataRow.getIcon());
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_ORDER.column, (Object)tagDataRow.getOrder());
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_DESC.column, (Object)tagDataRow.getDescription());
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_RANGE_MODIFY.column, (Object)(tagDataRow.isRangeModify() ? 1 : 0));
            batchUpdateMapSource[i].addValue(TagDefineTableEnum.TD_KEY.column, (Object)tagDataRow.getKey());
        }
        return this.executeBatchOperation(sqlBuilder.toString(), batchUpdateMapSource);
    }

    private List<ITagDefine> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, this::readTagDefine);
    }

    private int[] executeBatchOperation(String sql, MapSqlParameterSource[] source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(sql, (SqlParameterSource[])source);
    }

    private int executeUpdate(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(sql, (SqlParameterSource)source);
    }

    private MapSqlParameterSource buildUpdateMapSource(ITagDefine tag) {
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue(TagDefineTableEnum.TD_KEY.column, (Object)tag.getKey());
        source.addValue(TagDefineTableEnum.TD_OWNER.column, (Object)tag.getOwner());
        source.addValue(TagDefineTableEnum.TD_ENTITY.column, (Object)tag.getEntity());
        source.addValue(TagDefineTableEnum.TD_TITLE.column, (Object)tag.getTitle());
        source.addValue(TagDefineTableEnum.TD_ICON.column, (Object)tag.getIcon());
        source.addValue(TagDefineTableEnum.TD_CATEGORY.column, (Object)tag.getCategory());
        source.addValue(TagDefineTableEnum.TD_FORMULA.column, (Object)tag.getFormula());
        source.addValue(TagDefineTableEnum.TD_SHARED.column, (Object)(tag.getShared() ? 1 : 0));
        source.addValue(TagDefineTableEnum.TD_ORDER.column, (Object)tag.getOrder());
        source.addValue(TagDefineTableEnum.TD_DESC.column, (Object)tag.getDescription());
        source.addValue(TagDefineTableEnum.TD_RANGE_MODIFY.column, (Object)(tag.getRangeModify() ? 1 : 0));
        return source;
    }

    private TagDefine readTagDefine(ResultSet rs, int rowIdx) throws SQLException {
        TagDefine rowData = new TagDefine();
        rowData.setKey(rs.getString(TagDefineTableEnum.TD_KEY.column));
        rowData.setOwner(rs.getString(TagDefineTableEnum.TD_OWNER.column));
        rowData.setEntity(rs.getString(TagDefineTableEnum.TD_ENTITY.column));
        rowData.setTitle(rs.getString(TagDefineTableEnum.TD_TITLE.column));
        rowData.setIcon(rs.getString(TagDefineTableEnum.TD_ICON.column));
        rowData.setCategory(rs.getString(TagDefineTableEnum.TD_CATEGORY.column));
        rowData.setFormula(rs.getString(TagDefineTableEnum.TD_FORMULA.column));
        rowData.setShared(rs.getInt(TagDefineTableEnum.TD_SHARED.column) == 1);
        rowData.setOrder(rs.getString(TagDefineTableEnum.TD_ORDER.column));
        rowData.setDescription(rs.getString(TagDefineTableEnum.TD_DESC.column));
        rowData.setRangeModify(rs.getInt(TagDefineTableEnum.TD_RANGE_MODIFY.column) == 1);
        return rowData;
    }
}

