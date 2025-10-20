/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceNameResolver;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.ProxyDataSourceDecorator;
import java.util.List;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(value={"spring.cloud.service-registry.auto-registration.enabled"}, matchIfMissing=true)
@ComponentScan(basePackages={"com.jiuqi.nvwa.sf.adapter.spring.dsproxy"})
@Configuration
public class DataSourceDecoratorAutoConfiguration {
    @Bean
    public ProxyDataSourceDecorator proxyDataSourceDecorator(DataSourceNameResolver dataSourceNameResolver, List<QueryExecutionListener> listeners) {
        return new ProxyDataSourceDecorator(dataSourceNameResolver, listeners);
    }

    @Bean
    @ConditionalOnMissingBean
    public DataSourceNameResolver dataSourceNameResolver(ApplicationContext applicationContext) {
        return new DataSourceNameResolver(applicationContext);
    }
}

