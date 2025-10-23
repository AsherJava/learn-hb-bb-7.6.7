/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCacheProvider
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDao
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDaoImpl
 *  com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInstanceParser
 *  org.springframework.jdbc.core.JdbcTemplate
 */
package com.jiuqi.nr.workflow2.settings.spring;

import com.jiuqi.np.cache.NedisCacheProvider;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDao;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDaoImpl;
import com.jiuqi.nr.workflow2.events.executor.msg.parser.MessageInstanceParser;
import com.jiuqi.nr.workflow2.settings.filter.WorkflowEngineFilter;
import com.jiuqi.nr.workflow2.settings.listener.WorkflowSettingsInitListener;
import com.jiuqi.nr.workflow2.settings.message.dao.MessageSampleDao;
import com.jiuqi.nr.workflow2.settings.message.service.MessageInstanceService;
import com.jiuqi.nr.workflow2.settings.message.service.MessageInstanceServiceImpl;
import com.jiuqi.nr.workflow2.settings.message.service.MessageSampleService;
import com.jiuqi.nr.workflow2.settings.message.service.MessageSampleServiceImpl;
import com.jiuqi.nr.workflow2.settings.message.service.parser.MessageInstanceParserImpl;
import com.jiuqi.nr.workflow2.settings.message.web.MessageInstanceController;
import com.jiuqi.nr.workflow2.settings.message.web.MessageSampleController;
import com.jiuqi.nr.workflow2.settings.register.WorkflowSettingsAppActionRegister;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationServiceImpl;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsPersistService;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsPersistServiceImpl;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsQueryServiceImpl;
import com.jiuqi.nr.workflow2.settings.spring.PackageScanConfiguration;
import com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsConvertController;
import com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsManipulationController;
import com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsOtherQueryController;
import com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsQueryController;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@AutoConfiguration
@Import(value={PackageScanConfiguration.class})
public class WorkflowSettingAutoConfiguration {
    @Bean(value={"com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsManipulationService"})
    public WorkflowSettingsManipulationService workflowSettingsManipulationService(JdbcTemplate jdbcTemplate, NedisCacheProvider cacheProvider) {
        return new WorkflowSettingsManipulationServiceImpl((WorkflowSettingsDao)new WorkflowSettingsDaoImpl(jdbcTemplate), cacheProvider);
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsPersistServiceImpl"})
    public WorkflowSettingsPersistService workflowSettingsPersistService() {
        return new WorkflowSettingsPersistServiceImpl();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsQueryServiceImpl"})
    public WorkflowSettingsQueryServiceImpl workflowSettingsQueryService() {
        return new WorkflowSettingsQueryServiceImpl();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.message.service.MessageInstanceService"})
    public MessageInstanceService messageInstanceService() {
        return new MessageInstanceServiceImpl();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.message.service.MessageSampleService"})
    public MessageSampleService messageSampleService() {
        return new MessageSampleServiceImpl();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsQueryController"})
    public WorkflowSettingsQueryController workflowSettingsQueryController() {
        return new WorkflowSettingsQueryController();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsManipulationController"})
    public WorkflowSettingsManipulationController workflowSettingsManipulationController() {
        return new WorkflowSettingsManipulationController();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsOtherQueryController"})
    public WorkflowSettingsOtherQueryController workflowSettingsOtherQueryController() {
        return new WorkflowSettingsOtherQueryController();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.web.WorkflowSettingsConvertController"})
    public WorkflowSettingsConvertController workflowSettingsConvertController() {
        return new WorkflowSettingsConvertController();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.service.message.web.MessageInstanceController"})
    public MessageInstanceController messageInstanceController() {
        return new MessageInstanceController();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.service.message.web.MessageSampleController"})
    public MessageSampleController messageSampleController() {
        return new MessageSampleController();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.message.web.dao.MessageSampleDao"})
    public MessageSampleDao messageSampleDao() {
        return new MessageSampleDao();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.listener.WorkflowSettingsInitListener"})
    public WorkflowSettingsInitListener workflowSettingsInitListener() {
        return new WorkflowSettingsInitListener();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.register.WorkflowSettingsAppActionRegister"})
    public WorkflowSettingsAppActionRegister workflowSettingsAppActionRegister() {
        return new WorkflowSettingsAppActionRegister();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.filter.WorkflowEngineFilter"})
    public WorkflowEngineFilter workflowEngineFilter() {
        return new WorkflowEngineFilter();
    }

    @Bean(value={"com.jiuqi.nr.workflow2.settings.message.service.parser.MessageInstanceParserImpl"})
    public MessageInstanceParser messageInstanceParser() {
        return new MessageInstanceParserImpl();
    }
}

