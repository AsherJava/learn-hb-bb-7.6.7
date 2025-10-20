/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.config;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.DefaultOrgMappingService;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.impl.BdeDefaultOrgMappingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BdeOrgMappingConfig {
    @Bean
    @ConditionalOnMissingBean(value={DefaultOrgMappingService.class})
    private DefaultOrgMappingService getDefaultOrgMapping() {
        return new BdeDefaultOrgMappingService();
    }
}

