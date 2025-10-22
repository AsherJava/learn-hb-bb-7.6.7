/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.common;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ProcessEngineProviderImpl
implements ProcessEngineProvider {
    @Autowired
    private NrParameterUtils parameterUtils;
    @Autowired(required=false)
    private List<ProcessEngine> processEngines;

    @Override
    public Optional<ProcessEngine> getProcessEngine(String taskDefineKey) {
        Assert.notNull((Object)taskDefineKey, "parameter 'taskDefineKey' must not be null.");
        ProcessEngine processEngine = this.processEngines.stream().filter(pro -> pro.getType() == ProcessEngine.ProcessEngineType.ACTIVITI).findFirst().get();
        return this.isProcessConfiged(taskDefineKey) ? Optional.ofNullable(processEngine) : Optional.empty();
    }

    @Override
    public Optional<ProcessEngine> getProcessEngine(ProcessType processType) {
        Assert.notNull((Object)processType, "parameter 'taskDefineKey' must not be null.");
        return this.getProcessEngineByType(processType);
    }

    private boolean isProcessConfiged(String taskDefineKey) {
        return true;
    }

    private Optional<ProcessEngine> getProcessEngineByType(ProcessType processType) {
        Optional<ProcessEngine> processEngine;
        if ((processType == ProcessType.DEFAULT || processType == ProcessType.SIMPLE_ACTIVIT) && (processEngine = this.processEngines.stream().filter(pro -> pro.getType() == ProcessEngine.ProcessEngineType.UPLOAD).findFirst()).isPresent()) {
            ProcessEngine engine = processEngine.get();
            engine.setProcessType(processType);
            return Optional.ofNullable(engine);
        }
        return this.processEngines.stream().filter(pro -> pro.getType() == ProcessEngine.ProcessEngineType.ACTIVITI).findFirst();
    }

    @Override
    public Optional<ProcessEngine> getProcessEngine() {
        Optional<ProcessEngine> processEngine = this.processEngines.stream().filter(pro -> pro.getType() == ProcessEngine.ProcessEngineType.ACTIVITI).findFirst();
        return processEngine;
    }
}

