/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.Actor.ActorUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryParticipants {
    @Autowired
    ActorStrategyProvider actorStrategyProvider;

    public Set<String> getSuperiorActor(Optional<ProcessEngine> processEngine, Task task, BusinessKey businessKey) {
        Optional<UserTask> targetTask;
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Set<String> superiorActor = new HashSet<String>();
        if (task != null && (targetTask = runtimeService.getTargetTaskById(task, "tsk_upload", businessKey)).isPresent()) {
            superiorActor = ActorUtils.getTaskActors(targetTask.get(), businessKey, this.actorStrategyProvider, task);
        }
        return superiorActor;
    }

    public Set<String> getCurrenActor(Optional<ProcessEngine> processEngine, Task afterTask, BusinessKey businessKey) {
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        HashSet<String> currenActor = new HashSet();
        Optional<UserTask> userTask = deployService.getUserTask(afterTask.getProcessDefinitionId(), afterTask.getUserTaskId(), businessKey.getFormSchemeKey());
        currenActor = ActorUtils.getTaskActors(userTask.get(), businessKey, this.actorStrategyProvider, afterTask);
        return currenActor;
    }

    public Set<String> getActors(UserTask userTask, BusinessKey businessKey, Task task) {
        return ActorUtils.getTaskActors(userTask, businessKey, this.actorStrategyProvider, task);
    }
}

