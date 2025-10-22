/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.data.engine.condition.IConditionCache
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  org.activiti.bpmn.model.FlowElement
 *  org.activiti.bpmn.model.Process
 *  org.activiti.bpmn.model.UserTask
 *  org.activiti.engine.delegate.DelegateTask
 *  org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior
 *  org.activiti.engine.impl.util.ProcessDefinitionUtil
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.event.ActivitiTaskCreateEvent;
import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import com.jiuqi.nr.bpm.event.TaskCreateEventListener;
import com.jiuqi.nr.bpm.impl.activiti6.common.DelegateTaskWrapper;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.countersign.CounterSignActor;
import com.jiuqi.nr.bpm.impl.countersign.CounterSignCount;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.data.engine.condition.IConditionCache;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.Process;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.impl.bpmn.behavior.ParallelMultiInstanceBehavior;
import org.activiti.engine.impl.util.ProcessDefinitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class Activiti6TaskCreateEventListener
implements TaskCreateEventListener {
    private static final String STARTEVENT = "start";
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    ProcessStateHistoryDao processStateHistoryDao;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void onCreate(TaskCreateEvent event) {
        this.onCreate(((ActivitiTaskCreateEvent)event).getDelegateTask());
    }

    @Override
    public void onBatchCreate(TaskCreateEvent event) {
    }

    @Override
    public FlowsType getFlowsType() {
        return FlowsType.WORKFLOW;
    }

    private void onCreate(DelegateTask delegateTask) {
        Object Object2 = delegateTask.getExecution().getParent().getVariable(this.nrParameterUtils.getMapKey());
        Object forceState = delegateTask.getExecution().getParent().getVariable(this.nrParameterUtils.getForceMapKey());
        BusinessKey businessKey = this.getBusinessKey(delegateTask);
        if (businessKey == null) {
            return;
        }
        ProcessEngine engine = this.getProcessEngine(businessKey);
        if (engine == null) {
            return;
        }
        UserTask userTask = this.getUserTask(engine, delegateTask, businessKey.getFormSchemeKey());
        if (userTask == null) {
            return;
        }
        this.calculateTaskCandidate(delegateTask, userTask, businessKey);
        String actionId = STARTEVENT;
        if (Object2 != null) {
            actionId = Object2.toString();
        }
        Boolean force = false;
        if (forceState != null && forceState instanceof Boolean) {
            force = (Boolean)forceState;
        }
        Object variable = delegateTask.getExecution().getParent().getVariable(this.nrParameterUtils.getIConditionCache());
        IConditionCache conditionCache = (IConditionCache)variable;
        this.processStateHistoryDao.updateState(businessKey, userTask.getId(), actionId, force, conditionCache, delegateTask.getId());
        if (Object2 == null) {
            this.evaluateTodo(engine, delegateTask, userTask, businessKey);
        }
    }

    private BusinessKey getBusinessKey(DelegateTask delegateTask) {
        Object businessKeyVar = delegateTask.getVariable("businessKey");
        if (businessKeyVar == null) {
            return null;
        }
        return BusinessKeyFormatter.parsingFromString(businessKeyVar.toString());
    }

    private ProcessEngine getProcessEngine(BusinessKey businessKey) {
        Optional<ProcessEngine> engineFound = this.processEngineProvider.getProcessEngine(businessKey.getFormSchemeKey());
        return engineFound.isPresent() ? engineFound.get() : null;
    }

    private UserTask getUserTask(ProcessEngine engine, DelegateTask delegateTask, String formSchemeKey) {
        Optional<UserTask> userTaskFound = engine.getDeployService().getUserTask(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey(), formSchemeKey);
        return userTaskFound.isPresent() ? userTaskFound.get() : null;
    }

    private Task getTask(DelegateTask delegateTask) {
        return new DelegateTaskWrapper(delegateTask);
    }

    private void evaluateTodo(ProcessEngine engine, DelegateTask delegateTask, UserTask userTask, BusinessKey businessKey) {
        this.actorStrategyProvider.sendTodoMessage(this.getTask(delegateTask), userTask, businessKey.toString());
    }

    private String actionIdCacluete(Object actionId, String taskId, String formSchemKey) {
        String action = actionId.toString();
        if (action.equals("act_upload") && taskId.equals("tsk_upload")) {
            action = STARTEVENT;
        }
        if (action.equals("act_confirm") && taskId.equals("tsk_audit")) {
            action = "act_upload";
        }
        if (action.equals("act_submit") && taskId.equals("tsk_submit")) {
            action = STARTEVENT;
        }
        if (action.equals("act_cancel_confirm") && taskId.equals("tsk_upload")) {
            FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(formSchemKey);
            action = formScheme.getFlowsSetting().getDesignFlowSettingDefine().isUnitSubmitForCensorship() ? "act_submit" : STARTEVENT;
        }
        return action;
    }

    private void calculateTaskCandidate(DelegateTask delegateTask, UserTask userTask, BusinessKey businessKey) {
        boolean multiInstance = this.isMultiInstance(delegateTask.getProcessDefinitionId(), delegateTask.getTaskDefinitionKey());
        if (multiInstance) {
            this.calculateActor(delegateTask);
        }
    }

    private void calculateActor(DelegateTask delegateTask) {
        ArrayList<String> temp = new ArrayList<String>();
        String fliterUsed = this.fliterUsed();
        temp.add(fliterUsed);
        delegateTask.setVariable(delegateTask.getId(), (Object)fliterUsed);
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension(CounterSignConst.COUNTERSIGN_NR);
        Object object = extension.get(CounterSignConst.COUNTERSIGN_USED);
        if (object != null) {
            CounterSignActor counterSignActor = (CounterSignActor)object;
            Set<String> actors = counterSignActor.getActors();
            actors.add(fliterUsed);
        } else {
            CounterSignActor counterSignActor = new CounterSignActor();
            HashSet<String> temp2 = new HashSet<String>();
            temp2.add(fliterUsed);
            counterSignActor.setActors(temp2);
            extension.put(CounterSignConst.COUNTERSIGN_USED, (Serializable)counterSignActor);
        }
    }

    private String fliterUsed() {
        Iterator<String> iterator;
        NpContext context = NpContextHolder.getContext();
        ContextExtension extension = context.getExtension(CounterSignConst.COUNTERSIGN_NR);
        Object object = extension.get(CounterSignConst.COUNTERSIGN_ROLE);
        CounterSignCount counterSignCount = (CounterSignCount)object;
        Set<String> actors = counterSignCount.getActors();
        Object used = extension.get(CounterSignConst.COUNTERSIGN_USED);
        if (used != null) {
            CounterSignActor counterSignActor = (CounterSignActor)used;
            Set<String> usedActors = counterSignActor.getActors();
            if (usedActors != null && usedActors.size() > 0) {
                actors.removeAll(usedActors);
                Iterator<String> iterator2 = actors.iterator();
                if (iterator2.hasNext()) {
                    return iterator2.next();
                }
            } else {
                Iterator<String> iterator3 = actors.iterator();
                if (iterator3.hasNext()) {
                    return iterator3.next();
                }
            }
        }
        if ((iterator = actors.iterator()).hasNext()) {
            return iterator.next();
        }
        return null;
    }

    private boolean isMultiInstance(String processDefinitionId, String taskDefinitionKey) {
        ParallelMultiInstanceBehavior behavior;
        org.activiti.bpmn.model.UserTask userTask;
        boolean flag = false;
        Process process = ProcessDefinitionUtil.getProcess((String)processDefinitionId);
        FlowElement flowElement = process.getFlowElement(taskDefinitionKey);
        if (flowElement instanceof org.activiti.bpmn.model.UserTask && (userTask = (org.activiti.bpmn.model.UserTask)flowElement).getBehavior() instanceof ParallelMultiInstanceBehavior && (behavior = (ParallelMultiInstanceBehavior)userTask.getBehavior()) != null) {
            flag = true;
        }
        return flag;
    }
}

