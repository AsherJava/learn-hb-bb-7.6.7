/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.i18n.helper.I18nHelper
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityAuthorityService
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.i18n.helper.I18nHelper;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.service.IActionAlias;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.de.dataflow.util.PdfPrint;
import com.jiuqi.nr.bpm.de.dataflow.util.ProcessTrackExcel;
import com.jiuqi.nr.bpm.de.dataflow.util.ProcessTrackPrint;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.dao.impl.WorkflowSettingDao;
import com.jiuqi.nr.bpm.setting.pojo.ProcessExcelParam;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackExcelInfo;
import com.jiuqi.nr.bpm.setting.pojo.ProcessTrackPrintData;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeParam;
import com.jiuqi.nr.bpm.setting.pojo.ShowNodeResult;
import com.jiuqi.nr.bpm.setting.pojo.ShowResult;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.utils.AdvancedChineseNumberSort;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class ShowProcess {
    private static final Logger logger = LoggerFactory.getLogger(ShowProcess.class);
    @Autowired
    private WorkflowSettingDao workflowSettingDao;
    @Resource
    private UserService<User> userService;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    List<ActorStrategy> actorStrategy;
    @Autowired
    ProcessTrackExcel processTrackExcel;
    @Autowired
    ProcessTrackPrint processTrackPrint;
    @Autowired
    SettingUtil settingMethod;
    @Autowired
    IWorkflow workflow;
    @Autowired
    IActionAlias actionAlias;
    @Autowired
    BusinessGenerator businessGenerator;
    @Autowired
    DimensionUtil dimensionUtil;
    @Autowired
    IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IEntityAuthorityService entityAuthorityService;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    @Qualifier(value="process_btn")
    private I18nHelper i18nHelper;
    @Autowired
    private ActionAndStateUtil actionAndStateUtil;

    private String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (language == null || language.equals("")) {
            return "zh";
        }
        return language;
    }

    public ShowResult showWorkflow(ShowNodeParam nodeParam) {
        ShowResult showResult = new ShowResult();
        if (nodeParam == null) {
            return null;
        }
        LinkedList<ShowNodeResult> nodes = new LinkedList<ShowNodeResult>();
        LinkedList<String> lines = new LinkedList<String>();
        ArrayList<WorkFlowLine> worklineList = new ArrayList<WorkFlowLine>();
        ShowNodeResult node = null;
        FormSchemeDefine formScheme = null;
        try {
            UploadStateNew uploadStateNew;
            List<UploadRecordNew> hisUploadActionsOrigin;
            formScheme = this.settingMethod.getFormScheme(nodeParam.getFormSchemeKey());
            Map<String, DimensionValue> dimensionSetMap = nodeParam.getDimensionSetMap();
            boolean canRead = this.canRead(formScheme, dimensionSetMap);
            if (!canRead) {
                return showResult;
            }
            DimensionCollection dimensionCollection = this.workFlowDimensionBuilder.buildDimensionCollection(formScheme.getTaskKey(), dimensionSetMap);
            List dimensionCombinations = dimensionCollection.getDimensionCombinations();
            ArrayList<DimensionValueSet> dims = new ArrayList<DimensionValueSet>();
            for (DimensionCombination dimensionCombination : dimensionCombinations) {
                dims.add(dimensionCombination.toDimensionValueSet());
            }
            DimensionValueSet dimension = this.dimensionUtil.mergeDimensionValueSet(dims, formScheme.getKey());
            WorkflowSettingDefine workflowSetting = this.workflowSettingDao.getWorkflowSettingByFormSchemeKey(nodeParam.getFormSchemeKey());
            showResult.setWorkflowId(workflowSetting.getWorkflowId());
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(nodeParam.getFormSchemeKey());
            showResult.setDefaultWorkflow(defaultWorkflow);
            if (formScheme != null && defaultWorkflow) {
                boolean enableConfirm = this.enableConfirm(nodeParam.getFormSchemeKey());
                showResult.setConfrimed(enableConfirm);
            }
            List<UploadRecordNew> hisUploadActions = hisUploadActionsOrigin = this.queryUploadStateService.queryUploadHistoryStates(formScheme.getKey(), dimension, nodeParam.getFormKey(), nodeParam.getGroupKey());
            if (hisUploadActionsOrigin != null && hisUploadActionsOrigin.size() > 0) {
                hisUploadActions = hisUploadActionsOrigin.stream().sorted(Comparator.comparing(UploadRecordNew::getTime)).collect(Collectors.toList());
            }
            ArrayList<String> nodeList = new ArrayList<String>();
            if (hisUploadActions.size() > 0) {
                for (UploadRecordNew uploadRecordNew : hisUploadActions) {
                    Optional role;
                    Iterator<WorkFlowLine> workflowActionByCode;
                    nodeList.add(uploadRecordNew.getTaskId());
                    node = new ShowNodeResult();
                    List<WorkFlowLine> workflowLineList = this.settingMethod.getWorkFlowLinesByEndNode(uploadRecordNew.getTaskId(), formScheme.getKey());
                    if (workflowLineList.size() > 0) {
                        for (WorkFlowLine workFlowLine : workflowLineList) {
                            if (!workFlowLine.getBeforeNodeID().startsWith("StartEvent_")) continue;
                            worklineList.add(workFlowLine);
                            lines.add(workFlowLine.getId());
                            lines.add(workFlowLine.getBeforeNodeID());
                            lines.add(workFlowLine.getAfterNodeID());
                        }
                    }
                    if ((workflowActionByCode = this.settingMethod.getWorkFlowAction(formScheme.getKey(), uploadRecordNew.getTaskId(), uploadRecordNew.getAction())) != null) {
                        String id = ((WorkFlowAction)((Object)workflowActionByCode)).getId();
                        List<WorkFlowLine> workflowLinesByPreTask = this.settingMethod.getWorkflowLinesByPreTask(uploadRecordNew.getTaskId(), id);
                        if (workflowLinesByPreTask.size() > 0) {
                            for (WorkFlowLine workFlowLine : workflowLinesByPreTask) {
                                lines.add(workFlowLine.getAfterNodeID());
                                lines.add(workFlowLine.getId());
                                worklineList.add(workFlowLine);
                            }
                        }
                    }
                    WorkFlowAction workflowAction = this.settingMethod.getWorkFlowAction(formScheme.getKey(), uploadRecordNew.getTaskId(), uploadRecordNew.getAction());
                    node.setReturnType(this.getReturnTypeTitle(nodeParam.getFormSchemeKey(), uploadRecordNew.getReturnType()));
                    if (workflowAction != null && workflowAction.getId() != null) {
                        Optional role2;
                        String roleKey;
                        node.setActionCode(workflowAction.getActionCode());
                        node.setActionName(workflowAction.getActionTitle());
                        node.setActionState(workflowAction.getStateName());
                        node.setDesc(uploadRecordNew.getCmt());
                        node.setNodeId(workflowAction.getNodeid());
                        String operator = uploadRecordNew.getOperator();
                        if (operator != null) {
                            node.setUser(operator);
                        } else {
                            node.setUser("admin");
                        }
                        node.setTime(uploadRecordNew.getTime());
                        WorkFlowNodeSet workFlowNode = this.settingMethod.getWorkFlowNodeSet(workflowAction.getNodeid(), formScheme.getKey());
                        if (workFlowNode != null) {
                            node.setNodeName(workFlowNode.getTitle());
                        }
                        if ((roleKey = uploadRecordNew.getRoleKey()) != null && !roleKey.isEmpty() && (role2 = this.roleService.get(roleKey)).isPresent()) {
                            node.setRole(((Role)role2.get()).getTitle());
                        }
                        nodes.add(node);
                        continue;
                    }
                    String action = uploadRecordNew.getAction();
                    node.setActionCode(action);
                    String actionName = this.actionName(nodeParam.getFormSchemeKey(), action);
                    node.setActionName(actionName);
                    node.setDesc(uploadRecordNew.getCmt());
                    String taskId = uploadRecordNew.getTaskId();
                    node.setNodeId(taskId);
                    String nodeName = this.getNodeName(formScheme.getKey(), taskId);
                    node.setNodeName(nodeName);
                    node.setTime(uploadRecordNew.getTime());
                    String operator = uploadRecordNew.getOperator();
                    if (operator != null && !operator.isEmpty()) {
                        User user = this.userService.getByUsername(operator);
                        if (user != null) {
                            node.setUser(user.getNickname());
                        } else {
                            node.setUser(operator);
                        }
                    } else {
                        node.setUser("admin");
                    }
                    String roleKey = uploadRecordNew.getRoleKey();
                    if (roleKey != null && !roleKey.isEmpty() && (role = this.roleService.get(roleKey)).isPresent()) {
                        node.setRole(((Role)role.get()).getTitle());
                    }
                    nodes.add(node);
                }
            }
            if ((uploadStateNew = this.queryUploadStateService.queryUploadState(formScheme.getKey(), dimension, nodeParam.getFormKey(), nodeParam.getGroupKey())) != null) {
                String linkid;
                List<WorkFlowLine> workflowLinesByLinkid;
                List<WorkFlowLine> workflowLines;
                node = new ShowNodeResult();
                List<WorkFlowLine> workflowLinesByPreTask = this.settingMethod.getWorkflowLinesByPreTask(uploadStateNew.getTaskId(), formScheme.getKey());
                if (workflowLinesByPreTask.size() > 0) {
                    ArrayList<Boolean> startsWith = new ArrayList<Boolean>();
                    for (WorkFlowLine workFlowLine : workflowLinesByPreTask) {
                        boolean end = workFlowLine.getAfterNodeID().startsWith("End");
                        startsWith.add(end);
                    }
                    for (WorkFlowLine workFlowLine : workflowLinesByPreTask) {
                        if (startsWith.contains(true)) break;
                        lines.add(workFlowLine.getAfterNodeID());
                        lines.add(workFlowLine.getId());
                        worklineList.add(workFlowLine);
                    }
                }
                if ((workflowLines = this.settingMethod.getWorkflowLineByBNodeAndEndNode(nodeList, uploadStateNew.getTaskId(), formScheme.getKey())).size() > 0 && (workflowLinesByLinkid = this.settingMethod.getWorkflowLinesByLinkid(linkid = workflowLines.get(0).getLinkid())).size() > 0) {
                    for (WorkFlowLine workFlowLine : workflowLinesByLinkid) {
                        if (!workFlowLine.getBeforeNodeID().startsWith("Start")) continue;
                        lines.add(workFlowLine.getBeforeNodeID());
                        lines.add(workFlowLine.getId());
                    }
                }
                String nodeName = this.getNodeName(formScheme.getKey(), uploadStateNew.getTaskId());
                node.setNodeName(nodeName);
                ArrayList<String> roleKeys = new ArrayList<String>();
                node.setRoles(this.queryRoles(nodeParam, roleKeys));
                String actors = this.getActors(nodeParam, dimension, roleKeys);
                node.setActors(actors);
                node.setNodeId(uploadStateNew.getTaskId());
                nodes.add(node);
                Set nodeCodes = nodes.stream().map(e -> e.getNodeId()).collect(Collectors.toSet());
                if (!nodeCodes.contains(uploadStateNew.getTaskId())) {
                    nodes.add(node);
                }
            }
            if (!this.getLanguage().equals("zh")) {
                this.changeNodeNameToEN(nodes);
            }
            showResult.setNodeList(nodes);
            showResult.setLineList(lines);
            Collections.reverse(worklineList);
            showResult.setWorklineList(worklineList);
        }
        catch (Exception e1) {
            logger.error(e1.getMessage(), e1);
        }
        return showResult;
    }

    public String getReturnTypeTitle(String formSchemeKey, String returnType) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        String taskId = formScheme.getTaskKey();
        String title = "";
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskId);
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        boolean returnTypeEnable = flowsSetting.isOpenBackType();
        if (returnTypeEnable) {
            String baseDataEntityId = flowsSetting.getBackTypeEntity();
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(baseDataEntityId);
            IEntityTable entityTable = this.getBaseDataEntityTable(entityDefine.getId());
            List allRows = entityTable.getAllRows();
            for (IEntityRow row : allRows) {
                if (!row.getCode().equals(returnType)) continue;
                title = row.getTitle();
                break;
            }
        }
        return title;
    }

    private IEntityTable getBaseDataEntityTable(String entityId) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception var8) {
            Exception e = var8;
            throw new RuntimeException(e);
        }
    }

    private void changeNodeNameToEN(List<ShowNodeResult> nodes) {
        for (ShowNodeResult node : nodes) {
            String message;
            String nodeCode = node.getNodeId();
            if (StringUtils.isNotEmpty((String)nodeCode)) {
                if ("tsk_submit".equals(nodeCode)) {
                    node.setNodeName("submitcheck");
                } else if ("tsk_start".equals(nodeCode)) {
                    node.setNodeName("start");
                } else if ("tsk_upload".equals(nodeCode)) {
                    node.setNodeName("upload");
                } else if ("tsk_audit".equals(nodeCode) || "tsk_audit_after_confirm".equals(nodeCode)) {
                    node.setNodeName("audit");
                }
            }
            if (!StringUtils.isNotEmpty((String)node.getActionCode()) || !StringUtils.isNotEmpty((String)(message = this.i18nHelper.getMessage(node.getActionCode())))) continue;
            node.setActionName(message);
        }
    }

    private String getNodeName(String formSchemeKey, String nodeCode) {
        String nodeTitle = null;
        FormSchemeDefine formScheme = this.settingMethod.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
            if (defaultWorkflow) {
                if (nodeCode != null) {
                    nodeTitle = this.actionAndStateUtil.getNodeNameByCode(formSchemeKey, nodeCode);
                }
            } else {
                WorkFlowNodeSet workFlowNode = this.settingMethod.getWorkFlowNodeSet(nodeCode, formSchemeKey);
                if (workFlowNode != null && workFlowNode.getId() != null) {
                    nodeTitle = workFlowNode.getTitle();
                }
            }
        }
        return nodeTitle;
    }

    public String actionName(String formSchemeKey, String actionCode) {
        String actionName = "";
        Map<String, String> actionCodeAndStateName = this.actionAlias.actionCodeAndActionName(formSchemeKey);
        if (actionCodeAndStateName != null && actionCodeAndStateName.size() > 0) {
            actionName = actionCodeAndStateName.get(actionCode);
        } else if ("act_upload".equals(actionCode) || "cus_upload".equals(actionCode)) {
            actionName = "\u4e0a\u62a5";
        } else if ("act_reject".equals(actionCode) || "cus_reject".equals(actionCode)) {
            actionName = "\u9000\u56de";
        } else if ("act_confirm".equals(actionCode) || "cus_confirm".equals(actionCode)) {
            actionName = "\u786e\u8ba4";
        } else if ("act_submit".equals(actionCode) || "cus_submit".equals(actionCode)) {
            actionName = "\u9001\u5ba1";
        } else if ("act_return".equals(actionCode) || "cus_return".equals(actionCode)) {
            actionName = "\u9000\u5ba1";
        } else if ("act_cancel_confirm".equals(actionCode)) {
            actionName = "\u53d6\u6d88\u786e\u8ba4";
        } else if ("act_retrieve".equals(actionCode)) {
            actionName = "\u53d6\u56de";
        }
        return actionName;
    }

    private String getActors(ShowNodeParam nodeParam, DimensionValueSet dimension, List<String> roleKeys) {
        StringBuffer actorsStr = new StringBuffer();
        HashSet<String> actors = new HashSet<String>();
        Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(nodeParam.getFormSchemeKey());
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(nodeParam.getFormSchemeKey(), dimension, nodeParam.getFormKey(), nodeParam.getGroupKey());
        try {
            List<Object> tasks = new ArrayList();
            boolean defaultWorkflow = this.workflow.isDefaultWorkflow(nodeParam.getFormSchemeKey());
            if (defaultWorkflow) {
                boolean bindProcess = this.workflow.bindProcess(nodeParam.getFormSchemeKey(), dimension, nodeParam.getFormKey(), nodeParam.getGroupKey());
                if (bindProcess) {
                    tasks = runtimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
                }
            } else {
                Optional<ProcessInstance> instance = runtimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false, null);
                if (!Optional.empty().equals(instance)) {
                    tasks = runtimeService.queryTasks(instance.get().getId());
                }
            }
            if (tasks.size() > 0) {
                for (Task task : tasks) {
                    Optional<UserTask> userTask = deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), nodeParam.getFormSchemeKey());
                    List actorStrategyType = userTask.map(usertask -> usertask.getActorStrategies()).orElse(null);
                    if (this.actorStrategy.size() <= 0) continue;
                    for (ActorStrategyInstance actorStrategyInstance : actorStrategyType) {
                        String type = actorStrategyInstance.getType();
                        for (ActorStrategy ai : this.actorStrategy) {
                            Set<String> actor;
                            if (!ai.getType().equals(type)) continue;
                            Set<String> actorId = ai.getActors((BusinessKeyInfo)businessKey, actorStrategyInstance.getParameterJson(), task);
                            if (roleKeys != null && roleKeys.size() > 0) {
                                ArrayList userIds = new ArrayList();
                                for (String roleKey : roleKeys) {
                                    List identityIdByRole = this.roleService.getIdentityIdByRole(roleKey);
                                    if (identityIdByRole == null || identityIdByRole.size() <= 0) continue;
                                    userIds.addAll(identityIdByRole);
                                }
                                if (userIds != null && userIds.size() > 0) {
                                    actorId.retainAll(userIds);
                                }
                            }
                            if ((actor = this.actors(actorId)).isEmpty()) continue;
                            actors.addAll(actor);
                        }
                    }
                }
            }
            if (!actors.isEmpty()) {
                actors.forEach(e -> actorsStr.append(",").append((String)e));
            }
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
        return actorsStr.length() > 0 ? actorsStr.substring(1) : "";
    }

    private Set<String> actors(Set<String> actors) {
        HashSet<String> actor = new HashSet<String>();
        if (actors != null) {
            for (String id : actors) {
                User user1 = this.userService.get(id);
                if (user1 == null || !user1.isEnabled()) continue;
                actor.add(user1.getNickname());
            }
        }
        return actor;
    }

    public void exportExcel(HttpServletResponse response, ProcessExcelParam processExcelParam) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(processExcelParam.getFormSchemeKey());
        boolean canRead = this.canRead(formScheme, processExcelParam.getDimensionSetMap());
        if (!canRead) {
            return;
        }
        this.processTrackExcel.excelData(response, processExcelParam.getList());
    }

    public ProcessTrackPrintData printProcessTrack(List<ProcessTrackExcelInfo> list) {
        ProcessTrackPrintData processTrackPrintData = new ProcessTrackPrintData();
        byte[] bytes = PdfPrint.exportPdf(list, false);
        processTrackPrintData = new ProcessTrackPrintData("PDF\u8868\u683c", bytes);
        return processTrackPrintData;
    }

    private String queryRoles(ShowNodeParam nodeParam, List<String> roleKeys) {
        ArrayList<String> roleNames = new ArrayList<String>();
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(nodeParam.getFormSchemeKey());
        if (!defaultWorkflow) {
            Map<Task, String> taskStringMap;
            Map<String, DimensionValue> dimensionSetMap = nodeParam.getDimensionSetMap();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSetMap);
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(nodeParam.getFormSchemeKey(), dimensionValueSet, nodeParam.getFormKey(), nodeParam.getGroupKey());
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(nodeParam.getFormSchemeKey());
            RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            Actor candicateActor = Actor.fromNpContext();
            Optional<ProcessInstance> instance = runtimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false, null);
            if (!Optional.empty().equals(instance) && (taskStringMap = runtimeService.querySignTaskByProcessInstance(instance.get().getId(), candicateActor)) != null && taskStringMap.size() > 0) {
                for (Map.Entry<Task, String> entry : taskStringMap.entrySet()) {
                    String value = entry.getValue();
                    Optional role = this.roleService.get(value);
                    if (!role.isPresent() || roleKeys.contains(value)) continue;
                    roleNames.add(((Role)role.get()).getTitle());
                    roleKeys.add(((Role)role.get()).getId());
                }
                AdvancedChineseNumberSort.sort(roleNames);
            }
        }
        return roleNames.size() > 0 ? String.join((CharSequence)"\u3001", roleNames) : "";
    }

    private boolean canRead(FormSchemeDefine formScheme, Map<String, DimensionValue> dimensionSetMap) {
        try {
            String mainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
            DimensionValue dimensionValue = dimensionSetMap.get(mainDimName);
            DimensionValue periodDimensionValue = dimensionSetMap.get("DATATIME");
            String entityKeyData = dimensionValue.getValue();
            Date time = this.periodEngineService.getPeriodAdapter().getPeriodProvider(formScheme.getDateTime()).getPeriodDateRegion(periodDimensionValue.getValue())[1];
            String entityId = com.jiuqi.util.StringUtils.isNotEmpty((String)DsContextHolder.getDsContext().getContextEntityId()) ? DsContextHolder.getDsContext().getContextEntityId() : formScheme.getDw();
            boolean canWriteEntity = this.entityAuthorityService.canReadEntity(entityId, entityKeyData, null, time);
            if (canWriteEntity) {
                return true;
            }
        }
        catch (UnauthorizedEntityException e) {
            logger.error("\u6743\u9650\u4e0d\u7b26\u5408" + (Object)((Object)e));
        }
        catch (ParseException e) {
            logger.error("\u65f6\u671f\u7c7b\u578b\u8f6c\u6362\u62a5\u9519" + e);
        }
        return false;
    }

    public boolean enableConfirm(String formSchemeKey) {
        WorkflowSettingService workflowSettingService;
        WorkflowStatus queryFlowType;
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(formSchemeKey);
        FlowsType flowsType = formSchemeDefine.getFlowsSetting().getFlowsType();
        if (FlowsType.DEFAULT.equals((Object)flowsType) && WorkflowStatus.DEFAULT.equals((Object)(queryFlowType = (workflowSettingService = (WorkflowSettingService)BeanUtil.getBean(WorkflowSettingService.class)).queryFlowType(formSchemeKey)))) {
            return formSchemeDefine.getFlowsSetting().isDataConfirm();
        }
        return false;
    }
}

