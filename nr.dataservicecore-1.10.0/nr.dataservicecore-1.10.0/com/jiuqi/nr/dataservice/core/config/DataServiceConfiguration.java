/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.config;

import com.jiuqi.nr.dataservice.core.access.DataPermissionEvaluatorFactory;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.common.ProviderStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.dataservice.core"})
public class DataServiceConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public IProviderStore getProviderStore(@Autowired(required=false) DataPermissionEvaluatorFactory dataPermissionEvaluatorFactory) {
        return new ProviderStore(dataPermissionEvaluatorFactory);
    }
}

