/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.listedcompanyauthz.config;

import com.jiuqi.gcreport.listedcompanyauthz.config.ListedCompanySwagger2Configuration;
import com.jiuqi.gcreport.listedcompanyauthz.config.Swagger2Configuration;
import com.jiuqi.gcreport.listedcompanyauthz.rule.ListedCompanyFactoryPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages={"com.jiuqi.gcreport.listedcompanyauthz.dao", "com.jiuqi.gcreport.listedcompanyauthz.service", "com.jiuqi.gcreport.listedcompanyauthz.web", "com.jiuqi.gcreport.listedcompanyauthz.entity", "com.jiuqi.gcreport.listedcompanyauthz.rule", "com.jiuqi.gcreport.listedcompanyauthz.expimp", "com.jiuqi.gcreport.listedcompanyauthz.listener"})
@Import(value={ListedCompanySwagger2Configuration.class, Swagger2Configuration.class})
public class ListedCompanyConfiguration {
    @Bean(value={"com.jiuqi.gcreport.listedcompanyauthz.rule.ListedCompanyFactoryPostProcessor"})
    @ConditionalOnMissingBean(value={ListedCompanyFactoryPostProcessor.class})
    public static ListedCompanyFactoryPostProcessor initListedCompanyFactoryPostProcessor() {
        return new ListedCompanyFactoryPostProcessor();
    }
}

