/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodRow
 *  com.jiuqi.nr.workflow2.converter.utils.ConverterUtil
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig
 *  com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao
 *  com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition
 *  com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.EntityCaliberItem
 *  com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem
 *  com.jiuqi.nr.workflow2.todo.entityimpl.ReportDimensionItem
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParam
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoFilterConditionImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTabInfoImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl
 *  com.jiuqi.nr.workflow2.todo.entityimpl.UploadState
 *  com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton
 *  com.jiuqi.nr.workflow2.todo.enumeration.CommentType
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoType
 *  com.jiuqi.nr.workflow2.todo.env.TodoBaseContext
 *  com.jiuqi.nr.workflow2.todo.env.TodoFilterConditionContext
 *  com.jiuqi.nr.workflow2.todo.env.TodoTableDataContext
 *  com.jiuqi.nr.workflow2.todo.env.TodoTabsQuantityContext
 *  com.jiuqi.nr.workflow2.todo.envimpl.TodoTabsQuantityContextImpl
 *  com.jiuqi.nr.workflow2.todo.extend.TodoExtendInterface
 *  com.jiuqi.nr.workflow2.todo.extend.env.WorkFlowNode
 *  com.jiuqi.nr.workflow2.todo.service.TodoQueryService
 *  com.jiuqi.nr.workflow2.todo.utils.TodoUtil
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.workflow2.todo.service.service;

import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.stream.param.TaskOrgLinkListStream;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodRow;
import com.jiuqi.nr.workflow2.converter.utils.ConverterUtil;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IProcessStatus;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserTask;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.todo.dao.TodoSinglePeriodDao;
import com.jiuqi.nr.workflow2.todo.entity.TodoFilterCondition;
import com.jiuqi.nr.workflow2.todo.entity.TodoTabInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.EntityCaliberItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.PeriodItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.ReportDimensionItem;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataActualParam;
import com.jiuqi.nr.workflow2.todo.entityimpl.TableDataShowText;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoFilterConditionImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTabInfoImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataInfoImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTaskImpl;
import com.jiuqi.nr.workflow2.todo.entityimpl.UploadState;
import com.jiuqi.nr.workflow2.todo.entityimpl.WorkflowButton;
import com.jiuqi.nr.workflow2.todo.enumeration.CommentType;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
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
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="todoQueryService_2_0")
public class TodoQueryService_2_0
implements TodoQueryService {
    @Autowired
    @Qualifier(value="todoSinglePeriodDao_2_0")
    private TodoSinglePeriodDao todoSinglePeriodDao_2_0;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private DefaultProcessDesignService defaultProcessDesignService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private TodoUtil todoUtil;
    @Autowired
    private ConverterUtil converterUtil;
    @Autowired
    private TodoExtendInterface todoExtend;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private Workflow2EngineCompatibleCollector workflow2EngineCompatibleCollector;

    public TodoTabInfo getTodoTabInfos(TodoBaseContext context) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        WorkflowSettingsDO workflowSettingsDO = this.workflowSettingsService.queryWorkflowSettings(context.getTaskId());
        String workflowEngine = workflowSettingsDO.getWorkflowEngine();
        boolean isDefaultEngine = workflowEngine.equals("jiuqi.nr.default");
        Workflow2EngineCompatibleExtend workflow2EngineCompatibleExtend = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
        String uploadDescExtend = workflow2EngineCompatibleExtend.getUploadDesc(context.getTaskId());
        String returnDescExtend = workflow2EngineCompatibleExtend.getReturnDesc(context.getTaskId());
        FillInDescStrategy uploadDesc = uploadDescExtend == null ? null : FillInDescStrategy.valueOf((String)uploadDescExtend);
        FillInDescStrategy returnDesc = returnDescExtend == null ? null : FillInDescStrategy.valueOf((String)returnDescExtend);
        TodoTabInfoImpl tabInfo = new TodoTabInfoImpl();
        tabInfo.setWorkflowType(isDefaultEngine ? FlowsType.DEFAULT.toString() : FlowsType.WORKFLOW.toString());
        tabInfo.setFlowObjectType(this.transferToOldWorkflowObject(workflowSettingsDO.getWorkflowObjectType()));
        tabInfo.setSubmitExplain(uploadDesc != null);
        tabInfo.setBackDescriptionNeedWrite(returnDesc != null);
        tabInfo.setReportDimensionEnable(this.todoExtend.isReportDimensionEnable(context.getTaskId()));
        tabInfo.setMultiEntityWithReportDimensionEnable(this.todoExtend.isMultiEntityCaliberWithReportDimensionEnable(context.getTaskId()));
        List todoPeriodList = this.todoSinglePeriodDao_2_0.getPeriodsWithTodo(context.getTaskId());
        String curPeriod = context.getPeriod() == null || context.getPeriod().isEmpty() ? this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode() : context.getPeriod();
        String period = todoPeriodList == null || todoPeriodList.isEmpty() || todoPeriodList.contains(curPeriod) ? curPeriod : (String)todoPeriodList.stream().findFirst().get();
        tabInfo.setPeriod(period);
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
        List userTasks = this.processMetaDataService.queryAllUserTasks(context.getTaskId());
        List<WorkFlowNode> allWorkflowNode = this.buildWorkFlowNode(context.getTaskId(), userTasks, workflow2EngineCompatibleExtend, isDefaultEngine);
        tabInfo.setFlowNodes(this.filterTabs(context.getTaskId(), entityCaliber, period, allWorkflowNode));
        return tabInfo;
    }

    public Map<String, Integer> getTodoTabsQuantity(TodoTabsQuantityContext context) {
        HashMap<String, Integer> tabQuantityMap = new HashMap<String, Integer>();
        List workflowNodes = context.getWorkflowNodes();
        for (String workflowNode : workflowNodes) {
            int quantity = this.todoSinglePeriodDao_2_0.getNodeTodoQuantity(context.getTaskId(), context.getEntityCaliber(), context.getPeriod(), workflowNode);
            tabQuantityMap.put(workflowNode, quantity);
        }
        return tabQuantityMap;
    }

    public TodoFilterCondition getFilterConditions(TodoFilterConditionContext context) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        String curPeriod = context.getPeriod() == null || context.getPeriod().isEmpty() ? this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod().getCode() : context.getPeriod();
        TodoFilterConditionImpl filterCondition = new TodoFilterConditionImpl();
        filterCondition.setPeriodParam(this.buildPeriodParam(context.getTaskId()));
        filterCondition.setTodoType(Arrays.asList(TodoType.values()));
        List<EntityCaliberItem> entityCaliberItems = this.buildEntityCaliberParam(context.getTaskId());
        filterCondition.setEntityCaliberParam(this.filterEntityCaliber(entityCaliberItems, context.getTaskId(), context.getPeriod()));
        filterCondition.setWorkflowButtons(this.getWorkflowButtons(context.getTaskId(), context.getFlowNodeCode(), context.getPeriod()));
        filterCondition.setUploadStates(this.getUploadStates(context.getTaskId(), context.getFlowNodeCode()));
        filterCondition.setReportDimensionParam(this.buildReportDimensionParam(context.getTaskId(), curPeriod));
        return filterCondition;
    }

    public TodoTableDataInfoImpl getTodoTableData(TodoTableDataContext context) {
        List todoTasks = this.todoSinglePeriodDao_2_0.getTodoTask(context);
        ArrayList<TodoTableDataImpl> tableDataList = new ArrayList<TodoTableDataImpl>();
        for (TodoTaskImpl todoTask : todoTasks) {
            tableDataList.add(this.buildTodoTableData(todoTask, context));
        }
        int filterTaskTodoQuantity = this.todoSinglePeriodDao_2_0.getFilterTaskTodoQuantity(context);
        return new TodoTableDataInfoImpl(filterTaskTodoQuantity, tableDataList);
    }

    private List<PeriodItem> buildPeriodParam(String taskId) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskId);
        List periods = this.todoSinglePeriodDao_2_0.getPeriodsWithTodo(taskId);
        return periods.stream().filter(period -> period != null && !period.isEmpty()).map(period -> new PeriodItem(period, this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(period))).sorted(Comparator.comparing(PeriodItem::getCode, Comparator.reverseOrder())).collect(Collectors.toList());
    }

    private List<EntityCaliberItem> buildEntityCaliberParam(String taskId) {
        TaskOrgLinkListStream taskOrgLinkListStream = this.runTimeViewController.listTaskOrgLinkStreamByTask(taskId);
        List taskOrgLinkDefines = taskOrgLinkListStream.auth().getList();
        if (taskOrgLinkDefines == null || taskOrgLinkDefines.isEmpty()) {
            return new ArrayList<EntityCaliberItem>();
        }
        return taskOrgLinkDefines.stream().map(e -> new EntityCaliberItem(e.getEntity(), this.entityMetaService.queryEntity(e.getEntity()).getTitle())).collect(Collectors.toList());
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

    private TodoTableDataImpl buildTodoTableData(TodoTaskImpl todoTask, TodoTableDataContext context) {
        Date parseDate;
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskId());
        IPeriodRow curPeriod = this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getCurPeriod();
        String period = context.getPeriod() == null || context.getPeriod().isEmpty() ? curPeriod.getCode() : context.getPeriod();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskId());
        FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(context.getTaskId(), period);
        TodoTableDataImpl tableData = new TodoTableDataImpl();
        TableDataShowText showText = new TableDataShowText();
        TableDataActualParam actualParam = new TableDataActualParam();
        showText.setPeriod(this.periodEngineService.getPeriodAdapter().getPeriodProvider(taskDefine.getDateTime()).getPeriodTitle(todoTask.getPeriod()));
        actualParam.setPeriod(todoTask.getPeriod());
        IEntityRow targetEntityRow = this.getTargetEntityRow(formSchemeDefine, context.getEntityCaliber(), period, todoTask.getUnit());
        showText.setUnit(targetEntityRow != null ? targetEntityRow.getTitle() : todoTask.getUnit());
        actualParam.setUnit(todoTask.getUnit());
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            FormDefine targetFormDefine = this.getTargetFormDefine(formSchemeDefine, todoTask.getWorkflowObject());
            showText.setWorkflowObject(targetFormDefine != null ? targetFormDefine.getTitle() : todoTask.getWorkflowObject());
            actualParam.setWorkflowObject(todoTask.getWorkflowObject());
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            FormGroupDefine targetFormGroupDefine = this.getTargetFormGroupDefine(formSchemeDefine, todoTask.getWorkflowObject());
            showText.setWorkflowObject(targetFormGroupDefine != null ? targetFormGroupDefine.getTitle() : todoTask.getWorkflowObject());
            actualParam.setWorkflowObject(todoTask.getWorkflowObject());
        }
        IProcessStatus processStatus = this.processMetaDataService.queryStatus(context.getTaskId(), todoTask.getUploadState());
        showText.setUploadState(processStatus.getAlias());
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

    private WorkFlowType transferToOldWorkflowObject(WorkflowObjectType workflowObjectType) {
        switch (workflowObjectType) {
            case MAIN_DIMENSION: 
            case MD_WITH_SFR: {
                return WorkFlowType.ENTITY;
            }
            case FORM: {
                return WorkFlowType.FORM;
            }
            case FORM_GROUP: {
                return WorkFlowType.GROUP;
            }
        }
        return null;
    }

    private List<WorkFlowNode> buildWorkFlowNode(String taskKey, List<IUserTask> userTasks, Workflow2EngineCompatibleExtend workflow2EngineCompatibleExtend, boolean isDefaultEngine) {
        WorkflowDefineTemplate workflowDefineTemplate = workflow2EngineCompatibleExtend.isSubmitEnabled(taskKey) ? WorkflowDefineTemplate.SUBMIT_WORKFLOW : WorkflowDefineTemplate.STANDARD_WORKFLOW;
        String backDesc = workflow2EngineCompatibleExtend.getBackDesc(taskKey);
        String returnDesc = workflow2EngineCompatibleExtend.getReturnDesc(taskKey);
        String uploadDesc = workflow2EngineCompatibleExtend.getUploadDesc(taskKey);
        boolean isBackDescEnable = backDesc != null;
        boolean isReturnDescEnable = returnDesc != null;
        boolean isUploadDescEnable = uploadDesc != null;
        ArrayList<WorkFlowNode> workFlowNodes = new ArrayList<WorkFlowNode>();
        for (IUserTask userTask : userTasks) {
            String workflowNodeCode = userTask.getCode();
            if (workflowNodeCode.equals("tsk_start")) continue;
            WorkFlowNode workFlowNode = new WorkFlowNode();
            workFlowNode.setCode(userTask.getCode());
            workFlowNode.setTitle(userTask.getAlias());
            switch (workflowNodeCode) {
                case "tsk_submit": {
                    workFlowNode.setOpenComment(isBackDescEnable || isReturnDescEnable);
                    workFlowNode.setCommentType(isBackDescEnable ? (isReturnDescEnable ? CommentType.BOTH : CommentType.ONLY_RETURN_REVIEW) : (isReturnDescEnable ? CommentType.ONLY_RETURN : CommentType.NEITHER));
                    break;
                }
                case "tsk_upload": {
                    workFlowNode.setOpenComment(!workflowDefineTemplate.equals((Object)WorkflowDefineTemplate.SUBMIT_WORKFLOW) && isReturnDescEnable);
                    break;
                }
                case "tsk_audit": {
                    workFlowNode.setOpenComment(isUploadDescEnable);
                }
            }
            workFlowNodes.add(workFlowNode);
        }
        String reportNodeRetrieveOrReturnExtend = workflow2EngineCompatibleExtend.getReportNodeRetrieveOrReturn(taskKey);
        RetrieveStrategy reportNodeRetrieveOrReturn = reportNodeRetrieveOrReturnExtend == null ? null : RetrieveStrategy.valueOf((String)reportNodeRetrieveOrReturnExtend);
        boolean isApplyReturnEnable = false;
        if (reportNodeRetrieveOrReturn != null) {
            isApplyReturnEnable = reportNodeRetrieveOrReturn.equals((Object)RetrieveStrategy.APPLY_RETURN);
        }
        if (isDefaultEngine && isApplyReturnEnable) {
            WorkFlowNode requestRejectNode = new WorkFlowNode();
            requestRejectNode.setCode(TodoNodeType.REQUEST_REJECT.title);
            requestRejectNode.setTitle("\u7533\u8bf7\u9000\u56de");
            requestRejectNode.setOpenComment(true);
            workFlowNodes.add(requestRejectNode);
        }
        return workFlowNodes;
    }

    private List<WorkflowButton> getWorkflowButtons(String taskKey, String workflowNode, String period) {
        if (workflowNode.equals(TodoNodeType.REQUEST_REJECT.title)) {
            return new ArrayList<WorkflowButton>();
        }
        SchemePeriodLinkDefine schemePeriodLinkByPeriodAndTask = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (schemePeriodLinkByPeriodAndTask == null) {
            return new ArrayList<WorkflowButton>();
        }
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(schemePeriodLinkByPeriodAndTask.getSchemeKey());
        String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskKey);
        Workflow2EngineCompatibleExtend workflow2EngineCompatibleExtend = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
        List userActions = this.processMetaDataService.queryAllActions(taskKey, workflowNode);
        ArrayList<WorkflowButton> workflowButtons = new ArrayList<WorkflowButton>();
        for (IUserAction userAction : userActions) {
            if (userAction.getCode().equals("act_retrieve") || userAction.getCode().equals("act_apply_reject")) continue;
            WorkflowButton workflowButton = new WorkflowButton();
            workflowButton.setCode(userAction.getCode());
            workflowButton.setTitle(userAction.getAlias());
            workflowButton.setShowBatch(true);
            workflowButton.setActionParam((Object)this.converterUtil.buildActionParam(userAction.getCode(), userAction.getProperties(), workflowNode, workflow2EngineCompatibleExtend, formSchemeDefine));
            workflowButtons.add(workflowButton);
        }
        return workflowButtons;
    }

    private List<UploadState> getUploadStates(String taskKey, String workflowNode) {
        if (workflowNode.equals(TodoNodeType.REQUEST_REJECT.title)) {
            String workflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(taskKey);
            DefaultProcessConfig defaultProcessConfig = this.defaultProcessDesignService.queryDefaultProcessConfig(workflowDefine);
            ReportNodeConfig reportNodeConfig = defaultProcessConfig.getReportNodeConfig();
            AuditNodeConfig auditNodeConfig = defaultProcessConfig.getAuditNodeConfig();
            Map reportNodeActions = reportNodeConfig.getActions();
            Map auditNodeActions = auditNodeConfig.getActions();
            ArrayList<UploadState> uploadStates = new ArrayList<UploadState>();
            UploadState uploadState = new UploadState();
            uploadState.setCode("reported");
            uploadState.setTitle(((ActionInfo)reportNodeActions.get("act_upload")).getStateName());
            uploadStates.add(uploadState);
            UploadState confirmState = new UploadState();
            confirmState.setCode("confirmed");
            confirmState.setTitle(((ActionInfo)auditNodeActions.get("act_confirm")).getStateName());
            uploadStates.add(confirmState);
            return uploadStates;
        }
        List processStatuses = this.processMetaDataService.queryUserTaskStatus(taskKey, workflowNode);
        ArrayList<UploadState> uploadStates = new ArrayList<UploadState>();
        for (IProcessStatus processStatus : processStatuses) {
            UploadState uploadState = new UploadState();
            uploadState.setCode(processStatus.getCode());
            uploadState.setTitle(processStatus.getAlias());
            uploadStates.add(uploadState);
        }
        return uploadStates;
    }

    private List<EntityCaliberItem> filterEntityCaliber(List<EntityCaliberItem> entityCaliberItems, String taskId, String period) {
        return entityCaliberItems.stream().filter(item -> this.todoSinglePeriodDao_2_0.isTodoExistWithEntityCaliber(taskId, period, item.getCode())).collect(Collectors.toList());
    }

    private List<WorkFlowNode> filterTabs(String taskId, String entityCaliber, String period, List<WorkFlowNode> workFlowNodes) {
        TodoTabsQuantityContextImpl context = new TodoTabsQuantityContextImpl();
        context.setTaskId(taskId);
        context.setEntityCaliber(entityCaliber);
        context.setPeriod(period);
        context.setWorkflowNodes(workFlowNodes.stream().map(WorkFlowNode::getCode).collect(Collectors.toList()));
        Map<String, Integer> todoTabsQuantity = this.getTodoTabsQuantity((TodoTabsQuantityContext)context);
        return workFlowNodes.stream().filter(e -> (Integer)todoTabsQuantity.get(e.getCode()) > 0).collect(Collectors.toList());
    }
}

