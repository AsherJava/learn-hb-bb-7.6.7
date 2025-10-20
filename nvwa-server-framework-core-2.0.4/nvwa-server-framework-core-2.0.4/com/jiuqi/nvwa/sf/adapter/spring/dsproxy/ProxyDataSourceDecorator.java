/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceDecorator;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceNameResolver;
import java.util.List;
import javax.sql.DataSource;
import net.ttddyy.dsproxy.listener.QueryExecutionListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.core.Ordered;

public class ProxyDataSourceDecorator
implements DataSourceDecorator,
Ordered {
    private final DataSourceNameResolver dataSourceNameResolver;
    private final List<QueryExecutionListener> listeners;

    public ProxyDataSourceDecorator(DataSourceNameResolver dataSourceNameResolver, List<QueryExecutionListener> listeners) {
        this.dataSourceNameResolver = dataSourceNameResolver;
        this.listeners = listeners;
    }

    @Override
    public DataSource decorate(DataSource dataSource) {
        ProxyDataSourceBuilder proxyDataSourceBuilder = ProxyDataSourceBuilder.create();
        String dataSourceName = this.dataSourceNameResolver.resolveDataSourceName(dataSource);
        this.listeners.forEach(proxyDataSourceBuilder::listener);
        return proxyDataSourceBuilder.dataSource(dataSource).name(dataSourceName).build();
    }

    @Override
    public int getOrder() {
        return 20;
    }
}

