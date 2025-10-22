/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.event.TaskCreateEvent;
import com.jiuqi.nr.bpm.event.TaskCreateEventListener;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.ProcessTaskCreateBatchEvent;
import com.jiuqi.nr.bpm.impl.process.ProcessTaskCreateEvent;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessTaskCreateEventListener
implements TaskCreateEventListener {
    @Autowired
    ProcessStateHistoryDao processStateHistoryDao;
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired
    private NrParameterUtils nrParameterUtils;

    @Override
    public void onCreate(TaskCreateEvent event) {
        ProcessTaskCreateEvent taskCreateEvent = (ProcessTaskCreateEvent)event;
        BusinessKey businessKey = taskCreateEvent.getBusinessKey();
        this.processStateHistoryDao.updateState(businessKey, taskCreateEvent.getTaskId(), taskCreateEvent.getActionId(), taskCreateEvent.getForceUpload(), taskCreateEvent.getTaskId());
        String actionId = taskCreateEvent.getActionId();
        if (actionId.equals("start")) {
            this.sendMessageToDo(businessKey);
        }
    }

    @Override
    public void onBatchCreate(TaskCreateEvent event) {
        ProcessTaskCreateBatchEvent batchEvent = (ProcessTaskCreateBatchEvent)event;
        List<BusinessKey> businessKeys = batchEvent.getBusinessKey();
        for (BusinessKey businessKey : businessKeys) {
            this.sendMessageToDo(businessKey);
        }
    }

    private void sendMessageToDo(BusinessKey businessKey) {
        Optional<ProcessEngine> processEngine;
        RunTimeService runtimeService;
        List<Task> task;
        if (SendMessageTaskConfig.canSendMessage() && !(task = (runtimeService = (RunTimeService)(processEngine = this.nrParameterUtils.getProcessEngine(businessKey.getFormSchemeKey())).map(engine -> engine.getRunTimeService()).orElse(null)).queryTaskByBusinessKey(businessKey.toString())).isEmpty()) {
            Optional<UserTask> userTask = processEngine.get().getDeployService().getUserTask(null, task.get(0).getUserTaskId(), businessKey.getFormSchemeKey());
            this.actorStrategyProvider.sendTodoMessage(task.get(0), userTask.get(), businessKey.toString());
        }
    }

    @Override
    public FlowsType getFlowsType() {
        return FlowsType.DEFAULT;
    }
}

