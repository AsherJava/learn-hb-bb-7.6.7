/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.cloud.openfeign.EnableFeignClients
 */
package com.jiuqi.nr.entity.config;

import com.jiuqi.nr.entity.adapter.impl.basedata.auth.DefaultBaseDataRelationServiceImpl;
import com.jiuqi.nr.entity.adapter.impl.org.auth.OrgAuthTypeExtendImpl;
import com.jiuqi.nr.entity.component.currency.OrgCurrencyService;
import com.jiuqi.nr.entity.component.currency.impl.OrgCurrencyServiceImpl;
import com.jiuqi.nr.entity.component.dwdm.EntityDWDMService;
import com.jiuqi.nr.entity.component.dwdm.impl.EntityDWDMServiceImpl;
import com.jiuqi.nr.entity.component.fix.IFixService;
import com.jiuqi.nr.entity.component.fix.impl.FixServiceImpl;
import com.jiuqi.nr.entity.component.tree.service.EntityTreeService;
import com.jiuqi.nr.entity.component.tree.service.impl.EntityTreeServiceImpl;
import com.jiuqi.nr.entity.config.IsolatingBaseDataConfig;
import com.jiuqi.nr.entity.ext.auth.basedata.IBaseDataRelationService;
import com.jiuqi.nr.entity.ext.auth.org.IndependentOrgAuthProvider;
import com.jiuqi.nr.entity.ext.currency.OrgCurrencyAttributeMenuExtend;
import com.jiuqi.nr.entity.ext.dwdm.DWDMFieldSelectedMenuExtend;
import com.jiuqi.nr.entity.ext.dwdm.DWDMGeneratorMenuExtend;
import com.jiuqi.nr.entity.ext.dwdm.DWDMIDCCheckInterceptor;
import com.jiuqi.nr.entity.ext.filter.IEntityReferFilter;
import com.jiuqi.nr.entity.ext.filter.impl.DefaultEntityReferFilter;
import com.jiuqi.nr.entity.ext.option.OrgAuthSystemOptions;
import com.jiuqi.nr.entity.ext.version.IVersionQueryService;
import com.jiuqi.nr.entity.ext.version.impl.VersionQueryServiceImpl;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.internal.service.impl.AdapterServiceImpl;
import com.jiuqi.nr.entity.service.EntityEngine;
import com.jiuqi.nr.entity.service.IEntityAssist;
import com.jiuqi.nr.entity.service.IEntityAuthorityService;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityDefineAssist;
import com.jiuqi.nr.entity.service.IEntityIOService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.entity.service.impl.EntityAssistImpl;
import com.jiuqi.nr.entity.service.impl.EntityAuthorityServiceImpl;
import com.jiuqi.nr.entity.service.impl.EntityDataServiceImpl;
import com.jiuqi.nr.entity.service.impl.EntityEngineImpl;
import com.jiuqi.nr.entity.service.impl.EntityIOServiceImpl;
import com.jiuqi.nr.entity.service.impl.EntityMetaServiceImpl;
import com.jiuqi.nr.entity.service.impl.IEntityDefineAssistImpl;
import com.jiuqi.nr.entity.web.EntityTreeController;
import com.jiuqi.nr.entity.web.JUIEntityTreeController;
import com.jiuqi.nr.entity.web.OrgComponentController;
import com.jiuqi.nr.entity.web.OrgDataFixController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value={"com.jiuqi.nr.entity.adapter"})
@EnableFeignClients(value={"com.jiuqi.nr.entity.adapter.impl.org.client", "com.jiuqi.nr.entity.adapter.impl.basedata.client"})
public class EntityConfig {
    @Bean
    public EntityEngine entityEngine() {
        return new EntityEngineImpl();
    }

    @Bean
    public EntityTreeService entityTreeService() {
        return new EntityTreeServiceImpl();
    }

    @Bean
    public EntityTreeController entityTreeController() {
        return new EntityTreeController();
    }

    @Bean
    public JUIEntityTreeController juiEntityTreeController() {
        return new JUIEntityTreeController();
    }

    @Bean
    public AdapterService adapterService() {
        return new AdapterServiceImpl();
    }

    @Bean
    public IEntityAuthorityService entityAuthorityService() {
        return new EntityAuthorityServiceImpl();
    }

    @Bean
    public IEntityDataService entityDataService() {
        return new EntityDataServiceImpl();
    }

    @Bean
    public IEntityIOService entityIOService() {
        return new EntityIOServiceImpl();
    }

    @Bean
    public IEntityMetaService entityMetaService() {
        return new EntityMetaServiceImpl();
    }

    @Bean
    public IEntityAssist entityAssist() {
        return new EntityAssistImpl();
    }

    @Bean
    public OrgAuthTypeExtendImpl getOrgAuthTypeExtendImpl() {
        return new OrgAuthTypeExtendImpl();
    }

    @Bean
    public OrgAuthSystemOptions getOrgAuthSystemOptions() {
        return new OrgAuthSystemOptions();
    }

    @Bean
    public IndependentOrgAuthProvider getIndependentOrgAuthProvider() {
        return new IndependentOrgAuthProvider();
    }

    @Bean
    public IEntityReferFilter getEntityReferFilter() {
        return new DefaultEntityReferFilter();
    }

    @Bean
    public IVersionQueryService getVersionQueryService() {
        return new VersionQueryServiceImpl();
    }

    @Bean
    public IsolatingBaseDataConfig getIsolatingBaseDataConfig() {
        return new IsolatingBaseDataConfig();
    }

    @Bean
    public OrgComponentController getIdcController() {
        return new OrgComponentController();
    }

    @Bean
    public DWDMIDCCheckInterceptor getOrgCodeIDCCheckInterceptor() {
        return new DWDMIDCCheckInterceptor();
    }

    @Bean
    public DWDMFieldSelectedMenuExtend getDWDMFieldSelectedMenuExtend() {
        return new DWDMFieldSelectedMenuExtend();
    }

    @Bean
    public DWDMGeneratorMenuExtend getDWDMGeneratorMenuExtend() {
        return new DWDMGeneratorMenuExtend();
    }

    @Bean
    public EntityDWDMService getEntityDWDMService() {
        return new EntityDWDMServiceImpl();
    }

    @Bean
    public OrgCurrencyAttributeMenuExtend getOrgCurrencyAttributeMenuExtend() {
        return new OrgCurrencyAttributeMenuExtend();
    }

    @Bean
    public OrgCurrencyService getOrgCurrencyService() {
        return new OrgCurrencyServiceImpl();
    }

    @Bean
    public IEntityDefineAssist getIEntityDefineAssist() {
        return new IEntityDefineAssistImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public IBaseDataRelationService getIBaseDataRelationService() {
        return new DefaultBaseDataRelationServiceImpl();
    }

    @Bean
    public OrgDataFixController getOrgDataFixController() {
        return new OrgDataFixController();
    }

    @Bean
    public IFixService getIFixService() {
        return new FixServiceImpl();
    }
}

