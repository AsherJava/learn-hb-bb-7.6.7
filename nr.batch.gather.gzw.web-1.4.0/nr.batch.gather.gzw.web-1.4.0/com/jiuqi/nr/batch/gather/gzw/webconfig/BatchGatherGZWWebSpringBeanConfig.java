/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.gather.gzw.webconfig;

import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandler;
import com.jiuqi.nr.batch.gather.gzw.web.ext.database.BeforeViewPageDataGZWHandlerImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.nr.batch.gather.gzw.web"})
public class BatchGatherGZWWebSpringBeanConfig {
    @Bean
    @ConditionalOnMissingBean
    public BeforeViewPageDataGZWHandler defBeforeViewPageDataGZWHandlerImpl() {
        return new BeforeViewPageDataGZWHandlerImpl();
    }
}

