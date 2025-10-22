/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.StreamReadConstraints
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.impl.DefaultConnectionProvider
 *  org.springframework.transaction.annotation.EnableTransactionManagement
 */
package com.jiuqi.gcreport.common.config;

import com.fasterxml.jackson.core.StreamReadConstraints;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.common.np.NpReportQueryProvider;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.impl.DefaultConnectionProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScans(value={@ComponentScan(value={"com.jiuqi.gcreport.common"}), @ComponentScan(basePackageClasses={SpringContextUtils.class, NpReportQueryProvider.class})})
@EnableTransactionManagement
public class GcReportCommonConfiguration {
    @Bean
    Jackson2ObjectMapperBuilderCustomizer customStreamReadConstraints() {
        return builder -> builder.postConfigurer(objectMapper -> objectMapper.getFactory().setStreamReadConstraints(StreamReadConstraints.builder().maxNestingDepth(2000).maxStringLength(100000000).build()));
    }

    @Configuration
    static class GcConnectionProviderConfiguration {
        GcConnectionProviderConfiguration() {
        }

        @Bean
        @ConditionalOnMissingBean(value={IConnectionProvider.class})
        public IConnectionProvider getDefaultConnectionProvider() {
            return new DefaultConnectionProvider();
        }
    }
}

