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
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant
 *  com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao
 *  com.jiuqi.nr.workflow2.todo.dto.TodoJoinBuildDTO
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoItem
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoType
 *  com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext
 *  com.jiuqi.nr.workflow2.todo.envimpl.PageInfo
 *  com.jiuqi.nr.workflow2.todo.envimpl.TodoTableDataContextImpl
 *  com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface
 *  com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder
 *  com.jiuqi.nr.workflow2.todo.utils.TodoUtil
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.workflow2.todo.service.dao;

import com.jiuqi.bi.database.DBException;
import com.jiuqi.bi.database.DatabaseManager;
import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.database.paging.IPagingSQLBuilder;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.common.Consts;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.model.DataModelConstant;
import com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao;
import com.jiuqi.nr.workflow2.todo.dto.TodoJoinBuildDTO;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.envimpl.PageInfo;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoTableDataContextImpl;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.service.rowmapper.TodoTaskRowMapper_2_0;
import com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository(value="todoSinglePeriodDao_2_0")
public class TodoSinglePeriodDao_2_0
implements TodoSinglePeriodDao {
    public static final String TODO_TAB_COUNT_COLUMN = "TODO_TAB_QUANTITY";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TodoExtendInterface todoExtend;
    @Autowired
    private TodoUtil todoUtil;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;

    public int getTaskTodoQuantity(String taskId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        sqlBuilder.countSQL(TODO_TAB_COUNT_COLUMN, new String[0]).andWhere(new String[]{"BIZDEFINE", "PARTICIPANT"});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    public int getNodeTodoQuantity(String taskId, String entityCaliber, String period, String workflowNode) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskId, period);
        if (formSchemeDefine == null) {
            return 0;
        }
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        sqlBuilder.selectSQL(new String[]{" COUNT(*) AS TODO_TAB_QUANTITY"});
        this.buildInnerJoinSql(sqlBuilder, source, formSchemeDefine, new TodoJoinBuildDTO(taskId, entityCaliber, period, TodoType.UNCOMPLETED.name()));
        sqlBuilder.andWhere(new String[]{"BIZDEFINE", "TASKDEFINEKEY", "PARTICIPANT"});
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        source.addValue("TASKDEFINEKEY", (Object)workflowNode);
        if (period != null && !period.isEmpty()) {
            sqlBuilder.and().andColumn("BIZDATE");
            source.addValue("BIZDATE", (Object)period);
        }
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    public int getFilterTaskTodoQuantity(TodoTableDataContext todoTableDataContext) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(todoTableDataContext.getTaskId());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        sqlBuilder.selectSQL(new String[]{" COUNT(*) AS TODO_TAB_QUANTITY"});
        this.buildInnerJoinSql(sqlBuilder, source, formSchemeDefine, new TodoJoinBuildDTO(todoTableDataContext.getTaskId(), todoTableDataContext.getEntityCaliber(), todoTableDataContext.getPeriod(), todoTableDataContext.getTodoType()));
        this.buildCondition(sqlBuilder, source, todoTableDataContext, workflowObjectType);
        return this.executeQuantityQuery(sqlBuilder.toString(), source);
    }

    private int executeQuantityQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return (Integer)Objects.requireNonNull(template.queryForObject(sql, (SqlParameterSource)source, Integer.class));
    }

    public List<String> getPeriodsWithTodo(String taskId) {
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        sqlBuilder.selectSQL(new String[]{"DISTINCT BIZDATE"}).andWhere(new String[]{"BIZDEFINE", "PARTICIPANT"});
        MapSqlParameterSource source = new MapSqlParameterSource();
        source.addValue("BIZDEFINE", (Object)taskId);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        return this.executeListQuery(sqlBuilder.toString(), source);
    }

    public boolean isTodoExistWithEntityCaliber(String taskId, String period, String entityCaliber) {
        TodoTableDataContextImpl context = new TodoTableDataContextImpl();
        context.setTaskId(taskId);
        context.setPeriod(period);
        context.setEntityCaliber(entityCaliber);
        context.setTodoType(TodoType.UNCOMPLETED.title);
        List<TodoTaskImpl> todoTask = this.getTodoTask((TodoTableDataContext)context);
        return !todoTask.isEmpty();
    }

    private List<String> executeListQuery(String sql, MapSqlParameterSource source) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.queryForList(sql, (SqlParameterSource)source, String.class);
    }

    public List<TodoItem> getTodoTask(String taskId, PageInfo pageInfo) {
        return Collections.emptyList();
    }

    public List<TodoTaskImpl> getTodoTask(TodoTableDataContext todoTableDataContext) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(todoTableDataContext.getTaskId(), todoTableDataContext.getPeriod());
        if (formSchemeDefine == null) {
            return new ArrayList<TodoTaskImpl>();
        }
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(todoTableDataContext.getTaskId());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        List<String> selectColumns = this.getSelectColumns(workflowObjectType);
        sqlBuilder.selectSQL(selectColumns.toArray(new String[0]));
        this.buildInnerJoinSql(sqlBuilder, source, formSchemeDefine, new TodoJoinBuildDTO(todoTableDataContext.getTaskId(), todoTableDataContext.getEntityCaliber(), todoTableDataContext.getPeriod(), todoTableDataContext.getTodoType()));
        this.buildCondition(sqlBuilder, source, todoTableDataContext, workflowObjectType);
        sqlBuilder.orderByColumns(this.getOrderField(workflowObjectType));
        String sql = this.getPageSql(sqlBuilder.toString(), todoTableDataContext.getPageInfo());
        return this.executeQuery(sql, source, new TodoTaskRowMapper_2_0(workflowObjectType));
    }

    public List<TodoTaskImpl> getTodoTaskComment(TodoTableDataContext todoTableDataContext, List<TodoTaskImpl> todoTasks) {
        return Collections.emptyList();
    }

    public List<TodoItem> getHistoryTodoTask(String taskId, PageInfo pageInfo) {
        return Collections.emptyList();
    }

    public List<TodoTaskImpl> getHistoryTodoTask(TodoTableDataContext todoTableDataContext) {
        return Collections.emptyList();
    }

    private List<TodoTaskImpl> executeQuery(String sql, MapSqlParameterSource source, RowMapper<TodoTaskImpl> todoTaskRowMapper) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, todoTaskRowMapper);
    }

    private List<String> getSelectColumns(WorkflowObjectType workflowObjectType) {
        ArrayList<String> columns = new ArrayList<String>();
        columns.add("DATATIME");
        columns.add("MDCODE");
        columns.add("TASKID");
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            columns.add("IST_FORMKEY");
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            columns.add("IST_FORMGROUPKEY");
        }
        columns.add("PROCESSID");
        columns.add("IST_STATUS");
        columns.add("RECEIVETIME");
        columns.add("REMARK");
        return columns;
    }

    private void buildCondition(NamedParameterSqlBuilder sqlBuilder, MapSqlParameterSource source, TodoTableDataContext context, WorkflowObjectType workflowObjectType) {
        List uploadState;
        List rangeForms;
        List rangeUnits;
        if (context.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
            sqlBuilder.andWhere(new String[]{"PARTICIPANT"});
            source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        } else {
            sqlBuilder.andWhere(new String[]{"COMPLETEUSER"});
            source.addValue("COMPLETEUSER", (Object)NpContextHolder.getContext().getUserId());
        }
        sqlBuilder.and().andColumn("BIZDEFINE");
        source.addValue("BIZDEFINE", (Object)context.getTaskId());
        sqlBuilder.and().andColumn("BIZDATE");
        source.addValue("BIZDATE", (Object)context.getPeriod());
        String workflowNode = context.getWorkflowNode();
        if (workflowNode != null && !workflowNode.isEmpty()) {
            sqlBuilder.and().andColumn("TASKDEFINEKEY");
            source.addValue("TASKDEFINEKEY", (Object)workflowNode);
        }
        if ((rangeUnits = context.getRangeUnits()) != null && !rangeUnits.isEmpty()) {
            sqlBuilder.and().inColumn("MDCODE");
            source.addValue("MDCODE", (Object)rangeUnits);
        }
        if ((rangeForms = context.getRangeForms()) != null && !rangeForms.isEmpty()) {
            if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
                sqlBuilder.and().inColumn("IST_FORMKEY");
                source.addValue("IST_FORMKEY", (Object)rangeForms);
            } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
                sqlBuilder.and().inColumn("IST_FORMGROUPKEY");
                source.addValue("IST_FORMGROUPKEY", (Object)rangeForms);
            }
        }
        if ((uploadState = context.getUploadState()) != null && !uploadState.isEmpty()) {
            if (context.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
                sqlBuilder.and().inColumn("IST_STATUS");
                source.addValue("IST_STATUS", (Object)uploadState);
            } else {
                sqlBuilder.and().inColumn("OPT_TONODE");
                source.addValue("OPT_TONODE", (Object)uploadState);
            }
        }
    }

    private String[] getOrderField(WorkflowObjectType workflowObjectType) {
        int fieldNum = workflowObjectType.equals((Object)WorkflowObjectType.FORM) || workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP) ? 3 : 2;
        String[] orderFields = new String[fieldNum];
        orderFields[0] = "BIZDATE";
        orderFields[1] = "MDCODE";
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            orderFields[2] = "IST_FORMKEY";
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            orderFields[2] = "IST_FORMGROUPKEY";
        }
        return orderFields;
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

    private void buildInnerJoinSql(NamedParameterSqlBuilder sqlBuilder, MapSqlParameterSource source, FormSchemeDefine formSchemeDefine, TodoJoinBuildDTO todoJoinBuildDTO) {
        boolean isReportDimensionEnable = this.todoExtend.isReportDimensionEnable(todoJoinBuildDTO.getTaskId());
        sqlBuilder.innerJoin(todoJoinBuildDTO.getTodoType().equals(TodoType.UNCOMPLETED.title) ? DataModelConstant.getInstanceTableName((FormSchemeDefine)formSchemeDefine) : DataModelConstant.getHistoryTableName((FormSchemeDefine)formSchemeDefine));
        sqlBuilder.on().onColumn("PROCESSID", "IST_ID");
        String entityCaliber = todoJoinBuildDTO.getEntityCaliber();
        if (entityCaliber != null && !entityCaliber.isEmpty()) {
            TaskDefine taskDefine;
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityCaliber);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityCaliber);
            sqlBuilder.innerJoin(entityDefine.getCode());
            sqlBuilder.on().onColumn("MDCODE", entityModel.getBizKeyField().getName());
            IEntityAttribute reportDimensionField = this.findReportDimensionField(entityCaliber);
            if (isReportDimensionEnable && reportDimensionField != null) {
                taskDefine = this.runTimeViewController.getTask(todoJoinBuildDTO.getTaskId());
                List sceneDimensions = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme(), DimensionType.DIMENSION);
                for (DataDimension dimension : sceneDimensions) {
                    if (!dimension.getReportDim().booleanValue()) continue;
                    String reportDimensionColumn = this.entityMetaService.getDimensionName(dimension.getDimKey());
                    sqlBuilder.and().onColumn(reportDimensionColumn, reportDimensionField.getName());
                    break;
                }
            }
            taskDefine = this.runTimeViewController.getTask(todoJoinBuildDTO.getTaskId());
            Date[] versionPeriodRange = this.parseFromPeriod(todoJoinBuildDTO.getPeriod(), taskDefine.getDateTime());
            sqlBuilder.and().andLessThanColumn(entityModel.getBeginDateField().getName());
            source.addValue(entityModel.getBeginDateField().getName(), (Object)versionPeriodRange[1]);
            sqlBuilder.and().andMoreThanOrEqualColumn(entityModel.getEndDateField().getName());
            source.addValue(entityModel.getEndDateField().getName(), (Object)versionPeriodRange[1]);
        }
    }

    private IEntityAttribute findReportDimensionField(String entityId) {
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

    private Date[] parseFromPeriod(String periodString, String periodEntityId) {
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

