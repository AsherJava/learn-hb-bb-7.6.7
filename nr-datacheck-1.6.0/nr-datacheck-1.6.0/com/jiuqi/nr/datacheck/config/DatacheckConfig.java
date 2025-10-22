/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacheck.config;

import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupDefaultImpl;
import com.jiuqi.nr.datacheck.dataanalyze.AnalyzeRuleGroupProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value={"com.jiuqi.nr.datacheck"})
@Configuration
public class DatacheckConfig {
    @Bean
    @ConditionalOnMissingBean(value={AnalyzeRuleGroupProvider.class})
    public AnalyzeRuleGroupProvider getAnalyzeRuleGroupProvider() {
        return new AnalyzeRuleGroupDefaultImpl();
    }
}

