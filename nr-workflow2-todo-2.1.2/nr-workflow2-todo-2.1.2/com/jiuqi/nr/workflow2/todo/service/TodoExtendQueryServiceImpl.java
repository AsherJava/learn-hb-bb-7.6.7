/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.todo.service;

import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.workflow2.todo.dao.TodoExtendDao;
import com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.ReportDimensionItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParamExtend;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataExtend;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskExtend;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.service.TodoExtendQueryService;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import com.jiuqi.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TodoExtendQueryServiceImpl
implements TodoExtendQueryService {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    @Qualifier(value="todoSinglePeriodDao_1_0")
    private TodoSinglePeriodDao todoSinglePeriodDao;
    @Autowired
    private TodoExtendDao todoExtendDao;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private TodoUtil todoUtil;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private TodoExtendInterface todoExtend;

    @Override
    public List<PeriodItem> getPeriodsWithTodo(String taskKey) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        List<String> periods = this.todoSinglePeriodDao.getPeriodsWithTodo(taskKey);
        return periods.stream().filter(period -> period != null && !period.isEmpty()).map(period -> new PeriodItem((String)period, this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(period))).sorted(Comparator.comparing(PeriodItem::getCode, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    @Override
    public List<TodoTableDataExtend> getTodoTaskExtendInfo(String taskKey, String period, String entityCaliber) {
        List<TodoTaskExtend> todoTaskExtend = this.todoExtendDao.getTodoTaskExtend(taskKey, period, entityCaliber);
        ArrayList<TodoTableDataExtend> result = new ArrayList<TodoTableDataExtend>();
        for (TodoTaskExtend extend : todoTaskExtend) {
            TodoTableDataExtend todoTableDataExtend = this.buildTodoTableDataExtend(extend, taskKey, period);
            todoTableDataExtend.getTableDataActualParamExtend().setEntityCaliber(entityCaliber);
            result.add(todoTableDataExtend);
        }
        return result;
    }

    private TodoTableDataExtend buildTodoTableDataExtend(TodoTaskExtend todoTaskExtend, String taskKey, String period) {
        Date parseDate;
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        WorkFlowType flowObjectType = this.todoExtend.getFlowObjectType(taskKey, period);
        Map uploadStateMap = this.todoExtend.getUploadStates(taskKey, period, todoTaskExtend.getWorkflowNode()).stream().collect(Collectors.toMap(UploadState::getCode, Function.identity(), (v1, v2) -> v1));
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(taskKey, period);
        TodoTableDataExtend tableDataExtend = new TodoTableDataExtend();
        TableDataShowText showText = new TableDataShowText();
        TableDataActualParamExtend actualParam = new TableDataActualParamExtend();
        showText.setPeriod(this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(todoTaskExtend.getPeriod()));
        actualParam.setPeriod(todoTaskExtend.getPeriod());
        IEntityRow targetEntityRow = this.getTargetEntityRow(formSchemeDefine, DsContextHolder.getDsContext().getContextEntityId(), period, todoTaskExtend.getUnit());
        showText.setUnit(targetEntityRow != null ? targetEntityRow.getTitle() : todoTaskExtend.getUnit());
        actualParam.setUnit(todoTaskExtend.getUnit());
        if (flowObjectType == WorkFlowType.FORM) {
            FormDefine targetFormDefine = this.getTargetFormDefine(formSchemeDefine, todoTaskExtend.getWorkflowObject());
            showText.setWorkflowObject(targetFormDefine != null ? targetFormDefine.getTitle() : todoTaskExtend.getWorkflowObject());
            actualParam.setWorkflowObject(todoTaskExtend.getWorkflowObject());
        } else if (flowObjectType == WorkFlowType.GROUP) {
            FormGroupDefine targetFormGroupDefine = this.getTargetFormGroupDefine(formSchemeDefine, todoTaskExtend.getWorkflowObject());
            showText.setWorkflowObject(targetFormGroupDefine != null ? targetFormGroupDefine.getTitle() : todoTaskExtend.getWorkflowObject());
            actualParam.setWorkflowObject(todoTaskExtend.getWorkflowObject());
        }
        showText.setUploadState(((UploadState)uploadStateMap.get(todoTaskExtend.getUploadState())).getTitle());
        actualParam.setUploadState(todoTaskExtend.getUploadState());
        actualParam.setWorkflowTask(todoTaskExtend.getWorkflowNodeTask());
        actualParam.setWorkflowInstance(todoTaskExtend.getWorkflowInstance());
        actualParam.setAdjust(todoTaskExtend.getAdjust());
        List<ReportDimensionItem> caliberItems = this.buildReportDimensionParam(taskKey, period);
        Optional<ReportDimensionItem> caliber = caliberItems.stream().filter(item -> item.getCode().equals(todoTaskExtend.getReportDimension())).findFirst();
        showText.setReportDimension(caliber.isPresent() ? caliber.get().getTitle() : todoTaskExtend.getReportDimension());
        actualParam.setReportDimension(todoTaskExtend.getReportDimension());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            parseDate = simpleDateFormat.parse(todoTaskExtend.getTime());
        }
        catch (ParseException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        showText.setTime(simpleDateFormat.format(parseDate));
        showText.setComment(todoTaskExtend.getComment());
        actualParam.setWorkflowNode(todoTaskExtend.getWorkflowNode());
        tableDataExtend.setTableDataShowText(showText);
        tableDataExtend.setTableDataActualParamExtend(actualParam);
        return tableDataExtend;
    }

    private IEntityRow getTargetEntityRow(FormSchemeDefine formSchemeDefine, String entityCaliber, String period, String unit) {
        List entityRows = this.todoUtil.getEntityTable(StringUtils.isEmpty((String)entityCaliber) ? formSchemeDefine.getDw() : entityCaliber, period, formSchemeDefine.getDateTime()).getAllRows();
        Optional<IEntityRow> targetEntityRow = entityRows.stream().filter(row -> row.getEntityKeyData().equals(unit)).findFirst();
        return targetEntityRow.orElse(null);
    }

    private FormDefine getTargetFormDefine(FormSchemeDefine formSchemeDefine, String formKey) {
        List formDefines = this.runTimeViewController.listFormByFormScheme(formSchemeDefine.getKey());
        Optional<FormDefine> targetFormDefine = formDefines.stream().filter(formDefine -> formDefine.getKey().equals(formKey)).findFirst();
        return targetFormDefine.orElse(null);
    }

    private FormGroupDefine getTargetFormGroupDefine(FormSchemeDefine formSchemeDefine, String formGroupKey) {
        List formGroupDefines = this.runTimeViewController.listFormGroupByFormScheme(formSchemeDefine.getKey());
        Optional<FormGroupDefine> targetFormGroupDefine = formGroupDefines.stream().filter(formGroupDefine -> formGroupDefine.getKey().equals(formGroupKey)).findFirst();
        return targetFormGroupDefine.orElse(null);
    }

    private List<ReportDimensionItem> buildReportDimensionParam(String taskId, String period) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskId);
        List reportDimension = this.taskService.getReportDimension(taskId);
        DataDimension caliberDimention = null;
        for (DataDimension dimension : reportDimension) {
            if (!this.todoExtend.isCorporate(taskDefine, dimension)) continue;
            caliberDimention = dimension;
            break;
        }
        if (caliberDimention == null) {
            return new ArrayList<ReportDimensionItem>();
        }
        IEntityTable entityTable = this.todoUtil.getEntityTable(caliberDimention.getDimKey(), period, taskDefine.getDateTime());
        List allCaliber = entityTable.getAllRows();
        if (allCaliber == null || allCaliber.isEmpty()) {
            return new ArrayList<ReportDimensionItem>();
        }
        return allCaliber.stream().map(row -> new ReportDimensionItem(row.getEntityKeyData(), row.getTitle())).collect(Collectors.toList());
    }
}

