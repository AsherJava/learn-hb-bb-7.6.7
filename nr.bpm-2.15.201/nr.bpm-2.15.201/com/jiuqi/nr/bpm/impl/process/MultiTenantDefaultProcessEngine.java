/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.process;

import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.MultiTenantProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.condition.IConditionalExecute;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.event.EventDispatcher;
import com.jiuqi.nr.bpm.impl.process.DefaultProcessEngine;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.impl.process.util.ProcessUtil;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class MultiTenantDefaultProcessEngine
extends MultiTenantProcessEngine
implements DisposableBean {
    private final ConcurrentMap<String, DefaultProcessEngine> innerEngines = new ConcurrentHashMap<String, DefaultProcessEngine>();
    @Autowired
    private ActorStrategyProvider actorStrategyProvider;
    @Autowired
    private Optional<EventDispatcher> dispatcher;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired(required=false)
    private List<ProcessTaskBuilder> processTaskBuilder;
    @Autowired
    private ProcessStateHistoryDao processStateHistoryDao;
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired(required=false)
    private List<IConditionalExecute> conditionalExecutes;
    @Autowired
    private ProcessUtil processUtil;

    MultiTenantDefaultProcessEngine() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected ProcessEngine getCurrentProcessEngine() {
        DefaultProcessEngine engineFound = (DefaultProcessEngine)this.innerEngines.get("__default_tenant__");
        if (engineFound != null) {
            return engineFound;
        }
        MultiTenantDefaultProcessEngine multiTenantDefaultProcessEngine = this;
        synchronized (multiTenantDefaultProcessEngine) {
            engineFound = (DefaultProcessEngine)this.innerEngines.get("__default_tenant__");
            if (engineFound != null) {
                return engineFound;
            }
            DefaultProcessEngine newEngine = this.buildSimpleProcessEngine();
            DefaultProcessEngine existEngine = this.innerEngines.putIfAbsent("__default_tenant__", newEngine);
            if (existEngine == null) {
                engineFound = newEngine;
            } else {
                newEngine = null;
                engineFound = existEngine;
            }
        }
        return engineFound;
    }

    private DefaultProcessEngine buildSimpleProcessEngine() {
        DefaultProcessEngine simleProcess = new DefaultProcessEngine();
        return simleProcess.setActorStrategyProvider(this.actorStrategyProvider).setDispatcher(this.dispatcher).setProcessTaskBuilder(this.processTaskBuilder).setNrParameterUtils(this.nrParameterUtils).setProcessStateHistoryDao(this.processStateHistoryDao).setProcessEngineProvider(this.processEngineProvider).setConditionalExecutes(this.conditionalExecutes).setProcessUtil(this.processUtil);
    }

    @Override
    public ProcessEngine.ProcessEngineType getType() {
        return ProcessEngine.ProcessEngineType.UPLOAD;
    }

    @Override
    public void destroy() throws Exception {
        this.innerEngines.clear();
    }
}

