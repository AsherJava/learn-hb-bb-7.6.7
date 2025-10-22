/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.message.service.MessagePipelineService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  org.activiti.engine.impl.interceptor.Command
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SimpleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.SimpleMessageThreadLocalStrategy;
import com.jiuqi.nr.bpm.exception.ActorStrategyNotFound;
import com.jiuqi.nr.bpm.impl.Actor.ActorStrategyParameterType;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.impl.activiti6.extension.DeleteIdentityLinkCmd;
import com.jiuqi.nr.bpm.impl.activiti6.extension.InsertIdentityLinkCmd;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ReferCacheCmd;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.activiti.engine.impl.interceptor.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class ActorStrategyProviderImpl
implements ActorStrategyProvider,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(ActorStrategyProviderImpl.class);
    private Map<String, ActorStrategy<?>> actorStrategyMap;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    private List<ActorStrategy<?>> actorStrategies;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    ProcessEngineProvider processEngineProvider;
    @Autowired
    MessagePipelineService messagePipelineService;
    @Autowired
    ISendMessage sendMessage;
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Value(value="${jiuqi.nr.workflow.send-todo-async:true}")
    private boolean asyncSendTodo;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.actorStrategyMap = new HashMap();
        for (ActorStrategy<?> actorStrategy : this.actorStrategies) {
            if (this.actorStrategyMap.containsKey(actorStrategy.getType())) continue;
            this.actorStrategyMap.put(actorStrategy.getType(), actorStrategy);
        }
    }

    @Override
    public ActorStrategy<?> getActorStrategyByType(String actorStrategyType) {
        if (actorStrategyType == null || actorStrategyType.equals("")) {
            throw new IllegalArgumentException("actorStrategyType can not be null or empty.");
        }
        ActorStrategy<?> strategyWithGivenType = this.actorStrategyMap.get(actorStrategyType);
        if (strategyWithGivenType == null) {
            throw new ActorStrategyNotFound(actorStrategyType);
        }
        return strategyWithGivenType;
    }

    @Override
    public List<ActorStrategy<?>> getAllActorStrategies(boolean isContainDefault) {
        ArrayList allActorStrategies = new ArrayList();
        for (Map.Entry<String, ActorStrategy<?>> entry : this.actorStrategyMap.entrySet()) {
            ActorStrategy<?> actorStrategyClass = entry.getValue();
            if (!isContainDefault && actorStrategyClass.isDefault()) continue;
            allActorStrategies.add(actorStrategyClass);
        }
        return allActorStrategies;
    }

    @Override
    public ActorStrategyParameterType getActorStrategyParameterType(ActorStrategy<?> actorStrategy) {
        switch (actorStrategy.getParameterType().getSimpleName()) {
            case "SpecifiedAndGrantedUsersParameter": {
                return ActorStrategyParameterType.ROLEANDUSER;
            }
            case "SpecifiedUsersParameter": {
                return ActorStrategyParameterType.USER;
            }
            case "GrantedToEntityAndRoleParameter": {
                return ActorStrategyParameterType.ROLE;
            }
        }
        return ActorStrategyParameterType.NONE;
    }

    @Override
    public void refreshActorsByFormSchemeKey(String formSchemeKey, String period) {
        ProcessEngine engine = this.getProcessEngine(formSchemeKey);
        RunTimeService runtimeService = engine.getRunTimeService();
        List<ProcessInstance> instances = runtimeService.queryInstanceByFormSchemeKey(formSchemeKey, period);
        if (instances.isEmpty()) {
            return;
        }
        for (ProcessInstance instacne : instances) {
            if (engine.getType() == ProcessEngine.ProcessEngineType.ACTIVITI) {
                this.refreshActorsByProcessInstanceId(instacne.getId());
                continue;
            }
            this.refreshActorsByBusinessKey(instacne.getId());
        }
    }

    @Override
    public void refreshActorsByBusinessKey(String businessKey) {
        List<Task> tasks = this.getProcessEngine(BusinessKeyFormatter.parsingFromString(businessKey).getFormSchemeKey()).getRunTimeService().queryTaskByBusinessKey(businessKey);
        this.sendTodoMessage(tasks.get(0), this.queryCandidateUserIds(tasks.get(0), businessKey), businessKey);
    }

    @Override
    public void refreshActorsByProcessKey(String processKey) {
        List<ProcessInstance> processInstances = this.getProcessEngine(null).getRunTimeService().queryInstanceByProcessDefinitionKey(processKey);
        for (ProcessInstance processInstance : processInstances) {
            this.refreshActorsByProcessInstanceId(processInstance.getId());
        }
    }

    @Override
    public void refreshActorsByProcessInstanceId(String processInstanceId) {
        Optional<String> businessKey = this.getProcessEngine(null).getRunTimeService().getBusinessKey(processInstanceId);
        List<Task> tasks = this.getProcessEngine(null).getRunTimeService().queryTaskByProcessInstance(processInstanceId, null);
        this.doRefreshIdentityLinkCmd(tasks.get(0), this.queryCandidateUserIds(tasks.get(0), businessKey.get()));
        this.sendTodoMessage(tasks.get(0), this.queryCandidateUserIds(tasks.get(0), businessKey.get()), businessKey.get());
    }

    private void doRefreshIdentityLinkCmd(Task task, Set<String> candidateUserIds) {
        DeleteIdentityLinkCmd deleteCommand = new DeleteIdentityLinkCmd();
        InsertIdentityLinkCmd insertCommand = new InsertIdentityLinkCmd();
        ReferCacheCmd referCacheCmd = new ReferCacheCmd(task.getProcessDefinitionId());
        deleteCommand.setTaskId(task.getId());
        insertCommand.setCandidateUserIds(candidateUserIds);
        insertCommand.setTaskId(task.getId());
        this.getProcessEngine(null).getDeployService().getActivitExtensionService().getManagementService().executeCommand((Command)referCacheCmd);
        this.getProcessEngine(null).getDeployService().getActivitExtensionService().getManagementService().executeCommand((Command)deleteCommand);
        this.getProcessEngine(null).getDeployService().getActivitExtensionService().getManagementService().executeCommand((Command)insertCommand);
    }

    @Override
    public void sendTodoMessage(Task delegateTask, Set<String> candidateUserIds, String businessKeyStr) {
        try {
            if (SendMessageTaskConfig.canSendMessage()) {
                NpContext context = NpContextHolder.getContext();
                this.sendMessage.send(delegateTask, candidateUserIds, businessKeyStr, context.getUser().getFullname());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private ProcessEngine getProcessEngine(String formSchemeKey) {
        if (StringUtils.isEmpty((String)formSchemeKey)) {
            return this.processEngineProvider.getProcessEngine().get();
        }
        Optional<ProcessEngine> processEngine = this.nrParameterUtils.getProcessEngine(formSchemeKey);
        return processEngine.get();
    }

    @Override
    public void sendTodoMessage(Task delegateTask, UserTask userTask, String businessKeyStr) {
        try {
            if (SendMessageTaskConfig.canSendMessage()) {
                if (this.asyncSendTodo) {
                    SimpleMessageEvent simpleMessageEvent = new SimpleMessageEvent();
                    MessageData messageData = new MessageData();
                    messageData.setId(delegateTask.getId());
                    messageData.setName(delegateTask.getName());
                    messageData.setProcessDefinitionId(delegateTask.getProcessDefinitionId());
                    messageData.setProcessInstanceId(delegateTask.getProcessInstanceId());
                    messageData.setUserTaskId(delegateTask.getUserTaskId());
                    simpleMessageEvent.setTask(messageData);
                    simpleMessageEvent.setBusinessKey(BusinessKeyFormatter.parsingFromString(businessKeyStr));
                    NpContext context = NpContextHolder.getContext();
                    simpleMessageEvent.setOperator(context.getUser().getFullname());
                    SimpleMessageThreadLocalStrategy.addMessageBody(simpleMessageEvent);
                } else {
                    Set<String> candidateUserIds = this.queryCandidateUserIds(delegateTask, businessKeyStr);
                    NpContext context = NpContextHolder.getContext();
                    this.sendMessage.send(delegateTask, candidateUserIds, businessKeyStr, context.getUser().getFullname());
                }
            }
        }
        catch (Exception e) {
            logger.error("\u53d1\u9001\u4ee3\u529e\u5f02\u5e38", e);
        }
    }

    private Set<String> queryCandidateUserIds(Task task, String businessKey) {
        BusinessKey businessKeyInfo = BusinessKeyFormatter.parsingFromString(businessKey);
        Optional<UserTask> userTaskFound = this.getProcessEngine(businessKeyInfo.getFormSchemeKey()).getDeployService().getUserTask(task.getProcessDefinitionId(), task.getUserTaskId(), businessKeyInfo.getFormSchemeKey());
        UserTask userTask = userTaskFound.isPresent() ? userTaskFound.get() : null;
        Set<String> candidateUserIds = ActorUtils.getTaskActors(userTask, businessKeyInfo, this, task);
        return candidateUserIds;
    }
}

