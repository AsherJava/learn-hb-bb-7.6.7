/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.message.internal.ParticipantService
 *  com.jiuqi.nr.bpm.custom.bean.WorkFlowAction
 *  com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.setting.service.WorkflowSettingService
 *  com.jiuqi.nr.bpm.upload.WorkflowStatus
 *  com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil
 *  com.jiuqi.nr.bpm.upload.utils.DefaultButton
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskOrgLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataExtend
 *  com.jiuqi.nr.workflow2.todo.service.TodoExtendQueryService
 *  com.jiuqi.util.StringUtils
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.todo.internal;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.message.internal.ParticipantService;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.bpm.upload.utils.DefaultButton;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskOrgLinkDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.todo.TodoController;
import com.jiuqi.nr.todo.TodoRepository;
import com.jiuqi.nr.todo.TodoService;
import com.jiuqi.nr.todo.bean.TodoParam;
import com.jiuqi.nr.todo.bean.TodoResult;
import com.jiuqi.nr.todo.entity.QueryUnitDetailCommand;
import com.jiuqi.nr.todo.entity.TodoActionCommand;
import com.jiuqi.nr.todo.entity.TodoVO;
import com.jiuqi.nr.todo.inter.ITodoResult;
import com.jiuqi.nr.todo.internal.EntityQueryManager;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoTableDataExtend;
import com.jiuqi.nr.workflow2.todo.service.TodoExtendQueryService;
import com.jiuqi.util.StringUtils;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TodoControllerImpl
implements TodoController {
    private static final Logger logger = LoggerFactory.getLogger(TodoControllerImpl.class);
    private final TodoService todoService;
    private final TodoRepository todoRepository;
    private final ParticipantService participantService;
    private final EntityQueryManager entityQueryManager;
    @Autowired(required=false)
    Map<String, ITodoResult> todoResult;
    @Autowired
    private TodoExtendQueryService todoExtendQueryService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    protected IWorkflow workflow;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private ActionAndStateUtil actionAndStateUtil;
    @Autowired
    private CustomWorkFolwService customWorkFolwService;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private ITaskService taskService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Value(value="${jiuqi.nr.todo.version:2.0}")
    private String todoVersion;

    public TodoControllerImpl(TodoService todoService, TodoRepository todoRepository, ParticipantService participantService, @Qualifier(value="todo.entityManager") EntityQueryManager entityQueryManager) {
        this.todoService = todoService;
        this.todoRepository = todoRepository;
        this.participantService = participantService;
        this.entityQueryManager = entityQueryManager;
    }

    @Override
    @GetMapping(value={"/todo"})
    public List<TodoVO> list() {
        String userId = NpContextHolder.getContext().getUserId();
        if (userId == null) {
            return null;
        }
        List participantId = this.participantService.findParticipantId(userId);
        List<TodoVO> todoVOList = this.todoRepository.list(participantId);
        ITodoResult map = this.map();
        if (null != map) {
            List<TodoVO> todoList = map.getTodoList(todoVOList);
            return todoList;
        }
        return todoVOList;
    }

    @Override
    @PostMapping(value={"/todo/action"})
    public void performAction(@RequestBody TodoActionCommand command) {
        String userId = NpContextHolder.getContext().getUserId();
        this.todoService.doAction(command.getMessageId(), command.getAppName(), userId, command.getActionId(), command.getExtendsParams());
    }

    @Override
    @PostMapping(value={"/todo/unitInfo"})
    public List<Map<String, String>> getUnitDetail(@RequestBody QueryUnitDetailCommand command) {
        try {
            return this.entityQueryManager.getUnitDetails(command.getFormSchemeId(), command.getUnitIds(), command.getPeriod());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    @PostMapping(value={"/todo/fromorgroupinfo"})
    public List<Map<String, String>> getFormOrGroupDetail(@RequestBody QueryUnitDetailCommand command) {
        try {
            return this.entityQueryManager.getFormOrGroupDetails(command.getFormSchemeId(), command.getFormOrGroups());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    @GetMapping(value={"/todo/check"})
    public boolean checkNewTodo(@RequestParam String latestTimestamp) {
        String userId = NpContextHolder.getContext().getUserId();
        long latestTime = Long.parseLong(latestTimestamp);
        return this.todoService.existNewTodo(userId, latestTime);
    }

    @Override
    @PostMapping(value={"/todo/todoparam"})
    public TodoResult queryTodoParam(@RequestBody TodoParam todoParam) {
        TodoResult todoResult = new TodoResult();
        todoResult = this.todoService.queryTodoParam(todoParam);
        return todoResult;
    }

    private ITodoResult map() {
        Iterator<Map.Entry<String, ITodoResult>> iterator;
        if (this.todoResult != null && (iterator = this.todoResult.entrySet().iterator()).hasNext()) {
            Map.Entry<String, ITodoResult> todo = iterator.next();
            ITodoResult value = todo.getValue();
            return value;
        }
        return null;
    }

    @Override
    public List<TodoVO> todoList(int type) {
        return this.oldTodoListMethod(type);
    }

    @Override
    public List<TodoVO> todoList(String taskKey, String period, int type) {
        if (this.todoVersion.equals("2.0")) {
            TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
            FormSchemeDefine formSchemeDefine = this.getFormSchemeDefine(taskKey, period);
            List reportDimension = this.taskService.getReportDimension(taskKey);
            ArrayList<TodoVO> todoVOList = new ArrayList<TodoVO>();
            List taskOrgLinkDefines = this.runTimeViewController.listTaskOrgLinkByTask(taskKey);
            for (TaskOrgLinkDefine taskOrgLinkDefine : taskOrgLinkDefines) {
                String entityCaliber = taskOrgLinkDefine.getEntity();
                List todoTaskExtendInfo = this.todoExtendQueryService.getTodoTaskExtendInfo(taskKey, period, entityCaliber);
                boolean isContainsCorporate = true;
                String corporateValue = null;
                for (TodoTableDataExtend data : todoTaskExtendInfo) {
                    Date parsedDate;
                    String formSchemeKey = Objects.requireNonNull(formSchemeDefine).getKey();
                    String unitId = data.getTableDataActualParamExtend().getUnit();
                    String formOrGroupKey = data.getTableDataActualParamExtend().getWorkflowObject();
                    String adjust = data.getTableDataActualParamExtend().getAdjust();
                    WorkFlowType startType = this.workflow.queryStartType(formSchemeKey);
                    if (isContainsCorporate && corporateValue == null) {
                        for (DataDimension dimension : reportDimension) {
                            if (this.isCorporate(taskDefine, dimension)) {
                                IEntityTable entityTable = this.getEntityTable(entityCaliber, period, taskDefine.getDateTime(), unitId);
                                IEntityRow targetRow = entityTable.findByEntityKey(unitId);
                                corporateValue = targetRow.getAsString(dimension.getDimAttribute());
                                break;
                            }
                            isContainsCorporate = false;
                        }
                    }
                    String taskCode = data.getTableDataActualParamExtend().getWorkflowNode();
                    String id = this.workflow.getMessageId(formSchemeKey, period, unitId, adjust, formOrGroupKey, formOrGroupKey, startType, taskCode, corporateValue);
                    WorkFlowType workFlowType = formSchemeDefine.getFlowsSetting().getWordFlowType();
                    String workflowObjectType = "";
                    if (workFlowType.equals((Object)WorkFlowType.ENTITY)) {
                        workflowObjectType = "entity";
                    } else if (workFlowType.equals((Object)WorkFlowType.FORM)) {
                        workflowObjectType = "from";
                    } else if (workFlowType.equals((Object)WorkFlowType.GROUP)) {
                        workflowObjectType = "group";
                    }
                    HashMap<String, Object> params = new HashMap<String, Object>();
                    params.put("period", period);
                    params.put("unitName", data.getTableDataShowText().getUnit());
                    params.put("formSchemeId", formSchemeKey);
                    params.put("type", workflowObjectType);
                    params.put("operator", NpContextHolder.getContext().getUserId());
                    params.put("periodTitle", data.getTableDataShowText().getPeriod());
                    params.put("unitId", unitId);
                    params.put("action", this.getActionTitle(formSchemeDefine, data.getTableDataActualParamExtend().getUploadState(), formSchemeDefine.getFlowsSetting().isUnitSubmitForCensorship()));
                    params.put("actionId", data.getTableDataActualParamExtend().getUploadState());
                    params.put("formSchemeTitle", formSchemeDefine.getTitle());
                    params.put("taskTitle", taskDefine.getTitle());
                    params.put("taskId", taskKey);
                    params.put("entityCaliber", data.getTableDataActualParamExtend().getEntityCaliber());
                    if (workFlowType.equals((Object)WorkFlowType.FORM)) {
                        params.put("reportName", data.getTableDataShowText().getWorkflowObject());
                        params.put("reportId", data.getTableDataActualParamExtend().getWorkflowObject());
                    } else if (workFlowType.equals((Object)WorkFlowType.GROUP)) {
                        params.put("groupName", data.getTableDataShowText().getWorkflowObject());
                        params.put("reportId", data.getTableDataActualParamExtend().getWorkflowObject());
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    try {
                        parsedDate = dateFormat.parse(data.getTableDataShowText().getTime());
                    }
                    catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    TodoVO vo = new TodoVO();
                    vo.setId(id);
                    vo.setParams(params);
                    vo.setCreateTime(new Timestamp(parsedDate.getTime()));
                    todoVOList.add(vo);
                }
            }
            return todoVOList;
        }
        return this.oldTodoListMethod(type);
    }

    private List<TodoVO> oldTodoListMethod(int type) {
        String userId = NpContextHolder.getContext().getUserId();
        if (userId == null) {
            return null;
        }
        List participantId = this.participantService.findParticipantId(userId);
        List<TodoVO> todoVOList = this.todoService.todoList(participantId, type);
        ITodoResult map = this.map();
        if (null != map) {
            List<TodoVO> todoList = map.getTodoList(todoVOList);
            return todoList;
        }
        return todoVOList;
    }

    private FormSchemeDefine getFormSchemeDefine(String taskId, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine;
        try {
            schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        return schemePeriodLinkDefine == null ? null : this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }

    private boolean isCorporate(TaskDefine taskDefine, DataDimension dimension) {
        String dimAttribute = dimension.getDimAttribute();
        List reportDimension = this.taskService.getReportDimension(taskDefine.getKey());
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = reportDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    private String getActionTitle(FormSchemeDefine formSchemeDefine, String actionCode, boolean isUnitSubmitForCensorship) {
        String formSchemeKey = Objects.requireNonNull(formSchemeDefine).getKey();
        boolean defaultWorkflow = this.isDefaultWorkflow(formSchemeDefine.getKey());
        if (defaultWorkflow) {
            if (actionCode.equals("start")) {
                actionCode = isUnitSubmitForCensorship ? DefaultButton.NOSONGSHEN.getActionCode() : DefaultButton.NOSHANGBAO.getActionCode();
            }
            return this.actionAndStateUtil.getActionNameByActionCode(formSchemeKey, actionCode);
        }
        List allWorkflowAction = this.customWorkFolwService.getAllWorkflowAction();
        String finalActionCode = actionCode;
        Optional<WorkFlowAction> targetAction = allWorkflowAction.stream().filter(e -> e.getActionCode().equals(finalActionCode)).findFirst();
        return targetAction.isPresent() ? targetAction.get().getActionTitle() : "";
    }

    private boolean isDefaultWorkflow(String formSchemeKey) {
        WorkflowStatus queryFlowType;
        boolean defaultWorkflow = false;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        FlowsType flowsType = formSchemeDefine.getFlowsSetting().getFlowsType();
        if (FlowsType.DEFAULT.equals((Object)flowsType) && WorkflowStatus.DEFAULT.equals((Object)(queryFlowType = this.workflowSettingService.queryFlowType(formSchemeKey)))) {
            defaultWorkflow = true;
        }
        return defaultWorkflow;
    }

    private IEntityDefine getContextMainEntityDefine(TaskDefine taskDefine) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        if (StringUtils.isNotEmpty((String)entityId)) {
            return this.entityMetaService.queryEntity(entityId);
        }
        return this.entityMetaService.queryEntity(taskDefine.getDw());
    }

    private IEntityTable getEntityTable(String entityId, String period, String periodView, String unitCode) {
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        dimensionValueSet.setValue(dimensionName, (Object)unitCode);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.sorted(true);
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

