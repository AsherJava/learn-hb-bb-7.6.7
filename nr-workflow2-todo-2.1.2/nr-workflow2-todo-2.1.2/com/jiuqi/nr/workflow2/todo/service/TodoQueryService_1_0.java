/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.todo.service;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.workflow2.todo.dao.TodoMultiplePeriodDao;
import com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao;
import com.jiuqi.nr.workflow2.todo.dto.TodoQueryDTO;
import com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition;
import com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo;
import com.jiuqi.nr.workflow2.todo.entity.TodoTableData;
import com.jiuqi.nr.workflow2.todo.entity.TodoTask;
import com.jiuqi.nr.workflow2.todo.entityimpl.EntityCaliberItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.ReportDimensionItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParam;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoFilterConditionImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTabInfoImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoType;
import com.jiuqi.nr.workflow2.todo.env.TodoBaseContext;
import com.jiuqi.nr.workflow2.todo.env.TodoFilterConditionContext;
import com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext;
import com.jiuqi.nr.workflow2.todo.env.TodoTabsQuantityContext;
import com.jiuqi.nr.workflow2.todo.envimpl.TodoTabsQuantityContextImpl;
import com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface;
import com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode;
import com.jiuqi.nr.workflow2.todo.service.TodoQueryService;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import com.jiuqi.util.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="todoQueryService_1_0")
public class TodoQueryService_1_0
implements TodoQueryService {
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Resource
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Resource
    private PeriodEngineService periodEngineService;
    @Resource
    private TodoUtil todoUtil;
    @Resource
    private TodoExtendInterface todoExtend;
    @Resource
    @Qualifier(value="TodoSinglePeriodDao_1_0")
    private TodoSinglePeriodDao todoSinglePeriodDao_1_0;
    @Resource
    private TodoMultiplePeriodDao todoMultiplePeriodDao;
    @Autowired
    private ITaskService taskService;
    @Resource
    private IEntityMetaService entityMetaService;

    @Override
    public TodoTabInfo getTodoTabInfos(TodoBaseContext context) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        boolean multiEntityCaliberWithReportDimensionEnable = this.todoExtend.isMultiEntityCaliberWithReportDimensionEnable(context.getTaskId());
        TodoTabInfoImpl tabInfo = new TodoTabInfoImpl();
        tabInfo.setWorkflowType(FlowsType.DEFAULT.equals((Object)flowsSetting.getFlowsType()) ? FlowsType.DEFAULT.toString() : FlowsType.WORKFLOW.toString());
        tabInfo.setSubmitExplain(flowsSetting.isSubmitExplain());
        tabInfo.setBackDescriptionNeedWrite(flowsSetting.isBackDescriptionNeedWrite());
        tabInfo.setReportDimensionEnable(this.todoExtend.isReportDimensionEnable(context.getTaskId()));
        tabInfo.setMultiEntityWithReportDimensionEnable(multiEntityCaliberWithReportDimensionEnable);
        String period = context.getPeriod();
        if (period == null || period.isEmpty()) {
            List<String> todoPeriodList = this.todoSinglePeriodDao_1_0.getPeriodsWithTodo(context.getTaskId());
            String curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode();
            period = todoPeriodList == null || todoPeriodList.isEmpty() || todoPeriodList.contains(curPeriod) ? curPeriod : (String)todoPeriodList.stream().findFirst().get();
        }
        String entityCaliber = "";
        if (context.getEntityCaliber() != null && !context.getEntityCaliber().isEmpty()) {
            entityCaliber = context.getEntityCaliber();
            tabInfo.setEntityCaliber(entityCaliber);
        } else {
            List<EntityCaliberItem> entityCaliberItems = this.buildEntityCaliberParam(context.getTaskId());
            List<EntityCaliberItem> entityCaliberFilterList = this.filterEntityCaliber(entityCaliberItems, context.getTaskId(), period);
            if (entityCaliberFilterList != null && !entityCaliberFilterList.isEmpty()) {
                tabInfo.setEntityCaliber(entityCaliberFilterList.get(0).getCode());
                entityCaliber = entityCaliberFilterList.get(0).getCode();
            }
        }
        List<WorkFlowNode> allWorkflowNode = this.todoExtend.getAllWorkflowNode(context.getTaskId(), period);
        tabInfo.setFlowNodes(this.filterTabs(context.getTaskId(), entityCaliber, period, allWorkflowNode));
        tabInfo.setFlowObjectType(this.todoExtend.getFlowObjectType(context.getTaskId(), period));
        tabInfo.setPeriod(period);
        return tabInfo;
    }

    @Override
    public Map<String, Integer> getTodoTabsQuantity(TodoTabsQuantityContext context) {
        HashMap<String, Integer> tabQuantityMap = new HashMap<String, Integer>();
        List<String> workflowNodes = context.getWorkflowNodes();
        for (String workflowNode : workflowNodes) {
            int quantity = this.todoSinglePeriodDao_1_0.getNodeTodoQuantity(context.getTaskId(), context.getEntityCaliber(), context.getPeriod(), workflowNode);
            tabQuantityMap.put(workflowNode, quantity);
        }
        return tabQuantityMap;
    }

    @Override
    public TodoFilterCondition getFilterConditions(TodoFilterConditionContext context) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        TodoFilterConditionImpl filterCondition = new TodoFilterConditionImpl();
        filterCondition.setPeriodParam(this.buildPeriodParam(context.getTaskId()));
        filterCondition.setTodoType(Arrays.asList(TodoType.values()));
        List<EntityCaliberItem> entityCaliberItems = this.buildEntityCaliberParam(context.getTaskId());
        filterCondition.setEntityCaliberParam(this.filterEntityCaliber(entityCaliberItems, context.getTaskId(), context.getPeriod()));
        if (context.getPeriod() != null && !context.getPeriod().isEmpty()) {
            filterCondition.setWorkflowButtons(this.todoExtend.getWorkFlowButtons(context.getTaskId(), context.getPeriod(), context.getFlowNodeCode()));
            filterCondition.setUploadStates(this.todoExtend.getUploadStates(context.getTaskId(), context.getPeriod(), context.getFlowNodeCode()));
            filterCondition.setReportDimensionParam(this.buildReportDimensionParam(context.getTaskId(), context.getPeriod()));
            return filterCondition;
        }
        IPeriodRow curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
        filterCondition.setWorkflowButtons(this.todoExtend.getWorkFlowButtons(context.getTaskId(), curPeriod.getCode(), context.getFlowNodeCode()));
        filterCondition.setUploadStates(this.todoExtend.getUploadStates(context.getTaskId(), curPeriod.getCode(), context.getFlowNodeCode()));
        filterCondition.setReportDimensionParam(this.buildReportDimensionParam(context.getTaskId(), curPeriod.getCode()));
        return filterCondition;
    }

    @Override
    public TodoTableDataInfoImpl getTodoTableData(TodoTableDataContext context) {
        boolean isPeriodNone;
        List<TodoTableData> tableDataList = new ArrayList<TodoTableData>();
        List<Object> todoTasks = new ArrayList();
        String period = context.getPeriod();
        boolean bl = isPeriodNone = period == null || period.isEmpty();
        if (isPeriodNone) {
            List<TodoTaskImpl> partOfResult;
            TodoQueryDTO todoQueryDTO;
            if (context.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
                List<TodoItem> todoItems = this.todoSinglePeriodDao_1_0.getTodoTask(context.getTaskId(), context.getPageInfo());
                Map<String, List<TodoItem>> map = this.divideIntoMapByFormSchemeKey(todoItems);
                for (Map.Entry<String, List<TodoItem>> entry : map.entrySet()) {
                    todoQueryDTO = this.buildTodoQueryDTO(context, entry.getKey(), entry.getValue());
                    partOfResult = this.todoMultiplePeriodDao.getTodoTask(todoQueryDTO);
                    List<TodoTaskImpl> commentTaskList = this.distinctCommentTodoTask(this.todoMultiplePeriodDao.getTodoTaskComment(todoQueryDTO, partOfResult));
                    for (TodoTaskImpl todoTask_withComment : commentTaskList) {
                        partOfResult.stream().filter(todoTask -> todoTask.getWorkflowInstance().equals(todoTask_withComment.getWorkflowInstance())).forEach(todoTask -> {
                            String comment = todoTask_withComment.getComment();
                            if (comment != null && !comment.isEmpty()) {
                                todoTask.setComment(comment);
                            }
                        });
                    }
                    todoTasks.addAll(partOfResult);
                }
            } else {
                List<TodoItem> historyTodoItems = this.todoSinglePeriodDao_1_0.getHistoryTodoTask(context.getTaskId(), context.getPageInfo());
                Map<String, List<TodoItem>> map = this.divideIntoMapByFormSchemeKey(historyTodoItems);
                for (Map.Entry<String, List<TodoItem>> entry : map.entrySet()) {
                    todoQueryDTO = this.buildTodoQueryDTO(context, entry.getKey(), entry.getValue());
                    partOfResult = this.todoMultiplePeriodDao.getHistoryTodoTask(todoQueryDTO);
                    todoTasks.addAll(this.distinctCommentTodoTask(partOfResult));
                }
            }
            if (todoTasks.isEmpty()) {
                return new TodoTableDataInfoImpl(0, tableDataList);
            }
        } else if (context.getTodoType().equals(TodoType.UNCOMPLETED.title)) {
            todoTasks = this.todoSinglePeriodDao_1_0.getTodoTask(context);
            if (todoTasks == null || todoTasks.isEmpty()) {
                return new TodoTableDataInfoImpl(0, tableDataList);
            }
        } else {
            todoTasks = this.todoSinglePeriodDao_1_0.getHistoryTodoTask(context);
            if (todoTasks == null || todoTasks.isEmpty()) {
                return new TodoTableDataInfoImpl(0, tableDataList);
            }
            todoTasks = this.distinctCommentTodoTask(todoTasks);
        }
        for (TodoTaskImpl todoTaskImpl : todoTasks) {
            tableDataList.add(this.buildTodoTableData(todoTaskImpl, context));
        }
        tableDataList = tableDataList.stream().sorted(Comparator.comparing(data -> data.getTableDataActualParam().getPeriod(), Comparator.reverseOrder()).thenComparing(data -> data.getTableDataShowText().getUnit() == null ? "" : data.getTableDataShowText().getUnit()).thenComparing(data -> data.getTableDataShowText().getWorkflowObject() == null ? "" : data.getTableDataShowText().getWorkflowObject())).collect(Collectors.toList());
        int filterTaskTodoQuantity = 0;
        if (isPeriodNone) {
            List<TodoItem> list = this.todoSinglePeriodDao_1_0.getTodoTask(context.getTaskId(), null);
            Map<String, List<TodoItem>> itemMap = this.divideIntoMapByFormSchemeKey(list);
            for (Map.Entry<String, List<TodoItem>> entry : itemMap.entrySet()) {
                TodoQueryDTO todoQueryDTO = this.buildTodoQueryDTO(context, entry.getKey(), entry.getValue());
                filterTaskTodoQuantity += this.todoMultiplePeriodDao.getFilterTaskTodoQuantity(todoQueryDTO);
            }
        } else {
            filterTaskTodoQuantity = this.todoSinglePeriodDao_1_0.getFilterTaskTodoQuantity(context);
        }
        return new TodoTableDataInfoImpl(filterTaskTodoQuantity, tableDataList);
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

    private Map<String, List<TodoItem>> divideIntoMapByFormSchemeKey(List<TodoItem> todoItems) {
        HashMap<String, List<TodoItem>> groupData = new HashMap<String, List<TodoItem>>();
        todoItems.forEach(todoItem -> {
            ArrayList<TodoItem> data = (ArrayList<TodoItem>)groupData.get(todoItem.getFormSchemeKey());
            if (data == null) {
                data = new ArrayList<TodoItem>();
                data.add((TodoItem)todoItem);
                groupData.put(todoItem.getFormSchemeKey(), data);
            } else {
                data.add((TodoItem)todoItem);
            }
        });
        return groupData;
    }

    private List<TodoTaskImpl> distinctCommentTodoTask(List<TodoTaskImpl> todoTask_withComment) {
        ArrayList<TodoTaskImpl> distinctData = new ArrayList<TodoTaskImpl>();
        HashMap<String, List> groupData = new HashMap<String, List>();
        todoTask_withComment.forEach(todoTask -> {
            ArrayList<TodoTaskImpl> data = (ArrayList<TodoTaskImpl>)groupData.get(todoTask.getWorkflowInstance());
            if (data == null) {
                data = new ArrayList<TodoTaskImpl>();
                data.add((TodoTaskImpl)todoTask);
                groupData.put(todoTask.getWorkflowInstance(), data);
            } else {
                data.add((TodoTaskImpl)todoTask);
            }
        });
        groupData.forEach((workflowInstance, resultGroupData) -> {
            List sortGroupData = resultGroupData.stream().sorted(Comparator.comparing(TodoTask::getTime, Comparator.reverseOrder())).collect(Collectors.toList());
            distinctData.add((TodoTaskImpl)sortGroupData.get(0));
        });
        return distinctData;
    }

    private TodoQueryDTO buildTodoQueryDTO(TodoTableDataContext context, String formSchemeKey, List<TodoItem> todoItems) {
        TodoQueryDTO todoQueryDTO = new TodoQueryDTO();
        todoQueryDTO.setFormSchemeKey(formSchemeKey);
        todoQueryDTO.setTaskId(context.getTaskId());
        todoQueryDTO.setWorkflowNode(context.getWorkflowNode());
        todoQueryDTO.setRangeUnits(context.getRangeUnits());
        todoQueryDTO.setRangeForms(context.getRangeForms());
        todoQueryDTO.setUploadState(context.getUploadState());
        todoQueryDTO.setTodoType(context.getTodoType());
        todoQueryDTO.setStateTableModel(this.todoExtend.getStateTableModel(formSchemeKey));
        todoQueryDTO.setWorkflowInstanceRange(todoItems.stream().map(TodoItem::getWorkflowInstance).collect(Collectors.toList()));
        todoQueryDTO.setPageInfo(context.getPageInfo());
        return todoQueryDTO;
    }

    private List<PeriodItem> buildPeriodParam(String taskId) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskId);
        List<String> periods = this.todoSinglePeriodDao_1_0.getPeriodsWithTodo(taskId);
        return periods.stream().filter(period -> period != null && !period.isEmpty()).map(period -> new PeriodItem((String)period, this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(period))).sorted(Comparator.comparing(PeriodItem::getCode, Comparator.reverseOrder())).collect(Collectors.toList());
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

    private List<EntityCaliberItem> buildEntityCaliberParam(String taskId) {
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().getList();
        if (taskOrgLinkDefines == null || taskOrgLinkDefines.isEmpty()) {
            return new ArrayList<EntityCaliberItem>();
        }
        return taskOrgLinkDefines.stream().map(e -> new EntityCaliberItem(e.getEntity(), this.entityMetaService.queryEntity(e.getEntity()).getTitle())).collect(Collectors.toList());
    }

    private TodoTableDataImpl buildTodoTableData(TodoTaskImpl todoTask, TodoTableDataContext context) {
        Date parseDate;
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        IPeriodRow curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
        String period = context.getPeriod() == null || context.getPeriod().isEmpty() ? curPeriod.getCode() : context.getPeriod();
        WorkFlowType flowObjectType = this.todoExtend.getFlowObjectType(context.getTaskId(), period);
        Map uploadStateMap = this.todoExtend.getUploadStates(context.getTaskId(), period, context.getWorkflowNode()).stream().collect(Collectors.toMap(UploadState::getCode, Function.identity(), (v1, v2) -> v1));
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(context.getTaskId(), period);
        TodoTableDataImpl tableData = new TodoTableDataImpl();
        TableDataShowText showText = new TableDataShowText();
        TableDataActualParam actualParam = new TableDataActualParam();
        showText.setPeriod(this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(todoTask.getPeriod()));
        actualParam.setPeriod(todoTask.getPeriod());
        IEntityRow targetEntityRow = this.getTargetEntityRow(formSchemeDefine, context.getEntityCaliber(), period, todoTask.getUnit());
        showText.setUnit(targetEntityRow != null ? targetEntityRow.getTitle() : todoTask.getUnit());
        actualParam.setUnit(todoTask.getUnit());
        if (flowObjectType == WorkFlowType.FORM) {
            FormDefine targetFormDefine = this.getTargetFormDefine(formSchemeDefine, todoTask.getWorkflowObject());
            showText.setWorkflowObject(targetFormDefine != null ? targetFormDefine.getTitle() : todoTask.getWorkflowObject());
            actualParam.setWorkflowObject(todoTask.getWorkflowObject());
        } else if (flowObjectType == WorkFlowType.GROUP) {
            FormGroupDefine targetFormGroupDefine = this.getTargetFormGroupDefine(formSchemeDefine, todoTask.getWorkflowObject());
            showText.setWorkflowObject(targetFormGroupDefine != null ? targetFormGroupDefine.getTitle() : todoTask.getWorkflowObject());
            actualParam.setWorkflowObject(todoTask.getWorkflowObject());
        }
        showText.setUploadState(((UploadState)uploadStateMap.get(todoTask.getUploadState())).getTitle());
        actualParam.setUploadState(todoTask.getUploadState());
        actualParam.setWorkflowTask(todoTask.getWorkflowNodeTask());
        actualParam.setWorkflowInstance(todoTask.getWorkflowInstance());
        actualParam.setAdjust(todoTask.getAdjust());
        List<ReportDimensionItem> caliberItems = this.buildReportDimensionParam(context.getTaskId(), context.getPeriod());
        Optional<ReportDimensionItem> caliber = caliberItems.stream().filter(item -> item.getCode().equals(todoTask.getReportDimension())).findFirst();
        showText.setReportDimension(caliber.isPresent() ? caliber.get().getTitle() : todoTask.getReportDimension());
        actualParam.setReportDimension(todoTask.getReportDimension());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            parseDate = simpleDateFormat.parse(todoTask.getTime());
        }
        catch (ParseException e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        showText.setTime(simpleDateFormat.format(parseDate));
        showText.setComment(todoTask.getComment());
        tableData.setTableDataShowText(showText);
        tableData.setTableDataActualParam(actualParam);
        return tableData;
    }

    private List<EntityCaliberItem> filterEntityCaliber(List<EntityCaliberItem> entityCaliberItems, String taskId, String period) {
        return entityCaliberItems.stream().filter(item -> this.todoSinglePeriodDao_1_0.isTodoExistWithEntityCaliber(taskId, period, item.getCode())).collect(Collectors.toList());
    }

    private List<WorkFlowNode> filterTabs(String taskId, String entityCaliber, String period, List<WorkFlowNode> workFlowNodes) {
        TodoTabsQuantityContextImpl context = new TodoTabsQuantityContextImpl();
        context.setTaskId(taskId);
        context.setEntityCaliber(entityCaliber);
        context.setPeriod(period);
        context.setWorkflowNodes(workFlowNodes.stream().map(WorkFlowNode::getCode).collect(Collectors.toList()));
        Map<String, Integer> todoTabsQuantity = this.getTodoTabsQuantity(context);
        return workFlowNodes.stream().filter(e -> (Integer)todoTabsQuantity.get(e.getCode()) > 0).collect(Collectors.toList());
    }
}

