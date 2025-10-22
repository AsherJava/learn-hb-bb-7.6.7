/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.impl.process.service.impl;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowAction;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowDefine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowLine;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowParticipant;
import com.jiuqi.nr.bpm.custom.common.WorkFlowInfoObj;
import com.jiuqi.nr.bpm.custom.service.CustomWorkFolwService;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.ActivitModel;
import com.jiuqi.nr.bpm.impl.process.ProcessTask;
import com.jiuqi.nr.bpm.impl.process.ProcessUserTask;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessAction;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.impl.process.util.ProcessUtil;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActivitiProcessTaskBuilder
implements ProcessTaskBuilder {
    @Autowired
    CustomWorkFolwService customWorkFolwService;
    @Autowired
    WorkflowSettingService workflowSettingService;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private ProcessStateHistoryDao processStateHistoryDao;
    @Autowired(required=false)
    private List<IConditionalExecute> conditionalExecutes;
    @Autowired
    private ProcessUtil processUtil;
    @Autowired
    private BusinessGenerator businessGenerator;
    private static final String GETACTION_ID = "getActionid";
    private static final String GETACTION_CODE = "getActionCode";
    private static final String GETAFTER_NODE = "getAfterNodeID";
    private static final String GETBEFORE_NODE = "getBeforeNodeID";
    private static final String GET_NODE = "getNodeid";
    private static final Logger log = LoggerFactory.getLogger(ActivitiProcessTaskBuilder.class);
    private static ConcurrentHashMap<String, ActivitModel> cacheMap = new ConcurrentHashMap();
    private static final long EXPER_TIME = 300000L;

    @Override
    public List<Task> queryTaskByBusinessKey(BusinessKey businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.processUtil.buildUploadMasterKey(businessKey);
        return this.queryTasks(dimensionValueSet, businessKey.getFormKey(), formScheme);
    }

    @Override
    public List<Task> queryTasks(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        UploadStateNew uploadState = this.processStateHistoryDao.queryUploadState(dimensionValueSet, formKey, formScheme);
        if (uploadState == null) {
            return Collections.emptyList();
        }
        String taskId = uploadState.getTaskId();
        List<ActivitModel.TaskObject> taskNode = this.queryTaskNodes(taskId, formScheme.getKey());
        if (taskNode.isEmpty()) {
            return Collections.emptyList();
        }
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), dimensionValueSet, formKey, formKey);
        for (ActivitModel.TaskObject node : taskNode) {
            userTasks.add(new ProcessTask(node.getTaskNode(), businessKey));
        }
        return userTasks;
    }

    @Override
    public Optional<UserTask> queryUserTask(String userTaskId, String formSchemeKey) {
        List<ActivitModel.TaskObject> taskNode = this.queryTaskNodes(userTaskId, formSchemeKey);
        if (taskNode.isEmpty()) {
            return Optional.empty();
        }
        ActivitModel.TaskObject task = (ActivitModel.TaskObject)taskNode.stream().findFirst().get();
        return Optional.of(new ProcessUserTask(task.getTaskNode(), task.getParticipants(), this.buildProcessAction(task.getActions())));
    }

    @Override
    public Optional<Task> queryTaskById(String taskId, BusinessKey businessKey) {
        List<ActivitModel.TaskObject> taskNode = this.queryTaskNodes(taskId, businessKey.getFormSchemeKey());
        if (taskNode.isEmpty()) {
            return Optional.empty();
        }
        ActivitModel.TaskObject task = (ActivitModel.TaskObject)taskNode.stream().findFirst().get();
        return Optional.of(new ProcessTask(task.getTaskNode(), businessKey));
    }

    @Override
    public String nextUserTaskId(String taskId, String actionId, String businessKey) {
        BusinessKey businessKeyInfo = BusinessKeyFormatter.parsingFromString(businessKey);
        if (taskId.equals("start")) {
            return this.startFlow(businessKeyInfo, businessKey);
        }
        List<ActivitModel.TaskObject> tasks = this.queryTaskNodes(taskId, businessKeyInfo.getFormSchemeKey());
        if (tasks.isEmpty()) {
            throw new BpmException("can not find task node");
        }
        ActivitModel.TaskObject currNode = (ActivitModel.TaskObject)tasks.stream().findFirst().get();
        List<Object> actions = currNode.getActions();
        if ((actions = this.filter(actions, GETACTION_CODE, actionId)).isEmpty()) {
            throw new BpmException("can not find flow action");
        }
        List<Object> lines = currNode.getOutgoingLines();
        if ((lines = this.filter(lines, GETACTION_ID, ((WorkFlowAction)actions.stream().findFirst().get()).getId())).isEmpty()) {
            throw new BpmException("can not find outgoing line");
        }
        if (lines.size() > 1) {
            Optional<Task> task = this.queryTaskById(taskId, businessKeyInfo);
            WorkFlowLine exeLine = this.executeWorkFlowLines(businessKey, task.get(), NpContextHolder.getContext().getIdentityId(), actionId, lines);
            return exeLine.getAfterNodeID();
        }
        return ((WorkFlowLine)lines.stream().findFirst().get()).getAfterNodeID();
    }

    @Override
    public boolean canStartProcess(String businessKey) {
        List<WorkFlowLine> lines = this.nrParameterUtils.queryWorkFlowLines(businessKey);
        if (lines.size() <= 1) {
            return true;
        }
        int i = 0;
        for (WorkFlowLine line : lines) {
            boolean res = this.executeLines(businessKey, line.getConditionExecute(), line.getId());
            i = res ? i + 1 : i;
        }
        return i == 1;
    }

    @Override
    public ProcessType getProcessType() {
        return ProcessType.SIMPLE_ACTIVIT;
    }

    private String startFlow(BusinessKey businessKeyInfo, String businessKey) {
        List<WorkFlowLine> lines;
        Optional<ActivitModel> activitObj = this.getActivitiModel(businessKeyInfo.getFormSchemeKey(), true);
        if (!activitObj.isPresent()) {
            throw new BpmException("not found flow setting");
        }
        List tasks = activitObj.get().getTask().stream().filter(e -> e.getTaskNode().getId().startsWith("StartEvent")).collect(Collectors.toList());
        if (tasks.isEmpty()) {
            log.error(String.format("%s%s", "can not find task node! businessKey:", businessKey));
        }
        if ((lines = ((ActivitModel.TaskObject)tasks.stream().findFirst().get()).getOutgoingLines()).isEmpty()) {
            log.error(String.format("%s%s", "can not find outgoing line! businessKey:", businessKey));
        }
        if (lines.size() > 1) {
            WorkFlowLine line = this.executeStartLines(businessKey, lines);
            return line.getAfterNodeID();
        }
        return ((WorkFlowLine)lines.stream().findFirst().get()).getAfterNodeID();
    }

    private WorkFlowLine executeStartLines(String businessKey, List<WorkFlowLine> lines) {
        WorkFlowLine exeLine = null;
        int i = 0;
        for (WorkFlowLine line : lines) {
            boolean res = this.executeLines(businessKey, line.getConditionExecute(), line.getId());
            if (!res) continue;
            exeLine = line;
            ++i;
        }
        if (i > 1 || exeLine == null) {
            throw new BpmException("can not find outgoing line");
        }
        return exeLine;
    }

    private WorkFlowLine executeWorkFlowLines(String businessKey, Task task, String userId, String actionId, List<WorkFlowLine> lines) {
        WorkFlowLine exeLine = null;
        int i = 0;
        for (WorkFlowLine line : lines) {
            boolean res = this.executeLines(businessKey, line.getConditionExecute(), task, userId, actionId, line.getId());
            if (!res) continue;
            exeLine = line;
            ++i;
        }
        if (i > 1 || exeLine == null) {
            throw new BpmException("can not find outgoing line");
        }
        return exeLine;
    }

    private List<ActivitModel.TaskObject> queryTaskNodes(String taskId, String formSchemeKey) {
        List<ActivitModel.TaskObject> taskNode = new ArrayList<ActivitModel.TaskObject>();
        Optional<ActivitModel> activitObj = this.getActivitiModel(formSchemeKey, false);
        if (activitObj.isPresent()) {
            taskNode = activitObj.get().getTask().stream().filter(e -> e.getTaskNode().getId().equals(taskId)).collect(Collectors.toList());
        }
        return taskNode;
    }

    private Optional<ActivitModel> getActivitiModel(String formSchemKey, boolean clearCache) {
        Optional<ActivitModel> activitModel = CacheManager.getCacheBykey(formSchemKey, clearCache);
        if (!activitModel.isPresent() && (activitModel = Optional.ofNullable(this.loadActivitObjFromDB(formSchemKey))).isPresent()) {
            CacheManager.put(formSchemKey, activitModel.get());
        }
        return activitModel;
    }

    private ActivitModel loadActivitObjFromDB(String formSchemKey) {
        WorkflowSettingDefine define = this.workflowSettingService.getWorkflowDefineByFormSchemeKey(formSchemKey);
        WorkFlowDefine defineByID = this.customWorkFolwService.getWorkFlowDefineByID(define.getWorkflowId(), 1);
        WorkFlowInfoObj workDefineObj = this.customWorkFolwService.getWorkFlowInfoObj(defineByID);
        List<WorkFlowAction> actions = workDefineObj.getActions();
        List<WorkFlowNodeSet> nodes = workDefineObj.getNodesets();
        List<WorkFlowLine> lines = workDefineObj.getLines();
        List<WorkFlowParticipant> particis = workDefineObj.getParticis();
        ActivitModel activitModel = new ActivitModel();
        for (WorkFlowNodeSet node : nodes) {
            String nodeId = node.getId();
            ActivitModel.TaskObject taskObject = new ActivitModel.TaskObject();
            taskObject.setIncomingLines(this.filter(lines, GETAFTER_NODE, nodeId));
            taskObject.setOutgoingLines(this.filter(lines, GETBEFORE_NODE, nodeId));
            taskObject.setParticipants(this.filter(particis, GET_NODE, nodeId));
            taskObject.setActions(this.filter(actions, GET_NODE, nodeId));
            taskObject.setTaskNode(node);
            activitModel.getTask().add(taskObject);
        }
        activitModel.setInstantId(UUID.randomUUID().toString());
        activitModel.setCreateDate(new Date());
        return activitModel;
    }

    private List<? extends Object> filter(List<? extends Object> list, String methodName, String compValue) {
        return list.stream().filter(e -> {
            try {
                Method method = e.getClass().getDeclaredMethod(methodName, new Class[0]);
                return compValue.equals(method.invoke(e, new Object[0]));
            }
            catch (Exception exception) {
                log.error(exception.getMessage(), exception);
                return false;
            }
        }).collect(Collectors.toList());
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

    private boolean executeLines(String businessKey, String selectedExecute, String workFlowLineId) {
        for (IConditionalExecute conditionalExecute : this.conditionalExecutes) {
            if (!selectedExecute.equals(conditionalExecute.getClass().getSimpleName()) || !conditionalExecute.execute(businessKey, workFlowLineId)) continue;
            return true;
        }
        return false;
    }

    private boolean executeLines(String businessKey, String selectedExecute, Task task, String userId, String actionId, String workFlowLineId) {
        for (IConditionalExecute conditionalExecute : this.conditionalExecutes) {
            if (!selectedExecute.equals(conditionalExecute.getClass().getSimpleName()) || !conditionalExecute.execute(task, userId, actionId, businessKey, workFlowLineId)) continue;
            return true;
        }
        return false;
    }

    private static class CacheManager {
        private CacheManager() {
        }

        public static void put(String key, ActivitModel obj) {
            cacheMap.put(key, obj);
        }

        public static Optional<ActivitModel> getCacheBykey(String key, boolean clearCache) {
            ActivitModel obj = (ActivitModel)cacheMap.get(key);
            if (obj != null && (clearCache || new Date().getTime() - obj.getCreateDate().getTime() > 300000L)) {
                cacheMap.remove(key);
                return Optional.empty();
            }
            return Optional.ofNullable(obj);
        }
    }
}

