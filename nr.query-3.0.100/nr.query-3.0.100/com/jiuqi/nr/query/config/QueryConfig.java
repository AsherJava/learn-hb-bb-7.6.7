/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.config;

import com.jiuqi.nr.query.auth.QueryModelAuthorityProvider;
import com.jiuqi.nr.query.auth.authz2.QueryModelAuthorityService;
import com.jiuqi.nr.query.service.IQueryDimensionController;
import com.jiuqi.nr.query.service.IQueryGridDataController;
import com.jiuqi.nr.query.service.IQueryModalController;
import com.jiuqi.nr.query.service.LocalCacheManager;
import com.jiuqi.nr.query.service.QueryCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages={"com.jiuqi.nr.query", "com.jiuqi.nr.query.service", "com.jiuqi.nr.query.auth", "com.jiuqi.nr.query.transfer"})
@Configuration
public class QueryConfig {
    public IQueryModalController getQueryModelController() {
        return new IQueryModalController();
    }

    public IQueryGridDataController getQueryGridController() {
        return new IQueryGridDataController();
    }

    public IQueryDimensionController getQueryDimensionController() {
        return new IQueryDimensionController();
    }

    @Bean
    @ConditionalOnMissingBean(value={QueryCacheManager.class})
    public QueryCacheManager getQueryCacheManager() {
        return new QueryCacheManager();
    }

    @Bean
    @ConditionalOnMissingBean(value={LocalCacheManager.class})
    public LocalCacheManager getLocalCacheManager() {
        return new LocalCacheManager();
    }

    @Bean
    @ConditionalOnMissingBean(value={QueryModelAuthorityProvider.class})
    public QueryModelAuthorityProvider QueryModelAuthorityProvider() {
        return new QueryModelAuthorityService();
    }
}

