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
 *  org.jetbrains.annotations.NotNull
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
import com.jiuqi.nr.workflow2.form.reject.entity.RejectFormRecordEntity;
import com.jiuqi.nr.workflow2.form.reject.entity.dao.IRejectFormRecordDao;
import com.jiuqi.nr.workflow2.form.reject.enumeration.FormRejectStatus;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTableModelDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RejectFormRecordDao
implements IRejectFormRecordDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int[] insertRows(FRStatusTableModelDefine tableModelDefine, @NotNull List<IFormObject> formObjects, @NotNull String status) {
        if (formObjects.isEmpty()) {
            return new int[0];
        }
        String insertedSQL = this.insertSQL(tableModelDefine);
        MapSqlParameterSource[] sourceMap = this.buildParameterSource(tableModelDefine, formObjects, status);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.batchUpdate(insertedSQL, (SqlParameterSource[])sourceMap);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int updateRows(FRStatusTableModelDefine tableModelDefine, @NotNull DimensionCombination combination, @NotNull List<String> formIds, @NotNull String status) {
        if (formIds.isEmpty()) {
            return 0;
        }
        StringBuilder updateSQL = new StringBuilder();
        updateSQL.append(" UPDATE ").append(tableModelDefine.getName());
        updateSQL.append(" SET ");
        ColumnModelDefine statusColumnDefine = tableModelDefine.getStatusColumnDefine();
        updateSQL.append(statusColumnDefine.getName()).append("=:").append(statusColumnDefine.getName());
        updateSQL.append(" WHERE ");
        List<ColumnModelDefine> bizKeyColumnDefinesExceptForm = tableModelDefine.getBizKeyColumnDefines();
        List bizKeyColumnNames = bizKeyColumnDefinesExceptForm.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        updateSQL.append(bizKeyColumnNames.stream().map(e -> e + "=:" + e).collect(Collectors.joining(" AND ")));
        ColumnModelDefine formColumnDefine = tableModelDefine.getFormColumnDefine();
        updateSQL.append(" AND ").append(formColumnDefine.getName()).append(" IN (:").append(formColumnDefine.getName()).append(")");
        MapSqlParameterSource sourceMap = this.buildParameterSource(tableModelDefine, combination);
        sourceMap.addValue(formColumnDefine.getName(), formIds);
        sourceMap.addValue(statusColumnDefine.getName(), (Object)status);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(updateSQL.toString(), (SqlParameterSource)sourceMap);
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public int deleteRows(FRStatusTableModelDefine tableModelDefine, DimensionCombination combination) {
        List<ColumnModelDefine> bizKeyColumnDefines = tableModelDefine.getCombinationColumnDefines();
        String deleteSQL = this.deleteSQL(tableModelDefine.getName(), bizKeyColumnDefines);
        MapSqlParameterSource sourceMap = this.buildParameterSource(tableModelDefine, combination);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.update(deleteSQL, (SqlParameterSource)sourceMap);
    }

    @Override
    public List<RejectFormRecordEntity> queryRows(FRStatusTableModelDefine tableModelDefine, DimensionCombination combination) {
        List<ColumnModelDefine> selectColumnDefines = tableModelDefine.getAllColumnDefines();
        List<ColumnModelDefine> bizKeyColumnDefines = tableModelDefine.getCombinationColumnDefines();
        String selectSQL = this.selectSQL(tableModelDefine.getName(), selectColumnDefines, bizKeyColumnDefines);
        MapSqlParameterSource sourceMap = this.buildParameterSource(tableModelDefine, combination);
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(selectSQL, (SqlParameterSource)sourceMap, (rs, rowIdx) -> this.buildRecordData(tableModelDefine, rs, rowIdx));
    }

    private String insertSQL(FRStatusTableModelDefine tableModelDefine) {
        List<ColumnModelDefine> allColumnDefines = tableModelDefine.getAllColumnDefines();
        List valueColumnNames = allColumnDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
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

    protected String deleteSQL(String tableName, List<ColumnModelDefine> bizKeyColumnDefines) {
        StringBuilder sql = new StringBuilder();
        sql.append(" DELETE FROM ");
        sql.append(tableName);
        sql.append(" WHERE ");
        List bizKeyColumnNames = bizKeyColumnDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        sql.append(bizKeyColumnNames.stream().map(e -> e + "=:" + e).collect(Collectors.joining(" AND ")));
        return sql.toString();
    }

    protected String selectSQL(String tableName, List<ColumnModelDefine> selectColumnDefines, List<ColumnModelDefine> whereColumnDefines) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        List selectColumnNames = selectColumnDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        sql.append(String.join((CharSequence)",", selectColumnNames));
        sql.append(" FROM ").append(tableName);
        sql.append(" WHERE ");
        List whereColumnNames = whereColumnDefines.stream().map(ColumnModelDefine::getName).collect(Collectors.toList());
        sql.append(whereColumnNames.stream().map(e -> e + "=:" + e).collect(Collectors.joining(" AND ")));
        return sql.toString();
    }

    protected RejectFormRecordEntity buildRecordData(FRStatusTableModelDefine tableModelDefine, ResultSet rs, int rowIdx) throws SQLException {
        RejectFormRecordEntity entity = new RejectFormRecordEntity();
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        List<ColumnModelDefine> bizKeyColumnDefinesExceptForm = tableModelDefine.getCombinationColumnDefines();
        for (ColumnModelDefine columnModelDefine : bizKeyColumnDefinesExceptForm) {
            String columnName = columnModelDefine.getName();
            if (tableModelDefine.getStatusTable().getUnitColumnCode().equals(columnModelDefine.getCode())) {
                combination.setDWValue(columnName, columnModelDefine.getReferTableID(), (Object)rs.getString(columnName));
                continue;
            }
            combination.setValue(columnName, columnModelDefine.getReferTableID(), (Object)rs.getString(columnName));
        }
        ColumnModelDefine formColumnDefine = tableModelDefine.getFormColumnDefine();
        entity.setFormObject((IFormObject)new FormObject((DimensionCombination)combination, rs.getString(formColumnDefine.getName())));
        ColumnModelDefine statusColumnDefine = tableModelDefine.getStatusColumnDefine();
        entity.setStatus(FormRejectStatus.fromValue(rs.getString(statusColumnDefine.getName())));
        return entity;
    }

    protected MapSqlParameterSource buildParameterSource(FRStatusTableModelDefine tableModelDefine, DimensionCombination combination) {
        List<ColumnModelDefine> bizKeyColumnDefines = tableModelDefine.getCombinationColumnDefines();
        MapSqlParameterSource sourceMap = new MapSqlParameterSource();
        for (ColumnModelDefine columnModelDefine : bizKeyColumnDefines) {
            sourceMap.addValue(columnModelDefine.getName(), combination.getValue(columnModelDefine.getName()));
        }
        return sourceMap;
    }

    protected MapSqlParameterSource[] buildParameterSource(FRStatusTableModelDefine tableModelDefine, List<IFormObject> formObjects, String status) {
        MapSqlParameterSource[] sources = new MapSqlParameterSource[formObjects.size()];
        for (int i = 0; i < formObjects.size(); ++i) {
            IFormObject formObject = formObjects.get(i);
            sources[i] = this.buildParameterSource(tableModelDefine, formObject, status);
        }
        return sources;
    }

    protected MapSqlParameterSource buildParameterSource(FRStatusTableModelDefine tableModelDefine, IFormObject formObject, String status) {
        ColumnModelDefine formColumnDefine = tableModelDefine.getFormColumnDefine();
        List<ColumnModelDefine> bizKeyColumnDefines = tableModelDefine.getBizKeyColumnDefines();
        MapSqlParameterSource sourceMap = new MapSqlParameterSource();
        for (ColumnModelDefine columnModelDefine : bizKeyColumnDefines) {
            if (formColumnDefine.getID().equals(columnModelDefine.getID())) {
                sourceMap.addValue(columnModelDefine.getName(), (Object)formObject.getFormKey());
                continue;
            }
            sourceMap.addValue(columnModelDefine.getName(), formObject.getDimensions().getValue(columnModelDefine.getName()));
        }
        sourceMap.addValue(tableModelDefine.getStatusColumnDefine().getName(), (Object)status);
        return sourceMap;
    }
}

