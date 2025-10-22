/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.authz2.Authz2Constants
 *  com.jiuqi.np.authz2.privilege.service.PrivilegeService
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.authz2.service.SystemIdentityService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.user.SystemUser
 *  com.jiuqi.np.user.User
 *  com.jiuqi.np.user.service.SystemUserService
 *  com.jiuqi.np.user.service.UserService
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 *  com.jiuqi.nr.definition.auth.authz2.ResourceType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormGroupDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.snapshot.DataVersion
 *  com.jiuqi.nr.snapshot.ISnapshotCompare
 *  com.jiuqi.nr.snapshot.SnapshotService
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.org.OrgAuthFindDTO
 *  com.jiuqi.va.domain.org.OrgDTO
 *  com.jiuqi.va.domain.org.OrgDataOption$AuthType
 *  com.jiuqi.va.domain.user.UserDO
 *  com.jiuqi.va.feign.client.OrgAuthClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.impl.countersign.group;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.authz2.Authz2Constants;
import com.jiuqi.np.authz2.privilege.service.PrivilegeService;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.authz2.service.SystemIdentityService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.user.SystemUser;
import com.jiuqi.np.user.User;
import com.jiuqi.np.user.service.SystemUserService;
import com.jiuqi.np.user.service.UserService;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataVersionData;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.countersign.CounterSignParam;
import com.jiuqi.nr.bpm.impl.process.ProcessUserTask;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessAction;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.consts.TaskEnum;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.auth.authz2.ResourceType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.snapshot.DataVersion;
import com.jiuqi.nr.snapshot.ISnapshotCompare;
import com.jiuqi.nr.snapshot.SnapshotService;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.org.OrgAuthFindDTO;
import com.jiuqi.va.domain.org.OrgDTO;
import com.jiuqi.va.domain.org.OrgDataOption;
import com.jiuqi.va.domain.user.UserDO;
import com.jiuqi.va.feign.client.OrgAuthClient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class CounterParamBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String INIIALVERSIONID = "00000000-0000-0000-0000-000000000000";
    private static final String ROLE = "role";
    private static final String USER = "user";
    @Autowired
    CustomWorkFolwService customWorkFolwService;
    @Autowired
    List<ActorStrategy> actorStrategy;
    @Autowired
    RoleService roleService;
    @Autowired
    ActionMethod actionMethod;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    ProcessEngineProvider processEngineProvider;
    @Autowired
    private IRunTimeViewController runtimeView;
    @Autowired
    private ISnapshotCompare snapshotCompare;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private SnapshotService snapshotService;
    @Autowired
    private UserService<User> userService;
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private PrivilegeService privilegeService;
    @Autowired
    private IQueryUploadStateService queryUploadStateServiceImpl;
    @Autowired
    private DefinitionAuthorityProvider definitionAuthorityProvider;
    @Autowired
    private OrgAuthClient orgAuthClient;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private SystemIdentityService systemIdentityService;
    @Resource
    private WorkflowSettingService settingService;
    @Autowired
    private IWorkflow workflow;

    public Map<String, Object> buildCounterParam(String formSchemeKey, BusinessKey businessKey, Task task, String actionCode) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        HashMap<String, Set<String>> actorsMap = new HashMap<String, Set<String>>();
        this.nextCounterSignActors(formSchemeKey, task, businessKey, actorsMap);
        variables.put(this.nrParameterUtils.getCountersignParamMapKey(), actorsMap);
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        variables.put(this.nrParameterUtils.getCountersignObjKey(), this.countSignParam(task, businessKey, actionCode, false, workFlowDefine));
        return variables;
    }

    public Map<String, Object> buildCounterParam(String formSchemeKey, BusinessKey businessKey, Task task, String actionCode, boolean compareVersion) {
        HashMap<String, Object> variables = new HashMap<String, Object>();
        HashMap<String, Set<String>> actorsMap = new HashMap<String, Set<String>>();
        this.nextCounterSignActors(formSchemeKey, task, businessKey, actorsMap, actionCode, compareVersion);
        variables.put(this.nrParameterUtils.getCountersignParamMapKey(), actorsMap);
        if (!"start".equals(actionCode)) {
            WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
            WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
            variables.put(this.nrParameterUtils.getCountersignObjKey(), this.countSignParam(task, businessKey, actionCode, compareVersion, workFlowDefine));
        }
        return variables;
    }

    public boolean isMultiInstanceTask(String userTaskId, String linkId) {
        WorkFlowNodeSet currNodeSet = this.queryWorkflowNode(userTaskId, linkId);
        if (currNodeSet != null) {
            return currNodeSet.isSignNode();
        }
        return false;
    }

    public boolean isRole(String userTaskId, String linkId) {
        WorkFlowNodeSet currNodeSet = this.queryWorkflowNode(userTaskId, linkId);
        if (currNodeSet != null) {
            String userOrRole = currNodeSet.getUserOrRole();
            return ROLE.equals(userOrRole);
        }
        return false;
    }

    private void nextCounterSignActors(String formSchemeKey, Task task, BusinessKey businessKey, Map<String, Set<String>> actorsMap) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        List<WorkFlowLine> lines = this.queryAfterLines(businessKey, task.getUserTaskId(), workFlowDefine);
        if (CollectionUtils.isEmpty(lines)) {
            return;
        }
        Optional<ProcessEngine> processEngine = this.processEngineProvider.getProcessEngine(ProcessType.COMPLETED_ACTIVIT);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
        Optional<Object> userTask = Optional.empty();
        for (WorkFlowLine line : lines) {
            boolean countersign;
            String afterNodeID = line.getAfterNodeID();
            WorkFlowNodeSet workFlowNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(afterNodeID, workFlowDefine.getLinkid());
            List<WorkFlowAction> actions = this.customWorkFolwService.getWorkFlowNodeAction(workFlowNodeSet.getId(), workFlowDefine.getLinkid());
            WorkFlowNodeSet workflowNode = this.queryWorkflowNode(line.getAfterNodeID(), workFlowDefine.getLinkid());
            if (workflowNode == null || !(countersign = workflowNode.isSignNode())) continue;
            userTask = instance.isPresent() ? deployService.getUserTask(instance.get().getProcessDefinitionId(), workFlowNodeSet.getId(), formSchemeKey) : Optional.of(new ProcessUserTask(workFlowNodeSet, this.getParticPants(workFlowNodeSet, workFlowDefine), this.buildProcessAction(actions)));
            Set<String> users = this.users(workFlowNodeSet, (UserTask)userTask.get(), businessKey, workFlowDefine);
            String userOrRole = workflowNode.getUserOrRole();
            if (ROLE.equals(userOrRole)) {
                Set<String> roleIds = this.getRoleIds(users);
                actorsMap.put(line.getId(), roleIds);
                continue;
            }
            actorsMap.put(line.getId(), users);
        }
    }

    private void nextCounterSignActors(String formSchemeKey, Task task, BusinessKey businessKey, Map<String, Set<String>> actorsMap, String actionCode, boolean compareVersion) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        List<WorkFlowLine> lines = this.queryAfterLines(businessKey, task.getUserTaskId(), workFlowDefine);
        if (CollectionUtils.isEmpty(lines)) {
            return;
        }
        for (WorkFlowLine line : lines) {
            boolean countersign;
            String afterNodeID = line.getAfterNodeID();
            WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(line.getActionid(), workFlowDefine.getLinkid());
            WorkFlowNodeSet workflowNode = this.queryWorkflowNode(line.getAfterNodeID(), workFlowDefine.getLinkid());
            if (workflowAction == null && "start".equals(actionCode)) {
                this.queryActors(businessKey, workflowNode, line, afterNodeID, compareVersion, actorsMap);
                continue;
            }
            if (workflowNode == null || workflowAction == null || !workflowAction.getActionCode().equals(actionCode) || !(countersign = workflowNode.isSignNode())) continue;
            this.queryActors(businessKey, workflowNode, line, afterNodeID, compareVersion, actorsMap);
        }
    }

    private void queryActors(BusinessKey businessKey, WorkFlowNodeSet workflowNode, WorkFlowLine line, String afterNodeID, boolean compareVersion, Map<String, Set<String>> actorsMap) {
        Set<String> users = this.nodeActors(businessKey, afterNodeID);
        String userOrRole = workflowNode.getUserOrRole();
        if (ROLE.equals(userOrRole)) {
            if (compareVersion) {
                Set<String> hasAuthRoleIds = this.calculateNodeActors(afterNodeID, businessKey, users);
                if (hasAuthRoleIds == null || hasAuthRoleIds.size() == 0) {
                    Set<String> roleIdsByCurrentUser = this.getRoleIdsByCurrentUser();
                    actorsMap.put(line.getId(), roleIdsByCurrentUser);
                } else {
                    actorsMap.put(line.getId(), hasAuthRoleIds);
                }
            } else {
                Set<String> roleIds = this.getRoleIds(users);
                actorsMap.put(line.getId(), roleIds);
            }
        }
        if (USER.equals(userOrRole)) {
            Set<String> userKeys = this.removeSystemUser(users);
            actorsMap.put(line.getId(), userKeys);
        }
    }

    public Set<String> nodeActors(BusinessKey businessKey, String nodeID) {
        WorkflowSettingDefine refSetting = this.settingService.getWorkflowDefineByFormSchemeKey(businessKey.getFormSchemeKey());
        WorkFlowDefine workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(refSetting.getWorkflowId(), 1);
        WorkFlowNodeSet workFlowNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(nodeID, workFlowDefine.getLinkid());
        List<WorkFlowAction> actions = this.customWorkFolwService.getWorkFlowNodeAction(workFlowNodeSet.getId(), workFlowDefine.getLinkid());
        String formSchemeKey = businessKey.getFormSchemeKey();
        Optional<ProcessEngine> processEngine = this.processEngineProvider.getProcessEngine(ProcessType.COMPLETED_ACTIVIT);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
        Optional<Object> userTask = Optional.empty();
        userTask = instance.isPresent() ? deployService.getUserTask(instance.get().getProcessDefinitionId(), workFlowNodeSet.getId(), formSchemeKey) : Optional.of(new ProcessUserTask(workFlowNodeSet, this.getParticPants(workFlowNodeSet, workFlowDefine), this.buildProcessAction(actions)));
        Set<String> users = this.users(workFlowNodeSet, (UserTask)userTask.get(), businessKey, workFlowDefine);
        return users;
    }

    private CounterSignParam countSignParam(Task task, BusinessKey businessKey, String actionCode, boolean compareVersion, WorkFlowDefine workFlowDefine) {
        WorkFlowNodeSet currNodeSet = this.queryWorkflowNode(task.getUserTaskId(), workFlowDefine.getLinkid());
        WorkFlowNodeSet workFlowNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(task.getUserTaskId(), workFlowDefine.getLinkid());
        Optional<ProcessEngine> processEngine = this.processEngineProvider.getProcessEngine(ProcessType.COMPLETED_ACTIVIT);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        Optional<UserTask> currentUserTask = deployService.getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), businessKey.getFormSchemeKey());
        if (currNodeSet != null) {
            boolean countersign = currNodeSet.isSignNode();
            String userOrRole = currNodeSet.getUserOrRole();
            if (countersign) {
                CounterSignParam counterSignParam = new CounterSignParam();
                counterSignParam.setActionCode(actionCode);
                if (ROLE.equals(userOrRole)) {
                    Set<String> userIds = this.users(workFlowNodeSet, currentUserTask.get(), businessKey, workFlowDefine);
                    Set<String> hasAuthRole = this.calculateNodeActors(task.getUserTaskId(), businessKey, userIds);
                    counterSignParam.setActors(hasAuthRole);
                    if (compareVersion) {
                        counterSignParam.setSignCount(hasAuthRole.size());
                        counterSignParam.setCountSignAllUser(false);
                    } else {
                        counterSignParam.setCountSignAllUser(true);
                        counterSignParam.setUserOrRole(ROLE);
                    }
                    String[] sign_role = currNodeSet.getSign_role();
                    if (null != sign_role && sign_role.length > 0) {
                        counterSignParam.setSpecialSignRole(sign_role);
                    }
                }
                if (USER.equals(userOrRole)) {
                    counterSignParam.setUserOrRole(USER);
                    int countersign_count = currNodeSet.getCountersign_count();
                    if (countersign_count >= 1) {
                        counterSignParam.setSignCount(countersign_count);
                        counterSignParam.setCountSignAllUser(false);
                    } else {
                        counterSignParam.setCountSignAllUser(true);
                    }
                    String[] specialUser = currNodeSet.getSign_user();
                    if (null != specialUser && specialUser.length > 3) {
                        counterSignParam.setSpecialSignUser(currNodeSet.getSign_user());
                    }
                }
                counterSignParam.setBusinessKey(businessKey);
                counterSignParam.setTaskCode(currentUserTask.get().getId());
                return counterSignParam;
            }
        }
        return null;
    }

    private List<WorkFlowLine> queryAfterLines(BusinessKey businessKey, String taskCode, WorkFlowDefine workFlowDefine) {
        if (taskCode.equals(TaskEnum.TASK_START.getTaskId())) {
            return this.nrParameterUtils.queryWorkFlowLines(BusinessKeyFormatter.formatToString(businessKey));
        }
        WorkFlowNodeSet workFlowNodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(taskCode, workFlowDefine.getLinkid());
        if (workFlowNodeSet != null) {
            return this.customWorkFolwService.getWorkflowLinesByPreTask(taskCode, workFlowDefine.getLinkid());
        }
        return Collections.emptyList();
    }

    private WorkFlowNodeSet queryWorkflowNode(String nodeId, String linkId) {
        WorkFlowNodeSet nodeSet = this.customWorkFolwService.getWorkFlowNodeSetByID(nodeId, linkId);
        return nodeSet;
    }

    private List<WorkFlowParticipant> getParticPants(WorkFlowNodeSet workFlowNodeSet, WorkFlowDefine workFlowDefine) {
        String[] partis;
        ArrayList<WorkFlowParticipant> participants = new ArrayList<WorkFlowParticipant>();
        if (workFlowNodeSet != null && (partis = workFlowNodeSet.getPartis()) != null && partis.length > 0) {
            List<WorkFlowParticipant> workFlowParticipants = this.customWorkFolwService.getWorkFlowParticipantsByID(partis, workFlowDefine.getLinkid());
            participants.addAll(workFlowParticipants);
        }
        return participants;
    }

    private List<ProcessAction> buildProcessAction(List<WorkFlowAction> actions) {
        ArrayList<ProcessAction> processActions = new ArrayList<ProcessAction>();
        for (WorkFlowAction action : actions) {
            String actionCode = action.getActionCode();
            if (actionCode.equals("cus_upload")) {
                processActions.add(ProcessAction.CUS_UPLOAD);
            }
            if (actionCode.equals("cus_submit")) {
                processActions.add(ProcessAction.CUS_SUBMIT);
            }
            if (actionCode.equals("cus_reject")) {
                processActions.add(ProcessAction.CUS_REJECT);
            }
            if (actionCode.equals("cus_return")) {
                processActions.add(ProcessAction.CUS_RETURN);
            }
            if (!actionCode.equals("cus_confirm")) continue;
            processActions.add(ProcessAction.CUS_CONFIRM);
        }
        return processActions;
    }

    public Set<String> users(WorkFlowNodeSet workFlowNodeSet, UserTask task, BusinessKey businessKey, WorkFlowDefine workFlowDefine) {
        HashSet<String> actors = new HashSet<String>();
        List<WorkFlowParticipant> workFlowParticipants = this.getParticPants(workFlowNodeSet, workFlowDefine);
        if (!CollectionUtils.isEmpty(workFlowParticipants)) {
            for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                String[] roleIds = workFlowParticipant.getRoleIds();
                String[] userIds = workFlowParticipant.getUserIds();
                String strategyid = workFlowParticipant.getStrategyid();
                if (strategyid == null) continue;
                HashMap<String, String[]> map = new HashMap<String, String[]>();
                map.put("roleIds", roleIds);
                map.put("userIds", userIds);
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String param = objectMapper.writeValueAsString(map);
                    try {
                        ActorStrategyParameter receiverParam = ActionMethod.getReceiverParam(strategyid, param);
                        for (ActorStrategy ai : this.actorStrategy) {
                            if (!ai.getType().equals(strategyid)) continue;
                            Set<String> actor = ai.getActors((BusinessKeyInfo)businessKey, receiverParam, task);
                            actors.addAll(actor);
                        }
                    }
                    catch (Exception e) {
                        this.logger.error("\u67e5\u8be2\u53c2\u4e0e\u8005\u7b56\u7565\u5931\u8d25" + e.getMessage(), e);
                    }
                }
                catch (JsonProcessingException e1) {
                    this.logger.error(e1.getMessage(), e1);
                }
            }
        }
        return actors;
    }

    public boolean compareDataVersion(BusinessKey businessKey) {
        String initalVersionId = INIIALVERSIONID;
        List<DataVersionData> queryAll = this.queryAll(businessKey);
        if (queryAll != null && queryAll.size() > 0) {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(businessKey.getFormSchemeKey());
            List formKeys = this.runtimeView.queryAllFormKeysByFormScheme(businessKey.getFormSchemeKey());
            DataVersionData dataVersionData = queryAll.get(0);
            String compareDataVersionId = dataVersionData.getVersionId();
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            Map<String, DimensionValue> dimensionSet = this.dimensionUtil.getDimensionSet(buildDimension, businessKey.getFormSchemeKey());
            List compareVersionData = this.snapshotCompare.compareVersionData(dimensionSet, businessKey.getFormSchemeKey(), formKeys, initalVersionId, compareDataVersionId);
            return compareVersionData != null && compareVersionData.size() > 0;
        }
        return false;
    }

    public List<String> getFormKeys(BusinessKey businessKey, List<String> formKeys) {
        List<String> formKsys = new ArrayList<String>();
        String initalVersionId = INIIALVERSIONID;
        List<DataVersionData> queryAll = this.queryAll(businessKey);
        if (queryAll != null && queryAll.size() > 0) {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(businessKey.getFormSchemeKey());
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
            DataVersionData dataVersionData = queryAll.get(0);
            String compareDataVersionId = dataVersionData.getVersionId();
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            Map<String, DimensionValue> dimensionSet = this.dimensionUtil.getDimensionSet(buildDimension, businessKey.getFormSchemeKey());
            List compareVersionData = this.snapshotCompare.compareVersionData(dimensionSet, businessKey.getFormSchemeKey(), formKeys, initalVersionId, compareDataVersionId);
            formKsys = compareVersionData.stream().map(e -> e.getFormKey()).collect(Collectors.toList());
        }
        return formKsys;
    }

    public List<DataVersionData> queryAll(BusinessKey businessKey) {
        ArrayList<DataVersionData> resultList = new ArrayList<DataVersionData>();
        List list = null;
        try {
            FormSchemeDefine formScheme = this.runtimeView.getFormScheme(businessKey.getFormSchemeKey());
            TaskDefine taskDefine = this.runtimeView.queryTaskDefine(formScheme.getTaskKey());
            DimensionValueSet buildDimension = this.dimensionUtil.buildDimension(businessKey);
            String formSchemeKey = businessKey.getFormSchemeKey();
            DimensionCombinationBuilder builder = new DimensionCombinationBuilder(buildDimension);
            list = this.snapshotService.queryVersion(builder.getCombination(), formSchemeKey);
        }
        catch (Exception formScheme) {
            // empty catch block
        }
        HashMap<String, User> userCache = new HashMap<String, User>();
        for (DataVersion dataVersion : list) {
            DataVersionData dataVersionData = new DataVersionData();
            String createUser = dataVersion.getCreatUser();
            if (userCache.containsKey(createUser)) {
                User user = (User)userCache.get(createUser);
                if (user != null) {
                    dataVersionData.setCreatUser(user.getName());
                }
            } else {
                Optional find = this.userService.find(dataVersion.getCreatUser());
                User user = null;
                if (find.isPresent()) {
                    dataVersionData.setCreatUser(((User)find.get()).getName());
                    user = (User)find.get();
                } else {
                    Optional find2 = this.systemUserService.find(dataVersion.getCreatUser());
                    if (find2.isPresent()) {
                        dataVersionData.setCreatUser(((SystemUser)find2.get()).getName());
                        user = (User)find2.get();
                    }
                }
                userCache.put(createUser, user);
            }
            dataVersionData.init(dataVersion);
            resultList.add(dataVersionData);
        }
        return resultList;
    }

    private boolean hasUnitAuth(BusinessKey businessKey, String key, String identityId) {
        TableModelDefine table = this.dimensionUtil.getDwTableByFormSchemeKey(businessKey.getFormSchemeKey());
        User user = this.userService.get(identityId);
        String hasAuth = this.hasAuth(table.getCode(), key, user.getName(), identityId);
        return "true".equals(hasAuth);
    }

    private boolean hasFormAuth(String formKey, String identityId) {
        return this.definitionAuthorityProvider.canReadForm(formKey, identityId);
    }

    private boolean hasGroupAuth(String formKey, String identityId) {
        return this.privilegeService.hasAuth("task_privilege_read", identityId, (Object)ResourceType.FORM_GROUP.toResourceId(formKey));
    }

    private String hasAuth(String categoryname, String orgCode, String userName, String id) {
        OrgAuthFindDTO orgAuthFindDTO = new OrgAuthFindDTO();
        OrgDTO orgDTO = new OrgDTO();
        orgDTO.setCategoryname(categoryname);
        orgDTO.setCode(orgCode);
        orgDTO.setAuthType(OrgDataOption.AuthType.ACCESS);
        orgAuthFindDTO.setOrgDTO(orgDTO);
        UserDO userDO = new UserDO();
        userDO.setUsername(userName);
        userDO.setId(id);
        orgAuthFindDTO.setUserDO(userDO);
        R existDataAuth = this.orgAuthClient.existDataAuth(orgAuthFindDTO);
        Object object = existDataAuth.get((Object)"exist");
        return object.toString();
    }

    private Set<String> getRoleIds(Set<String> userIds) {
        HashSet<String> roldIds = new HashSet<String>();
        for (String userid : userIds) {
            Set roleids = this.roleService.getIdByIdentity(userid);
            roldIds.addAll(roleids);
        }
        Set<String> retainRoleId = this.removeRoleId(roldIds);
        return retainRoleId;
    }

    private Set<String> getRoleIdsByCurrentUser() {
        HashSet<String> roldIds = new HashSet<String>();
        Actor candicateActor = Actor.fromNpContext();
        Set roleids = this.roleService.getIdByIdentity(candicateActor.getUserId());
        roldIds.addAll(roleids);
        Set<String> retainRoleId = this.removeRoleId(roldIds);
        return retainRoleId;
    }

    private Set<String> removeRoleId(Set<String> roleIds) {
        HashSet<String> temp = new HashSet<String>();
        List allsystemroleids = Authz2Constants.allSystemRoleIds;
        for (String roleId : roleIds) {
            if (allsystemroleids.contains(roleId)) continue;
            temp.add(roleId);
        }
        return temp;
    }

    private Set<String> removeSystemUser(Set<String> users) {
        HashSet<String> temp = new HashSet<String>();
        Set allSystemIdentities = this.systemIdentityService.getAllSystemIdentities();
        if (allSystemIdentities != null && users != null) {
            for (String userKey : users) {
                if (allSystemIdentities.contains(userKey)) continue;
                temp.add(userKey);
            }
        }
        return temp;
    }

    private Set<String> getActorsOfVersionData(BusinessKey businessKey, String nodeId, List<String> formKeys, Set<String> userIds, WorkFlowType workflowType) {
        HashSet<String> actors = new HashSet<String>();
        List<String> fliterformKeys = this.getFormKeys(businessKey, formKeys);
        if (WorkFlowType.ENTITY.equals((Object)workflowType)) {
            for (String userId : userIds) {
                for (String formKey : fliterformKeys) {
                    boolean hasAuth = this.hasFormAuth(formKey, userId);
                    if (!hasAuth) continue;
                    Set roleIds = this.roleService.getIdByIdentity(userId);
                    actors.addAll(roleIds);
                }
            }
        } else if (WorkFlowType.FORM.equals((Object)workflowType)) {
            for (String userId : userIds) {
                for (String formKey : fliterformKeys) {
                    boolean hasAuth = this.hasFormAuth(formKey, userId);
                    if (!hasAuth) continue;
                    Set roleIds = this.roleService.getIdByIdentity(userId);
                    actors.addAll(roleIds);
                }
            }
        } else if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            HashSet<String> groupKeys = new HashSet<String>();
            for (String formKey : fliterformKeys) {
                List<FormGroupDefine> formGroupDefines = this.commonUtil.getFormGroupDefineByFormKey(formKey);
                for (FormGroupDefine formGroupDefine : formGroupDefines) {
                    groupKeys.add(formGroupDefine.getKey());
                }
            }
            for (String userId : userIds) {
                for (String formKey : groupKeys) {
                    boolean hasAuth = this.hasFormAuth(formKey, userId);
                    if (!hasAuth) continue;
                    Set roleIds = this.roleService.getIdByIdentity(userId);
                    actors.addAll(roleIds);
                }
            }
        }
        Set<String> retainRoleId = this.removeRoleId(actors);
        return retainRoleId;
    }

    private Set<String> calculateNodeActors(String nodeId, BusinessKey businessKey, Set<String> userIds) {
        HashSet<String> actors = new HashSet<String>();
        WorkFlowType workflowType = this.commonUtil.workflowType(businessKey.getFormSchemeKey());
        List formKeys = this.runtimeView.queryAllFormKeysByFormScheme(businessKey.getFormSchemeKey());
        Set<String> actorsOfVersionData = this.getActorsOfVersionData(businessKey, nodeId, formKeys, userIds, workflowType);
        actors.addAll(actorsOfVersionData);
        Map<String, Set<String>> userAndRoleMap = this.userReleateRole(userIds);
        Set<String> rolesOfState = this.getHisUploadActors(businessKey, formKeys, nodeId, userIds, userAndRoleMap, workflowType);
        actors.addAll(rolesOfState);
        return actors;
    }

    public Map<String, Set<String>> userReleateRole(Set<String> userIds) {
        HashMap<String, Set<String>> roleMap = new HashMap<String, Set<String>>();
        for (String userid : userIds) {
            HashSet<String> roleIds = new HashSet<String>();
            Set idByIdentity = this.roleService.getIdByIdentity(userid);
            roleIds.addAll(idByIdentity);
            Set<String> retainRoleId = this.removeRoleId(roleIds);
            roleMap.put(userid, retainRoleId);
        }
        return roleMap;
    }

    public boolean upload(BusinessKey businessKey, List<String> formKeys, String nodeId, Set<String> actors, Map<String, Set<String>> userAndRoleMap, WorkFlowType workflowType) {
        Set<String> hisUploadActors = this.getHisUploadActors(businessKey, formKeys, nodeId, actors, userAndRoleMap, workflowType);
        return hisUploadActors.size() == 0;
    }

    private Set<String> getHisUploadActors(BusinessKey businessKey, List<String> formKeys, String nodeId, Set<String> userIds, Map<String, Set<String>> userAndRoleMap, WorkFlowType workflowType) {
        HashSet<String> fliterKeys = new HashSet<String>();
        List<String> formKeysOrGroupKeys = this.formKeysOrGroupKeys(workflowType, formKeys);
        List<UploadRecordNew> hisUploadStates = this.queryUploadStateServiceImpl.queryUploadHistoryStates(businessKey, formKeysOrGroupKeys, nodeId);
        if (hisUploadStates == null || hisUploadStates.size() == 0) {
            for (String userId : userIds) {
                fliterKeys.addAll((Collection<String>)userAndRoleMap.get(userId));
            }
            return fliterKeys;
        }
        boolean flag = false;
        for (String userId : userIds) {
            Set<String> roles = userAndRoleMap.get(userId);
            for (int i = 0; i < hisUploadStates.size(); ++i) {
                UploadRecordNew uploadRecordNew = hisUploadStates.get(i);
                boolean hasAuth = this.hasAuth(businessKey, uploadRecordNew, workflowType, userId);
                String operator = uploadRecordNew.getOperator();
                User username = this.userService.get(operator);
                if (!username.getId().equals(userId) || !hasAuth) continue;
                String action = uploadRecordNew.getAction();
                if ("act_reject".equals(action) || "cus_reject".equals(action) || "act_return".equals(action) || "cus_return".equals(action)) {
                    fliterKeys.addAll(roles);
                }
                flag = true;
                break;
            }
            if (flag) continue;
            fliterKeys.addAll(roles);
        }
        return fliterKeys;
    }

    private boolean hasAuth(BusinessKey businessKey, UploadRecordNew uploadRecordNew, WorkFlowType workflowType, String userId) {
        String mainDimName = this.dimensionUtil.getDwMainDimName(businessKey.getFormSchemeKey());
        Object value = uploadRecordNew.getEntities().getValue(mainDimName);
        String formKey = uploadRecordNew.getFormKey();
        if (WorkFlowType.ENTITY.equals((Object)workflowType)) {
            boolean hasAuth = this.hasUnitAuth(businessKey, value.toString(), userId);
            return hasAuth;
        }
        if (WorkFlowType.FORM.equals((Object)workflowType)) {
            boolean hasAuth = this.hasFormAuth(formKey, userId);
            return hasAuth;
        }
        if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            boolean hasAuth = this.hasGroupAuth(formKey, userId);
            return hasAuth;
        }
        return false;
    }

    private List<String> formKeysOrGroupKeys(WorkFlowType workflowType, List<String> formKeys) {
        ArrayList<String> groupKeys = new ArrayList<String>();
        if (WorkFlowType.GROUP.equals((Object)workflowType)) {
            for (String formKey : formKeys) {
                List<FormGroupDefine> formGroupDefines = this.commonUtil.getFormGroupDefineByFormKey(formKey);
                for (FormGroupDefine formGroupDefine : formGroupDefines) {
                    groupKeys.add(formGroupDefine.getKey());
                }
            }
        }
        return formKeys;
    }

    public List<String> queryNextNodeActors(String formSchemeKey, WorkFlowNodeSet nodeSet) {
        boolean defaultWorkflow = this.workflow.isDefaultWorkflow(formSchemeKey);
        if (!defaultWorkflow) {
            boolean signStartMode;
            String userOrRole;
            if (nodeSet == null) {
                return new ArrayList<String>();
            }
            boolean signNode = nodeSet.isSignNode();
            if (signNode && (userOrRole = nodeSet.getUserOrRole()).equals(ROLE) && (signStartMode = nodeSet.isSignStartMode())) {
                return this.getworkFlowParticipants(nodeSet.getPartis(), nodeSet.getLinkid());
            }
        }
        return new ArrayList<String>();
    }

    public WorkFlowNodeSet getWorkflowNodeSetByID(String formSchemeKey, String nodeId, String actionId) {
        List<WorkFlowLine> workflowLines;
        String workflowId;
        WorkFlowDefine workFlowDefine;
        WorkflowSettingDefine workflowSettingDefine = this.settingService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowSettingDefine != null && workflowSettingDefine.getId() != null && (workFlowDefine = this.customWorkFolwService.getWorkFlowDefineByID(workflowId = workflowSettingDefine.getWorkflowId(), 1)) != null && workFlowDefine.getId() != null && (workflowLines = this.customWorkFolwService.getWorkflowLinesByPreTask(nodeId, workFlowDefine.getLinkid())) != null && workflowLines.size() > 0) {
            for (WorkFlowLine workflowLine : workflowLines) {
                String actionCode;
                String actionid = workflowLine.getActionid();
                WorkFlowAction workflowAction = this.customWorkFolwService.getWorkflowActionById(actionid, workFlowDefine.getLinkid());
                if (workflowAction == null || !(actionCode = workflowAction.getActionCode()).equals(actionId)) continue;
                return this.customWorkFolwService.getWorkFlowNodeSetByID(workflowLine.getAfterNodeID(), workFlowDefine.getLinkid());
            }
        }
        return null;
    }

    private List<String> getworkFlowParticipants(String[] partis, String linkid) {
        ArrayList<String> participants = new ArrayList<String>();
        List<WorkFlowParticipant> workFlowParticipants = this.customWorkFolwService.getWorkFlowParticipantsByID(partis, linkid);
        if (workFlowParticipants != null) {
            for (WorkFlowParticipant workFlowParticipant : workFlowParticipants) {
                String[] roleIds = workFlowParticipant.getRoleIds();
                participants.addAll(Arrays.asList(roleIds));
            }
        }
        return participants;
    }
}

