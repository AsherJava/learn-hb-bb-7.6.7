/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.auth.authz2;

import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.auth.authz2.TaskAuthorityService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskAuthorityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={DefinitionAuthorityProvider.class})
    public DefinitionAuthorityProvider taskAuthorityProvider() {
        return new TaskAuthorityService();
    }
}

