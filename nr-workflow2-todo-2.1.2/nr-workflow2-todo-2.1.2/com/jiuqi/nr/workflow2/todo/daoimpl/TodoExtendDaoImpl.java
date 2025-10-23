/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  org.springframework.jdbc.core.JdbcOperations
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.core.RowMapper
 *  org.springframework.jdbc.core.namedparam.MapSqlParameterSource
 *  org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
 *  org.springframework.jdbc.core.namedparam.SqlParameterSource
 */
package com.jiuqi.nr.workflow2.todo.daoimpl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.todo.dao.TodoExtendDao;
import com.jiuqi.nr.workflow2.todo.daoimpl.TodoSinglePeriodDao_1_0;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskExtend;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.extend.WorkFlowStateTableModel;
import com.jiuqi.nr.workflow2.todo.utils.NamedParameterSqlBuilder;
import com.jiuqi.nr.workflow2.todo.utils.TodoTaskExtendRowMapper;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class TodoExtendDaoImpl
implements TodoExtendDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TodoUtil todoUtil;
    @Autowired
    private TodoExtendInterface todoExtend;
    @Autowired
    public PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private TodoSinglePeriodDao_1_0 todoSinglePeriodDao_1_0;

    @Override
    public List<TodoTaskExtend> getTodoTaskExtend(String taskKey, String period, String entityCaliber) {
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskKey, period);
        WorkFlowStateTableModel stateTableModel = this.todoExtend.getStateTableModel(formSchemeDefine.getKey());
        boolean isAdjust = this.todoUtil.isAdjust(formSchemeDefine.getKey());
        NamedParameterSqlBuilder sqlBuilder = new NamedParameterSqlBuilder("TODO_TASK");
        MapSqlParameterSource source = new MapSqlParameterSource();
        boolean isReportDimensionEnable = this.todoExtend.isReportDimensionEnable(taskKey);
        List<String> columnList = this.buildTodoTaskJoinColumns(stateTableModel, isAdjust, isReportDimensionEnable);
        sqlBuilder.selectSQL(columnList.toArray(new String[0]));
        sqlBuilder.innerJoin(stateTableModel.getTableName());
        sqlBuilder.on().onColumn("PROCESSID", stateTableModel.getWorkflowInstanceColumn());
        if (entityCaliber != null && !entityCaliber.isEmpty()) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(entityCaliber);
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityCaliber);
            sqlBuilder.innerJoin(entityDefine.getCode());
            sqlBuilder.on().onColumn(stateTableModel.getUnitColumn(), entityModel.getBizKeyField().getName());
            IEntityAttribute reportDimensionField = this.todoSinglePeriodDao_1_0.findReportDimensionField(entityCaliber);
            if (isReportDimensionEnable && reportDimensionField != null) {
                sqlBuilder.and().onColumn(stateTableModel.getReportDimensionColumn(), reportDimensionField.getName());
            }
            TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
            Date[] versionPeriodRange = this.todoSinglePeriodDao_1_0.parseFromPeriod(period, taskDefine.getDateTime());
            sqlBuilder.and().andLessThanColumn(entityModel.getBeginDateField().getName());
            source.addValue(entityModel.getBeginDateField().getName(), (Object)versionPeriodRange[1]);
            sqlBuilder.and().andMoreThanOrEqualColumn(entityModel.getEndDateField().getName());
            source.addValue(entityModel.getEndDateField().getName(), (Object)versionPeriodRange[1]);
        }
        sqlBuilder.andWhere("BIZDEFINE", "BIZDATE", "PARTICIPANT");
        source.addValue("BIZDEFINE", (Object)taskKey);
        source.addValue("BIZDATE", (Object)period);
        source.addValue("PARTICIPANT", (Object)NpContextHolder.getContext().getUserId());
        sqlBuilder.orderByColumns(this.getOrderField(stateTableModel));
        TodoTaskExtendRowMapper todoTaskExtendRowMapper = new TodoTaskExtendRowMapper(stateTableModel, isAdjust, isReportDimensionEnable);
        return this.executeQuery(sqlBuilder.toString(), source, todoTaskExtendRowMapper);
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
        columnList.add("TASKDEFINEKEY");
        return columnList;
    }

    private String[] getOrderField(WorkFlowStateTableModel stateTableModel) {
        String[] orderFields = new String[]{stateTableModel.getPeriodColumn(), stateTableModel.getUnitColumn()};
        return orderFields;
    }

    private List<TodoTaskExtend> executeQuery(String sql, MapSqlParameterSource source, RowMapper<TodoTaskExtend> rowMapper) {
        NamedParameterJdbcTemplate template = new NamedParameterJdbcTemplate((JdbcOperations)this.jdbcTemplate);
        return template.query(sql, (SqlParameterSource)source, rowMapper);
    }
}

