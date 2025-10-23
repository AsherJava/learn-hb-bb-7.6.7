/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.DBException
 *  com.jiuqi.bi.database.DatabaseManager
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.database.paging.IPagingSQLBuilder
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.common.Consts
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
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
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao;
import com.jiuqi.nr.workflow2.todo.dto.TodoJoinBuildDTO;
import com.jiuqi.nr.workflow2.todo.entity.TodoTask;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoTableDataContextImpl;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder;
import com.jiuqi.nr.workflow2.todo.utils.TodoItemRowMapper;
import com.jiuqi.nr.workflow2.todo.utils.TodoTaskCommentRowMapper;
import com.jiuqi.nr.workflow2.todo.utils.TodoTaskRowMapper;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

@Repository(value="todoSinglePeriodDao_1_0")
public class TodoSinglePeriodDao_1_0
implements TodoSinglePeriodDao {
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
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    public PeriodEngineService periodEngineService;

    @Override
    public int getTaskTodoQuantity(String taskId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        sqlBuilder.countSQL(TODO_TAB_COUNT_COLUMN, new String[0]).andWhere("BIZDEFINE", "PARTICIPANT");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    @Override
    public int getNodeTodoQuantity(String taskId, String entityCaliber, String period, String workflowNode) {
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(taskId, period);
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        sqlBuilder.selectSQL(" COUNT(*) AS TODO_TAB_QUANTITY");
        this.buildInnerJoinSql(sqlBuilder, source, stateTableModel, new TodoJoinBuildDTO(taskId, entityCaliber, period, TodoType.UNCOMPLETED.name()));
        sqlBuilder.andWhere("BIZDEFINE", "TASKDEFINEKEY", "PARTICIPANT");
        source.addValue("BIZDEFINE", (Object)taskId);
        if (period != null && !period.isEmpty()) {
            sqlBuilder.and().andColumn("BIZDATE");
            source.addValue("BIZDATE", (Object)period);
        }
        source.addValue("TASKDEFINEKEY", (Object)workflowNode);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    @Override
    public int getFilterTaskTodoQuantity(TodoTableDataContext todoTableDataContext) {
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        sqlBuilder.selectSQL(" COUNT(*) AS TODO_TAB_QUANTITY");
        this.buildInnerJoinSql(sqlBuilder, source, stateTableModel, new TodoJoinBuildDTO(todoTableDataContext.getTaskId(), todoTableDataContext.getEntityCaliber(), todoTableDataContext.getPeriod(), todoTableDataContext.getTodoType()));
        this.buildCondition(sqlBuilder, source, todoTableDataContext, stateTableModel);
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    private int executeQuantityQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (Integer)Objects.requireNonNull(template.queryForObject(sql, (SqlParameterSource)source, Integer.class));
    }

    @Override
    public List<String> getPeriodsWithTodo(String taskId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        sqlBuilder.selectSQL("DISTINCT BIZDATE").andWhere("BIZDEFINE", "PARTICIPANT");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        return this.executeListQuery(sqlBuilder.toString(), source);
    }

    @Override
    public boolean isTodoExistWithEntityCaliber(String taskId, String period, String entityCaliber) {
        TodoTableDataContextImpl context = new TodoTableDataContextImpl();
        context.setTaskId(taskId);
        context.setPeriod(period);
        context.setEntityCaliber(entityCaliber);
        context.setTodoType(TodoType.UNCOMPLETED.title);
        List<TodoTaskImpl> todoTask = this.getTodoTask(context);
        return !todoTask.isEmpty();
    }

    private List<String> executeListQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.queryForList(sql, (SqlParameterSource)source, String.class);
    }

    @Override
    public List<TodoItem> getTodoTask(String taskId, PageInfo pageInfo) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        String[] selectColumns = new String[]{"BIZDEFINE", "BIZCODE", "BIZTITLE", "TASKID", "PARTICIPANT", "TASKDEFINEKEY", "PROCESSID", "PROCESSDEFINEKEY", "BIZDATE", "RECEIVETIME", "REMARK"};
        sqlBuilder.selectSQL(selectColumns).andWhere("BIZDEFINE", "PARTICIPANT");
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        String sql = pageInfo == null ? sqlBuilder.toString() : this.getPageSql(sqlBuilder.toString(), pageInfo);
        return this.executeQuery(sql, source);
    }

    @Override
    public List<TodoTaskImpl> getTodoTask(TodoTableDataContext todoTableDataContext) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(formSchemeDefine.getKey());
        boolean isAdjust = this.todoUtil.isAdjust(formSchemeDefine.getKey());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        boolean isReportDimensionEnable = this.todoExtend.isReportDimensionEnable(todoTableDataContext.getTaskId());
        List<String> columnList = this.buildTodoTaskJoinColumns(stateTableModel, isAdjust, isReportDimensionEnable);
        sqlBuilder.selectSQL(columnList.toArray(new String[0]));
        this.buildInnerJoinSql(sqlBuilder, source, stateTableModel, new TodoJoinBuildDTO(todoTableDataContext.getTaskId(), todoTableDataContext.getEntityCaliber(), todoTableDataContext.getPeriod(), todoTableDataContext.getTodoType()));
        this.buildCondition(sqlBuilder, source, todoTableDataContext, stateTableModel);
        sqlBuilder.orderByColumns(this.getOrderField(stateTableModel));
        String sql = this.getPageSql(sqlBuilder.toString(), todoTableDataContext.getPageInfo());
        TodoTaskRowMapper todoTaskRowMapper = new TodoTaskRowMapper(stateTableModel, isAdjust, isReportDimensionEnable);
        return this.executeQuery(sql, source, todoTaskRowMapper);
    }

    @Override
    public List<TodoTaskImpl> getTodoTaskComment(TodoTableDataContext todoTableDataContext, List<TodoTaskImpl> todoTasks) {
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        String[] selectColumns = new String[]{"PROCESSID", stateTableModel.getCommentColumn(), stateTableModel.getTimeColumn()};
        sqlBuilder.selectJoinOn(selectColumns, stateTableModel.getHistoryTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        sqlBuilder.where().inColumn("PROCESSID");
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("PROCESSID", todoTasks.stream().map(TodoTask::getWorkflowInstance).collect(Collectors.toList()));
        TodoTaskCommentRowMapper todoTaskCommentRowMapper = new TodoTaskCommentRowMapper(stateTableModel);
        return this.executeCommentQuery(sqlBuilder.toString(), source, todoTaskCommentRowMapper);
    }

    @Override
    public List<TodoItem> getHistoryTodoTask(String taskId, PageInfo pageInfo) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        String[] selectColumns = new String[]{"BIZDEFINE", "BIZCODE", "BIZTITLE", "TASKID", "PARTICIPANT", "TASKDEFINEKEY", "PROCESSID", "PROCESSDEFINEKEY", "BIZDATE", "REMARK"};
        sqlBuilder.selectSQL(selectColumns).andWhere("BIZDEFINE", "PARTICIPANT");
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        String sql = this.getPageSql(sqlBuilder.toString(), pageInfo);
        return this.executeQuery(sql, source);
    }

    @Override
    public List<TodoTaskImpl> getHistoryTodoTask(TodoTableDataContext todoTableDataContext) {
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK_HISTORY");
        MapSqlParameterSource source = new MapSqlParameterSource();
        String[] selectColumns = new String[]{stateTableModel.getPeriodColumn(), stateTableModel.getUnitColumn(), "TASKID", stateTableModel.getWorkFlowObjectColumn(), "PROCESSID", stateTableModel.getCurrentActionColumn(), "REMARK", stateTableModel.getTimeColumn(), stateTableModel.getCommentColumn()};
        sqlBuilder.selectJoinOn(selectColumns, stateTableModel.getHistoryTableName(), "PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        this.buildCondition(sqlBuilder, source, todoTableDataContext, stateTableModel);
        sqlBuilder.orderByColumns(this.getOrderField(stateTableModel));
        String sql = this.getPageSql(sqlBuilder.toString(), todoTableDataContext.getPageInfo());
        TodoTaskRowMapper todoTaskRowMapper = new TodoTaskRowMapper(stateTableModel, false, this.todoExtend.isReportDimensionEnable(todoTableDataContext.getTaskId()));
        return this.executeQuery(sql, source, todoTaskRowMapper);
    }

    private List<String> buildTodoTaskJoinColumns(WorkFlowStateTableModel stateTableModel, boolean isAdjust, boolean isReportDimensionEnable) {
        ArrayList<String> columnList = new ArrayList<String>();
        columnList.add(stateTableModel.getPeriodColumn());
        columnList.add(stateTableModel.getUnitColumn());
        columnList.add("TASKID");
        columnList.add(stateTableModel.getWorkFlowObjectColumn());
        columnList.add("PROCESSID");
        columnList.add(stateTableModel.getUploadStateColumn());
        columnList.add("RECEIVETIME");
        if (isAdjust) {
            columnList.add(stateTableModel.getAdjustColumn());
        }
        if (isReportDimensionEnable) {
            columnList.add(stateTableModel.getReportDimensionColumn());
        }
        columnList.add("REMARK");
        return columnList;
    }

    private void buildCondition(NamedParameterSqlBuilder sqlBuilder, MapSqlParameterSource source, TodoTableDataContext todoTableDataContext, WorkFlowStateTableModel stateTableModel) {
        List<String> uploadState;
        List<String> rangeUnits;
        if (todoTableDataContext.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
            sqlBuilder.andWhere("PARTICIPANT");
            source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        } else {
            sqlBuilder.andWhere("COMPLETEUSER");
            source.addValue("COMPLETEUSER", (Object)NpContextHolder.getContext().getUserId());
        }
        sqlBuilder.and().andColumn("BIZDEFINE");
        source.addValue("BIZDEFINE", (Object)todoTableDataContext.getTaskId());
        sqlBuilder.and().andColumn(stateTableModel.getPeriodColumn());
        source.addValue(stateTableModel.getPeriodColumn(), (Object)todoTableDataContext.getPeriod());
        String workflowNode = todoTableDataContext.getWorkflowNode();
        if (workflowNode != null && !workflowNode.isEmpty()) {
            sqlBuilder.and().andColumn("TASKDEFINEKEY");
            source.addValue("TASKDEFINEKEY", (Object)workflowNode);
        }
        if ((rangeUnits = todoTableDataContext.getRangeUnits()) != null && !rangeUnits.isEmpty()) {
            sqlBuilder.and().inColumn(stateTableModel.getUnitColumn());
            source.addValue(stateTableModel.getUnitColumn(), rangeUnits);
        }
        WorkFlowType flowObjectType = this.todoExtend.getFlowObjectType(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        List<String> rangeForms = todoTableDataContext.getRangeForms();
        if (flowObjectType != WorkFlowType.ENTITY && rangeForms != null && !rangeForms.isEmpty()) {
            sqlBuilder.and().inColumn(stateTableModel.getWorkFlowObjectColumn());
            source.addValue(stateTableModel.getWorkFlowObjectColumn(), rangeForms);
        }
        if ((uploadState = todoTableDataContext.getUploadState()) != null && !uploadState.isEmpty()) {
            if (todoTableDataContext.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
                sqlBuilder.and().inColumn(stateTableModel.getUploadStateColumn());
                source.addValue(stateTableModel.getUploadStateColumn(), uploadState);
            } else {
                sqlBuilder.and().inColumn(stateTableModel.getCurrentActionColumn());
                source.addValue(stateTableModel.getCurrentActionColumn(), uploadState);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getPageSql(String sql, PageInfo pageInfo) {
        if (pageInfo == null) {
            return sql;
        }
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

    private List<TodoItem> executeQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, (RowMapper)new TodoItemRowMapper());
    }

    private List<TodoTaskImpl> executeQuery(String sql, MapSqlParameterSource source, TodoTaskRowMapper todoTaskRowMapper) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, (RowMapper)todoTaskRowMapper);
    }

    private List<TodoTaskImpl> executeCommentQuery(String sql, MapSqlParameterSource source, TodoTaskCommentRowMapper todoTaskCommentRowMapper) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, (RowMapper)todoTaskCommentRowMapper);
    }

    private String[] getOrderField(WorkFlowStateTableModel stateTableModel) {
        String[] orderFields = new String[]{stateTableModel.getPeriodColumn(), stateTableModel.getUnitColumn()};
        return orderFields;
    }

    private void buildInnerJoinSql(NamedParameterSqlBuilder sqlBuilder, MapSqlParameterSource source, WorkFlowStateTableModel stateTableModel, TodoJoinBuildDTO todoJoinBuildDTO) {
        boolean isReportDimensionEnable = this.todoExtend.isReportDimensionEnable(todoJoinBuildDTO.getTaskId());
        sqlBuilder.innerJoin(todoJoinBuildDTO.getTodoType().equals(TodoType.UNCOMPLETED.title) ? stateTableModel.getTableName() : stateTableModel.getHistoryTableName());
        sqlBuilder.on().onColumn("PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        String entityCaliber = todoJoinBuildDTO.getEntityCaliber();
        if (entityCaliber != null && !entityCaliber.isEmpty()) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityCaliber);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityCaliber);
            sqlBuilder.innerJoin(entityDefine.getCode());
            sqlBuilder.on().onColumn(stateTableModel.getUnitColumn(), entityModel.getBizKeyField().getName());
            IEntityAttribute reportDimensionField = this.findReportDimensionField(entityCaliber);
            if (isReportDimensionEnable && reportDimensionField != null) {
                sqlBuilder.and().onColumn(stateTableModel.getReportDimensionColumn(), reportDimensionField.getName());
            }
            TaskDefine taskDefine = this.runTimeViewController.getTask(todoJoinBuildDTO.getTaskId());
            Date[] versionPeriodRange = this.parseFromPeriod(todoJoinBuildDTO.getPeriod(), taskDefine.getDateTime());
            sqlBuilder.and().andLessThanColumn(entityModel.getBeginDateField().getName());
            source.addValue(entityModel.getBeginDateField().getName(), (Object)versionPeriodRange[1]);
            sqlBuilder.and().andMoreThanOrEqualColumn(entityModel.getEndDateField().getName());
            source.addValue(entityModel.getEndDateField().getName(), (Object)versionPeriodRange[1]);
        }
    }

    public IEntityAttribute findReportDimensionField(String entityId) {
        ArrayList<String> currencyFieldName = new ArrayList<String>();
        List entityRefer = this.entityMetaService.getEntityRefer(entityId);
        for (IEntityRefer refer : entityRefer) {
            if (!refer.getReferEntityId().equals("MD_CURRENCY@BASE")) continue;
            currencyFieldName.add(refer.getOwnField());
        }
        IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
        List showFields = entityModel.getShowFields();
        IEntityAttribute parentField = entityModel.getParentField();
        IEntityAttribute targetField = null;
        for (IEntityAttribute showField : showFields) {
            String referColumnID = showField.getReferColumnID();
            if (showField.getName().equals(parentField.getName()) || referColumnID == null || referColumnID.isEmpty() || showField.isMultival() || currencyFieldName.contains(showField.getName())) continue;
            targetField = showField;
            break;
        }
        return targetField;
    }

    public Date[] parseFromPeriod(String periodString, String periodEntityId) {
        Date[] dateRegion = new Date[]{Consts.DATE_VERSION_INVALID_VALUE, Consts.DATE_VERSION_FOR_ALL};
        if (StringUtils.isEmpty((String)periodString) || StringUtils.isEmpty((String)periodEntityId)) {
            return dateRegion;
        }
        IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(periodEntityId);
        try {
            return periodProvider.getPeriodDateRegion(periodString);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

