/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.impl.process.service.impl.ProcessDeployServiceImpl;
import com.jiuqi.nr.bpm.impl.process.service.impl.ProcessRuntimeServiceImpl;
import com.jiuqi.nr.bpm.impl.process.util.ProcessUtil;
import com.jiuqi.nr.bpm.service.AbstractRuntimeService;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import java.util.List;
import java.util.Optional;

public class DefaultProcessEngine
implements ProcessEngine {
    private ProcessEngineProvider processEngineProvider;
    private AbstractRuntimeService runTimeService;
    private ProcessDeployServiceImpl deployService = new ProcessDeployServiceImpl();

    public DefaultProcessEngine() {
        this.runTimeService = new ProcessRuntimeServiceImpl(this.deployService);
    }

    @Override
    public ProcessEngine.ProcessEngineType getType() {
        return ProcessEngine.ProcessEngineType.UPLOAD;
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
        return this.processEngineProvider.getProcessEngine().get().getHistoryService();
    }

    @Override
    public ProcessEngine setProcessType(ProcessType processType) {
        this.runTimeService.setProcessType(processType);
        this.deployService.setProcessType(processType);
        return this;
    }

    public DefaultProcessEngine setProcessEngineProvider(ProcessEngineProvider processEngineProvider) {
        this.processEngineProvider = processEngineProvider;
        return this;
    }

    public DefaultProcessEngine setActorStrategyProvider(ActorStrategyProvider actorStrategyProvider) {
        this.runTimeService.setActorStrategyProvider(actorStrategyProvider);
        return this;
    }

    public DefaultProcessEngine setDispatcher(Optional<EventDispatcher> dispatcher) {
        this.runTimeService.setUserActionEventHandler(dispatcher);
        return this;
    }

    public DefaultProcessEngine setNrParameterUtils(NrParameterUtils nrParameterUtils) {
        this.runTimeService.setNrParameterUtils(nrParameterUtils);
        return this;
    }

    public DefaultProcessEngine setProcessUtil(ProcessUtil processUtil) {
        this.runTimeService.setProcessUtil(processUtil);
        return this;
    }

    public DefaultProcessEngine setProcessTaskBuilder(List<ProcessTaskBuilder> processTaskBuilder) {
        this.runTimeService.setProcessTaskBuilder(processTaskBuilder);
        this.deployService.setProcessTaskBuilders(processTaskBuilder);
        return this;
    }

    public DefaultProcessEngine setProcessStateHistoryDao(ProcessStateHistoryDao processStateHistoryDao) {
        this.runTimeService.setProcessStateHistoryDao(processStateHistoryDao);
        return this;
    }

    public DefaultProcessEngine setConditionalExecutes(List<IConditionalExecute> conditionalExecutes) {
        this.runTimeService.setConditionalExecute(conditionalExecutes);
        return this;
    }
}

