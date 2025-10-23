/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowEngineConfigSaveExecutor
 */
package com.jiuqi.nr.workflow2.settings.factory;

import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowEngineConfigSaveExecutor;
import com.jiuqi.nr.workflow2.settings.factory.IConfigSaveExecutorFactory;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigSaveExecutorFactoryImpl
implements IConfigSaveExecutorFactory {
    private Map<String, WorkflowEngineConfigSaveExecutor> map;
    private List<WorkflowEngineConfigSaveExecutor> list;

    @Autowired
    public ConfigSaveExecutorFactoryImpl(List<WorkflowEngineConfigSaveExecutor> executors) {
        this.list = executors;
        this.map = executors.stream().collect(Collectors.toMap(WorkflowEngineConfigSaveExecutor::getWorkflowEngineName, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public WorkflowEngineConfigSaveExecutor getConfigSaveExecutor(String workflowEngine) {
        return this.map.get(workflowEngine);
    }

    @Override
    public List<WorkflowEngineConfigSaveExecutor> getAllConfigSaveExecutors() {
        return this.list;
    }
}

