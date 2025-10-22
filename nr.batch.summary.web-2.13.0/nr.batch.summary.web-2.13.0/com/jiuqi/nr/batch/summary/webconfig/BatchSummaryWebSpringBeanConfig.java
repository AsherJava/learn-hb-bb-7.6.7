/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.webconfig;

import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandler;
import com.jiuqi.nr.batch.summary.web.ext.database.BeforeViewPageDataHandlerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.batch.summary.web"})
public class BatchSummaryWebSpringBeanConfig {
    @Bean
    @ConditionalOnMissingBean
    public BeforeViewPageDataHandler defBeforeViewPageDataHandlerImpl() {
        return new BeforeViewPageDataHandlerImpl();
    }
}

