/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  javax.annotation.Resource
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.workflow2.todo.daoimpl;

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.todo.dao.TodoMultiplePeriodDao;
import com.jiuqi.nr.workflow2.todo.dto.TodoQueryDTO;
import com.jiuqi.nr.workflow2.todo.entity.TodoTask;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder;
import com.jiuqi.nr.workflow2.todo.utils.TodoTaskCommentRowMapper;
import com.jiuqi.nr.workflow2.todo.utils.TodoTaskRowMapper;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class TodoMultiplePeriodDaoImpl
implements TodoMultiplePeriodDao {
    public static final String TODO_TAB_COUNT_COLUMN = "TODO_TAB_QUANTITY";
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private TodoExtendInterface todoExtend;
    @Resource
    private TodoUtil todoUtil;
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    @Override
    public int getFilterTaskTodoQuantity(TodoQueryDTO todoQueryDTO) {
        WorkFlowStateTableModel stateTableModel = todoQueryDTO.getStateTableModel();
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        if (todoQueryDTO.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
            sqlBuilder.selectJoinOn(new String[]{" COUNT(*) AS TODO_TAB_QUANTITY"}, stateTableModel.getTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        } else {
            sqlBuilder.selectJoinOn(new String[]{" COUNT(*) AS TODO_TAB_QUANTITY"}, stateTableModel.getHistoryTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        }
        this.buildCondition(sqlBuilder, source, todoQueryDTO, stateTableModel);
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    private int executeQuantityQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (Integer)Objects.requireNonNull(template.queryForObject(sql, (SqlParameterSource)source, Integer.class));
    }

    @Override
    public List<TodoTaskImpl> getTodoTask(TodoQueryDTO todoQueryDTO) {
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(todoQueryDTO.getFormSchemeKey());
        boolean isAdjust = this.todoUtil.isAdjust(todoQueryDTO.getFormSchemeKey());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        String[] selectColumns = isAdjust ? new String[]{stateTableModel.getPeriodColumn(), stateTableModel.getUnitColumn(), "TASKID", stateTableModel.getWorkFlowObjectColumn(), "PROCESSID", stateTableModel.getUploadStateColumn(), "RECEIVETIME", stateTableModel.getAdjustColumn(), "REMARK"} : new String[]{stateTableModel.getPeriodColumn(), stateTableModel.getUnitColumn(), "TASKID", stateTableModel.getWorkFlowObjectColumn(), "PROCESSID", stateTableModel.getUploadStateColumn(), "RECEIVETIME", "REMARK"};
        sqlBuilder.selectJoinOn(selectColumns, stateTableModel.getTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        this.buildCondition(sqlBuilder, source, todoQueryDTO, stateTableModel);
        TodoTaskRowMapper todoTaskRowMapper = new TodoTaskRowMapper(stateTableModel, isAdjust, this.todoExtend.isReportDimensionEnable(todoQueryDTO.getTaskId()));
        return this.executeQuery(sqlBuilder.toString(), source, todoTaskRowMapper);
    }

    @Override
    public List<TodoTaskImpl> getTodoTaskComment(TodoQueryDTO todoQueryDTO, List<TodoTaskImpl> todoTasks) {
        WorkFlowStateTableModel stateTableModel = todoQueryDTO.getStateTableModel();
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        String[] selectColumns = new String[]{"PROCESSID", stateTableModel.getCommentColumn(), stateTableModel.getTimeColumn()};
        sqlBuilder.selectJoinOn(selectColumns, stateTableModel.getHistoryTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        if (todoTasks != null && !todoTasks.isEmpty()) {
            sqlBuilder.where().inColumn("PROCESSID");
            source.addValue("PROCESSID", todoTasks.stream().map(TodoTask::getWorkflowInstance).collect(Collectors.toList()));
        }
        TodoTaskCommentRowMapper todoTaskCommentRowMapper = new TodoTaskCommentRowMapper(stateTableModel);
        return this.executeCommentQuery(sqlBuilder.toString(), source, todoTaskCommentRowMapper);
    }

    @Override
    public List<TodoTaskImpl> getHistoryTodoTask(TodoQueryDTO todoQueryDTO) {
        WorkFlowStateTableModel stateTableModel = todoQueryDTO.getStateTableModel();
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK_HISTORY");
        MapSqlParameterSource source = new MapSqlParameterSource();
        String[] selectColumns = new String[]{stateTableModel.getPeriodColumn(), stateTableModel.getUnitColumn(), "TASKID", stateTableModel.getWorkFlowObjectColumn(), "PROCESSID", stateTableModel.getCurrentActionColumn(), "REMARK", stateTableModel.getCommentColumn()};
        sqlBuilder.selectJoinOn(selectColumns, stateTableModel.getHistoryTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        this.buildCondition(sqlBuilder, source, todoQueryDTO, stateTableModel);
        TodoTaskRowMapper todoTaskRowMapper = new TodoTaskRowMapper(stateTableModel, false, this.todoExtend.isReportDimensionEnable(todoQueryDTO.getTaskId()));
        return this.executeQuery(sqlBuilder.toString(), source, todoTaskRowMapper);
    }

    private void buildCondition(NamedParameterSqlBuilder sqlBuilder, MapSqlParameterSource source, TodoQueryDTO todoQueryDTO, WorkFlowStateTableModel stateTableModel) {
        List<String> workflowInstanceRange;
        List<String> uploadState;
        if (todoQueryDTO.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
            sqlBuilder.andWhere("PARTICIPANT");
            source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        } else {
            sqlBuilder.andWhere("COMPLETEUSER");
            source.addValue("COMPLETEUSER", (Object)NpContextHolder.getContext().getUserId());
        }
        sqlBuilder.and().andColumn("BIZDEFINE");
        source.addValue("BIZDEFINE", (Object)todoQueryDTO.getTaskId());
        sqlBuilder.and().andColumn("TASKDEFINEKEY");
        source.addValue("TASKDEFINEKEY", (Object)todoQueryDTO.getWorkflowNode());
        List<String> rangeUnits = todoQueryDTO.getRangeUnits();
        if (rangeUnits != null && !rangeUnits.isEmpty()) {
            sqlBuilder.and().inColumn(stateTableModel.getUnitColumn());
            source.addValue(stateTableModel.getUnitColumn(), rangeUnits);
        }
        WorkFlowType flowObjectType = this.todoExtend.getFlowObjectType(todoQueryDTO.getFormSchemeKey());
        List<String> rangeForms = todoQueryDTO.getRangeForms();
        if (flowObjectType != WorkFlowType.ENTITY && rangeForms != null && !rangeForms.isEmpty()) {
            sqlBuilder.and().inColumn(stateTableModel.getWorkFlowObjectColumn());
            source.addValue(stateTableModel.getWorkFlowObjectColumn(), rangeForms);
        }
        if ((uploadState = todoQueryDTO.getUploadState()) != null && !uploadState.isEmpty()) {
            if (todoQueryDTO.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
                sqlBuilder.and().inColumn(stateTableModel.getUploadStateColumn());
                source.addValue(stateTableModel.getUploadStateColumn(), uploadState);
            } else {
                sqlBuilder.and().inColumn(stateTableModel.getCurrentActionColumn());
                source.addValue(stateTableModel.getCurrentActionColumn(), uploadState);
            }
        }
        if ((workflowInstanceRange = todoQueryDTO.getWorkflowInstanceRange()) != null && !workflowInstanceRange.isEmpty()) {
            sqlBuilder.and().inColumn("PROCESSID");
            source.addValue("PROCESSID", workflowInstanceRange);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getPageSql(String sql, PageInfo pageInfo) {
        try (Connection connection = Objects.requireNonNull(this.jdbcTemplate.getDataSource()).getConnection();){
            IDatabase database = DatabaseManager.getInstance().findDatabaseByConnection(connection);
            IPagingSQLBuilder pageSQLBuilder = database.createPagingSQLBuilder();
            pageSQLBuilder.setRawSQL(sql);
            String string = pageSQLBuilder.buildSQL((pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize(), pageInfo.getCurrentPage() * pageInfo.getPageSize());
            return string;
        }
        catch (DBException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TodoTaskImpl> executeQuery(String sql, MapSqlParameterSource source, TodoTaskRowMapper todoTaskRowMapper) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, (RowMapper)todoTaskRowMapper);
    }

    private List<TodoTaskImpl> executeCommentQuery(String sql, MapSqlParameterSource source, TodoTaskCommentRowMapper todoTaskCommentRowMapper) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, (RowMapper)todoTaskCommentRowMapper);
    }
}

