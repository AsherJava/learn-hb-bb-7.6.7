/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.ProcessEngine
 *  org.activiti.engine.ProcessEngineConfiguration
 */
package com.jiuqi.nr.bpm.impl.activiti6;

import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.common.ProcessProvider;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.impl.activiti6.Activiti6DeployServiceImpl;
import com.jiuqi.nr.bpm.impl.activiti6.Activiti6HistoryServiceImpl;
import com.jiuqi.nr.bpm.impl.activiti6.Activiti6RunTimeServiceImpl;
import com.jiuqi.nr.bpm.impl.activiti6.extension.ExtensionService;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.service.AbstractRuntimeService;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import java.util.List;
import java.util.Optional;
import org.activiti.engine.ProcessEngineConfiguration;
import org.springframework.util.Assert;

class Activiti6ProcessEngine
implements ProcessEngine {
    private final org.activiti.engine.ProcessEngine activitiProcessEngine;
    private final Activiti6DeployServiceImpl deployService;
    private final AbstractRuntimeService runTimeService;
    private final Activiti6HistoryServiceImpl historyService;

    public Activiti6ProcessEngine(org.activiti.engine.ProcessEngine innerEngine) {
        Assert.notNull((Object)innerEngine, "parameter 'innerEngine' must not be null.");
        this.activitiProcessEngine = innerEngine;
        ExtensionService extensionService = new ExtensionService(this.activitiProcessEngine);
        this.deployService = new Activiti6DeployServiceImpl(this.activitiProcessEngine, extensionService);
        this.runTimeService = new Activiti6RunTimeServiceImpl(this.activitiProcessEngine, this.deployService, extensionService);
        this.historyService = new Activiti6HistoryServiceImpl(this.activitiProcessEngine);
    }

    public Activiti6ProcessEngine setActorStrategyProvider(ActorStrategyProvider actorStrategyProvider) {
        Assert.notNull((Object)actorStrategyProvider, "parameter 'actorStrategyProvider' must not be null.");
        this.runTimeService.setActorStrategyProvider(actorStrategyProvider);
        return this;
    }

    public Activiti6ProcessEngine setUserActionEventHandler(EventDispatcher actionEventHandler) {
        this.runTimeService.setUserActionEventHandler(Optional.ofNullable(actionEventHandler));
        this.deployService.setActionEventHandler(actionEventHandler);
        return this;
    }

    public Activiti6ProcessEngine setProcessProviders(List<ProcessProvider> processProviders) {
        this.runTimeService.setProcessProviders(processProviders);
        return this;
    }

    public Activiti6ProcessEngine setNrParameterUtils(NrParameterUtils nrParameterUtils) {
        this.runTimeService.setNrParameterUtils(nrParameterUtils);
        return this;
    }

    public Activiti6ProcessEngine setConditionalExecute(List<IConditionalExecute> setConditionalExecute) {
        this.runTimeService.setConditionalExecute(setConditionalExecute);
        return this;
    }

    @Override
    public ProcessEngine.ProcessEngineType getType() {
        return ProcessEngine.ProcessEngineType.ACTIVITI;
    }

    @Override
    public DeployService getDeployService() {
        return this.deployService;
    }

    @Override
    public RunTimeService getRunTimeService() {
        return this.runTimeService;
    }

    @Override
    public HistoryService getHistoryService() {
        return this.historyService;
    }

    @Override
    public ProcessEngine setProcessType(ProcessType processType) {
        this.runTimeService.setProcessType(processType);
        return this;
    }

    public ProcessEngineConfiguration getProcessEngineConfiguration() {
        return this.activitiProcessEngine.getProcessEngineConfiguration();
    }

    public void close() {
        this.activitiProcessEngine.close();
    }
}

