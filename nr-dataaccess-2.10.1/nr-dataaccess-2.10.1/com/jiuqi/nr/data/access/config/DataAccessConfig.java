/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory
 *  com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider
 */
package com.jiuqi.nr.data.access.config;

import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.access.service.IFormConditionAccessService;
import com.jiuqi.nr.data.access.service.impl.DataAccessEvaluatorFactory;
import com.jiuqi.nr.data.access.service.impl.FormConditionAccessServiceImpl;
import com.jiuqi.nr.data.access.service.impl.FormWriteableAccessServiceImpl;
import com.jiuqi.nr.data.access.service.impl.UnitWriteFormAccessServiceImpl;
import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.data.access"})
public class DataAccessConfig {
    @Autowired
    private DefinitionAuthorityProvider authorityProvider;

    @Bean
    @ConditionalOnMissingBean
    public IFormConditionAccessService getFormConditionAccessService() {
        return new FormConditionAccessServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public FormWriteableAccessServiceImpl getFormWriteableAccessServiceImpl() {
        return new FormWriteableAccessServiceImpl(this.authorityProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    public UnitWriteFormAccessServiceImpl getUnitWriteFormAccessServiceImpl() {
        return new UnitWriteFormAccessServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public DataPermissionEvaluatorFactory getDataPermissionEvaluatorFactory(IDataAccessServiceProvider dataAccessProvider) {
        return new DataAccessEvaluatorFactory(dataAccessProvider);
    }
}

