/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.annotation.config;

import com.jiuqi.nr.annotation.mapping.IAnnotationMappingProvider;
import com.jiuqi.nr.annotation.mapping.impl.DefaultAnnotationMappingProviderImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.annotation"})
public class AnnotationConfiguration {
    @Bean
    @ConditionalOnMissingBean(value={IAnnotationMappingProvider.class})
    public IAnnotationMappingProvider getIAnnotationMappingProvider() {
        return new DefaultAnnotationMappingProviderImpl();
    }
}

