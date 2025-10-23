/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.definition;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.nr.workflow2.engine.common.definition.ProcessDefinitionProvider;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionBuilder;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessDesignService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessDefinitionProvider
extends ProcessDefinitionProvider
implements InitializingBean {
    @Autowired
    private IActorStrategyFactory actorStrategyFactory;
    @Autowired
    private IActionEventFactory eventFactory;
    @Autowired
    private DefaultProcessDesignService processDesignService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private NedisCacheManager cacheManager;
    private static final String CACHENAME = "nr.workflow2.engine.dft.definition";
    private NedisCache cache;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.cache = this.cacheManager.getCache(CACHENAME);
    }

    @Override
    public String engineName() {
        return "jiuqi.nr.default";
    }

    @Override
    public ProcessDefinition getProcessDefintion(String processDefinitionId) {
        ProcessDefinition processDefinition = (ProcessDefinition)this.cache.get(processDefinitionId, ProcessDefinition.class);
        if (processDefinition == null) {
            processDefinition = this.buildProcessDefintion(processDefinitionId);
            this.cache.put(processDefinitionId, (Object)processDefinition);
        }
        if (ProcessDefinition.NOT_EXISTS_PROCESSDEFINTION.equals(processDefinition)) {
            return null;
        }
        return processDefinition;
    }

    private ProcessDefinition buildProcessDefintion(String processDefinitionId) {
        DefaultProcessConfig procesConfig = this.processDesignService.queryDefaultProcessConfig(processDefinitionId);
        if (procesConfig == null) {
            return ProcessDefinition.NOT_EXISTS_PROCESSDEFINTION;
        }
        try {
            DefaultProcessDefinitionBuilder builder = new DefaultProcessDefinitionBuilder(processDefinitionId, procesConfig, this.actorStrategyFactory, this.eventFactory, this.roleService);
            return builder.build();
        }
        catch (Exception e) {
            throw new ProcessDefinitionException("jiuqi.nr.default", processDefinitionId, (Throwable)e);
        }
    }

    @Override
    public void onProcessChanged(String processDefinitionId) {
        this.cache.evict(processDefinitionId);
    }
}

