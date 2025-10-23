/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.workflow2.engine.core.spring;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.workflow2.engine.core.IProcessEngineFactory;
import com.jiuqi.nr.workflow2.engine.core.ProcessEngineFactoryImpl;
import com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration;
import com.jiuqi.nr.workflow2.engine.core.actor.ActorStrategyFactoryImpl;
import com.jiuqi.nr.workflow2.engine.core.actor.ActorStrategyRegisteration;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.event.ActionEventFactoryImpl;
import com.jiuqi.nr.workflow2.engine.core.event.ActionEventRegisteration;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDaoImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsServiceImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
public class ProcessEngineAutoConfiguration {
    @Bean
    public IProcessEngineFactory processEngineFactory(List<ProcessEngineRegisteration> registerations) {
        return new ProcessEngineFactoryImpl(registerations);
    }

    @Bean
    public IActorStrategyFactory actorStrategyFactory(List<ActorStrategyRegisteration> registerations) {
        return new ActorStrategyFactoryImpl(registerations);
    }

    @Bean
    public IActionEventFactory actionFactory(List<ActionEventRegisteration> registerations) {
        return new ActionEventFactoryImpl(registerations);
    }

    @Bean
    public WorkflowSettingsService workflowSettingsService(JdbcTemplate jdbcTemplate, NedisCacheProvider cacheProvider) {
        return new WorkflowSettingsServiceImpl(new WorkflowSettingsDaoImpl(jdbcTemplate), cacheProvider);
    }

    @Bean
    public Workflow2EngineCompatibleCollector workflow2CompatibleCollector() {
        return new Workflow2EngineCompatibleCollector();
    }
}

