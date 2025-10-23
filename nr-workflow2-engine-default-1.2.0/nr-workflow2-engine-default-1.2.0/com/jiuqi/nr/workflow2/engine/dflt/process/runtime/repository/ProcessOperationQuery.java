/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository;

import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.QueryModel;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.repository.ProcessOperationDO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ProcessOperationQuery {
    private final JdbcTemplate jdbcTemplate;
    private final QueryModel.ProcessOperationQueryModel optQueryModel;

    public ProcessOperationQuery(JdbcTemplate jdbcTemplate, QueryModel.ProcessOperationQueryModel optQueryModel) {
        this.jdbcTemplate = jdbcTemplate;
        this.optQueryModel = optQueryModel;
    }

    public QueryModel.ProcessOperationQueryModel getQueryModel() {
        return this.optQueryModel;
    }

    private String makeSQLForInsertOperation() {
        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ").append(this.optQueryModel.getTableName()).append(" (").append(this.optQueryModel.getIdColumn().getName()).append(", ").append(this.optQueryModel.getIstIdColumn().getName()).append(", ").append(this.optQueryModel.getFromNodeColumn().getName()).append(", ").append(this.optQueryModel.getActionColumn().getName()).append(", ").append(this.optQueryModel.getToNodeColumn().getName()).append(", ").append(this.optQueryModel.getNewStatusColumn().getName()).append(", ").append(this.optQueryModel.getOperateTimeColumn().getName()).append(", ").append(this.optQueryModel.getOperateUserColumn().getName()).append(", ").append(this.optQueryModel.getOperateIdentityColumn().getName()).append(", ").append(this.optQueryModel.getCommentColumn().getName()).append(", ").append(this.optQueryModel.getOperateTypeColumn().getName()).append(", ").append(this.optQueryModel.getForceReportColumn().getName());
        sqlBuilder.append(") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        return sqlBuilder.toString();
    }

    public void insertOperation(ProcessOperationDO operation) {
        Object[] args = new Object[12];
        int fi = 0;
        args[fi++] = operation.getId();
        args[fi++] = operation.getInstanceId();
        args[fi++] = operation.getFromNode();
        args[fi++] = operation.getAction();
        args[fi++] = operation.getToNode();
        args[fi++] = operation.getNewStatus();
        args[fi++] = operation.getOperateTime();
        args[fi++] = operation.getOperate_user();
        args[fi++] = operation.getOperate_identity();
        args[fi++] = operation.getComment();
        args[fi++] = operation.getOperate_type();
        args[fi] = operation.isForceReport() ? 1 : 0;
        this.jdbcTemplate.update(this.makeSQLForInsertOperation(), args);
    }

    public void insertOperations(List<ProcessOperationDO> operations) {
        if (operations.isEmpty()) {
            return;
        }
        ArrayList<Object[]> batchArgs = new ArrayList<Object[]>(operations.size());
        for (ProcessOperationDO operation : operations) {
            Object[] args = new Object[12];
            int fi = 0;
            args[fi++] = operation.getId();
            args[fi++] = operation.getInstanceId();
            args[fi++] = operation.getFromNode();
            args[fi++] = operation.getAction();
            args[fi++] = operation.getToNode();
            args[fi++] = operation.getNewStatus();
            args[fi++] = operation.getOperateTime();
            args[fi++] = operation.getOperate_user();
            args[fi++] = operation.getOperate_identity();
            args[fi++] = operation.getComment();
            args[fi++] = operation.getOperate_type();
            args[fi] = operation.isForceReport() ? 1 : 0;
            batchArgs.add(args);
        }
        this.jdbcTemplate.batchUpdate(this.makeSQLForInsertOperation(), batchArgs);
    }

    private String makeSQLForDeleteOperation() {
        StringBuilder sqlBuilder = new StringBuilder("DELETE FROM ").append(this.optQueryModel.getTableName()).append(" WHERE ").append(this.optQueryModel.getIstIdColumn().getName()).append(" IN(:").append(this.optQueryModel.getIstIdColumn().getName()).append(")");
        return sqlBuilder.toString();
    }

    public void deleteOperation(String instanceId) {
        this.deleteOperations(Arrays.asList(instanceId));
    }

    public void deleteOperations(Collection<String> instanceIds) {
        if (instanceIds.isEmpty()) {
            return;
        }
        HashMap<String, Collection<String>> args = new HashMap<String, Collection<String>>(1);
        args.put(this.optQueryModel.getIstIdColumn().getName(), instanceIds);
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
        namedParameterJdbcTemplate.update(this.makeSQLForDeleteOperation(), args);
    }

    private String makeSQLForQueryOperation() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(this.optQueryModel.getIdColumn().getName()).append(", ").append(this.optQueryModel.getIstIdColumn().getName()).append(", ").append(this.optQueryModel.getFromNodeColumn().getName()).append(", ").append(this.optQueryModel.getActionColumn().getName()).append(", ").append(this.optQueryModel.getToNodeColumn().getName()).append(", ").append(this.optQueryModel.getNewStatusColumn().getName()).append(", ").append(this.optQueryModel.getOperateTimeColumn().getName()).append(", ").append(this.optQueryModel.getOperateUserColumn().getName()).append(", ").append(this.optQueryModel.getOperateIdentityColumn().getName()).append(", ").append(this.optQueryModel.getCommentColumn().getName()).append(", ").append(this.optQueryModel.getOperateTypeColumn().getName()).append(", ").append(this.optQueryModel.getForceReportColumn().getName()).append(" FROM ").append(this.optQueryModel.getTableName());
        sqlBuilder.append(" WHERE ").append(this.optQueryModel.getIstIdColumn().getName()).append("=?");
        sqlBuilder.append(" ORDER BY ").append(this.optQueryModel.getOperateTimeColumn().getName());
        return sqlBuilder.toString();
    }

    public List<ProcessOperationDO> queryOperations(String instanceId) {
        Object[] args = new Object[]{instanceId};
        return (List)this.jdbcTemplate.query(this.makeSQLForQueryOperation(), rs -> {
            ArrayList<ProcessOperationDO> result = new ArrayList<ProcessOperationDO>();
            while (rs.next()) {
                int fi = 1;
                ProcessOperationDO operation = new ProcessOperationDO();
                operation.setId(rs.getString(fi++));
                operation.setInstanceId(rs.getString(fi++));
                operation.setFromNode(rs.getString(fi++));
                operation.setAction(rs.getString(fi++));
                operation.setToNode(rs.getString(fi++));
                operation.setNewStatus(rs.getString(fi++));
                Calendar operateTime = Calendar.getInstance();
                operateTime.setTime(rs.getTimestamp(fi++));
                operation.setOperateTime(operateTime);
                operation.setOperate_user(rs.getString(fi++));
                operation.setOperate_identity(rs.getString(fi++));
                operation.setComment(rs.getString(fi++));
                operation.setOperate_type(rs.getString(fi++));
                operation.setForceReport(rs.getInt(fi++) == 1);
                result.add(operation);
            }
            return result;
        }, args);
    }

    public ProcessOperationDO queryLastOperation(String instanceId) {
        Object[] args = new Object[]{instanceId};
        return (ProcessOperationDO)this.jdbcTemplate.query(this.makeSQLForQueryOperation(), rs -> {
            if (rs.next()) {
                int fi = 1;
                ProcessOperationDO operation = new ProcessOperationDO();
                operation.setId(rs.getString(fi++));
                operation.setInstanceId(rs.getString(fi++));
                operation.setFromNode(rs.getString(fi++));
                operation.setAction(rs.getString(fi++));
                operation.setToNode(rs.getString(fi++));
                operation.setNewStatus(rs.getString(fi++));
                Calendar operateTime = Calendar.getInstance();
                operateTime.setTime(rs.getTimestamp(fi++));
                operation.setOperateTime(operateTime);
                operation.setOperate_user(rs.getString(fi++));
                operation.setOperate_identity(rs.getString(fi++));
                operation.setComment(rs.getString(fi++));
                operation.setOperate_type(rs.getString(fi++));
                operation.setForceReport(rs.getInt(fi++) == 1);
                return operation;
            }
            return null;
        }, args);
    }

    public void deleteAll() {
        String sql = "DELETE FROM " + this.optQueryModel.getTableName();
        this.jdbcTemplate.execute(sql);
    }
}

