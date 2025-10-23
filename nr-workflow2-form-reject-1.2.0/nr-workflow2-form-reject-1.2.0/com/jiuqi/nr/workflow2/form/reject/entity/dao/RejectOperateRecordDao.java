/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.workflow2.form.reject.entity.dao;

import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IFormObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.nr.workflow2.form.reject.entity.IRejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.RejectOperateRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.IRejectOperateRecordDao;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTableModelDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RejectOperateRecordDao
implements IRejectOperateRecordDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] insertRows(FROperateTableModelDefine tableModelDefine, List<RejectOperateRecordEntity> entities) {
        String insertedSQL = this.insertSQL(tableModelDefine);
        MapSqlParameterSource[] sourceMap = this.buildParameterSource(tableModelDefine, entities);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(insertedSQL, (SqlParameterSource[])sourceMap);
    }

    @Override
    public List<IRejectOperateRecordEntity> queryRows(FROperateTableModelDefine tableModelDefine, DimensionCombination combination) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        List selectColNames = tableModelDefine.getAllColumnDefines().stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        sql.append(String.join((CharSequence)",", selectColNames));
        sql.append(" FROM ").append(tableModelDefine.getName());
        sql.append(" WHERE ");
        List whereColumnNames = tableModelDefine.getCombinationColumnDefines().stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        sql.append(whereColumnNames.stream().map(e -> e + "=:" + e).collect(Collectors.joining(" AND ")));
        sql.append(" ORDER BY ").append(tableModelDefine.getOptTimeColumnDefine().getName()).append(" DESC");
        MapSqlParameterSource sourceMap = this.buildParameterSource(tableModelDefine, combination);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql.toString(), (SqlParameterSource)sourceMap, (rs, rowIdx) -> this.buildRecordData(tableModelDefine, rs, rowIdx));
    }

    protected MapSqlParameterSource[] buildParameterSource(FROperateTableModelDefine tableModelDefine, List<RejectOperateRecordEntity> entities) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[entities.size()];
        for (int i = 0; i < entities.size(); ++i) {
            RejectOperateRecordEntity entity = entities.get(i);
            sources[i] = this.buildParameterSource(tableModelDefine, entity);
        }
        return sources;
    }

    protected MapSqlParameterSource buildParameterSource(FROperateTableModelDefine tableModelDefine, RejectOperateRecordEntity entity) {
        MapSqlParameterSource sourceMap = this.buildParameterSource(tableModelDefine, entity.getFormObject());
        ColumnModelDefine optIdColumnDefine = tableModelDefine.getOptIdColumnDefine();
        sourceMap.addValue(optIdColumnDefine.getName(), (Object)entity.getOptId());
        ColumnModelDefine optUserColumnDefine = tableModelDefine.getOptUserColumnDefine();
        sourceMap.addValue(optUserColumnDefine.getName(), (Object)entity.getOptUser());
        ColumnModelDefine optTimeColumnDefine = tableModelDefine.getOptTimeColumnDefine();
        sourceMap.addValue(optTimeColumnDefine.getName(), (Object)entity.getOptTime());
        ColumnModelDefine optCommentColumnDefine = tableModelDefine.getOptCommentColumnDefine();
        sourceMap.addValue(optCommentColumnDefine.getName(), (Object)entity.getOptComment());
        return sourceMap;
    }

    protected MapSqlParameterSource buildParameterSource(FROperateTableModelDefine tableModelDefine, IFormObject formObject) {
        List<ColumnModelDefine> combinationColumnDefines = tableModelDefine.getCombinationColumnDefines();
        DimensionCombination combination = formObject.getDimensions();
        MapSqlParameterSource sourceMap = new MapSqlParameterSource();
        for (ColumnModelDefine columnModelDefine : combinationColumnDefines) {
            sourceMap.addValue(columnModelDefine.getName(), combination.getValue(columnModelDefine.getName()));
        }
        ColumnModelDefine formIdColumnDefine = tableModelDefine.getFormIdColumnDefine();
        sourceMap.addValue(formIdColumnDefine.getName(), (Object)formObject.getFormKey());
        return sourceMap;
    }

    protected MapSqlParameterSource buildParameterSource(FROperateTableModelDefine tableModelDefine, DimensionCombination combination) {
        List<ColumnModelDefine> combinationColumnDefines = tableModelDefine.getCombinationColumnDefines();
        MapSqlParameterSource sourceMap = new MapSqlParameterSource();
        for (ColumnModelDefine columnModelDefine : combinationColumnDefines) {
            sourceMap.addValue(columnModelDefine.getName(), combination.getValue(columnModelDefine.getName()));
        }
        return sourceMap;
    }

    protected String insertSQL(FROperateTableModelDefine tableModelDefine) {
        List<ColumnModelDefine> valueColumnDefines = tableModelDefine.getAllColumnDefines();
        List valueColumnNames = valueColumnDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        StringBuilder sql = new StringBuilder();
        sql.append(" INSERT INTO ");
        sql.append(tableModelDefine.getName()).append(" ( ");
        sql.append(String.join((CharSequence)",", valueColumnNames));
        sql.append(" ) ");
        sql.append(" VALUES ");
        sql.append(" ( ");
        sql.append(valueColumnNames.stream().map(e -> ":" + e).collect(Collectors.joining(",")));
        sql.append(" ) ");
        return sql.toString();
    }

    protected IRejectOperateRecordEntity buildRecordData(FROperateTableModelDefine tableModelDefine, ResultSet rs, int rowIdx) throws SQLException {
        RejectOperateRecordEntity operateRecordEntity = new RejectOperateRecordEntity();
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        List<ColumnModelDefine> bizKeyColumnDefinesExceptForm = tableModelDefine.getCombinationColumnDefines();
        for (ColumnModelDefine columnModelDefine : bizKeyColumnDefinesExceptForm) {
            String columnName = columnModelDefine.getName();
            if (tableModelDefine.getOperateTable().getUnitColumnCode().equals(columnModelDefine.getCode())) {
                combination.setDWValue(columnName, columnModelDefine.getReferTableID(), (Object)rs.getString(columnName));
                continue;
            }
            combination.setValue(columnName, columnModelDefine.getReferTableID(), (Object)rs.getString(columnName));
        }
        ColumnModelDefine formColumnDefine = tableModelDefine.getFormIdColumnDefine();
        operateRecordEntity.setFormObject((IFormObject)new FormObject((DimensionCombination)combination, rs.getString(formColumnDefine.getName())));
        ColumnModelDefine optUserColumnDefine = tableModelDefine.getOptUserColumnDefine();
        operateRecordEntity.setOptUser(rs.getString(optUserColumnDefine.getName()));
        ColumnModelDefine optTimeColumnDefine = tableModelDefine.getOptTimeColumnDefine();
        operateRecordEntity.setOptTime(rs.getTimestamp(optTimeColumnDefine.getName()));
        ColumnModelDefine optCommentColumnDefine = tableModelDefine.getOptCommentColumnDefine();
        operateRecordEntity.setOptComment(rs.getString(optCommentColumnDefine.getName()));
        ColumnModelDefine optIdColumnDefine = tableModelDefine.getOptIdColumnDefine();
        operateRecordEntity.setOptId(rs.getString(optIdColumnDefine.getName()));
        return operateRecordEntity;
    }
}

