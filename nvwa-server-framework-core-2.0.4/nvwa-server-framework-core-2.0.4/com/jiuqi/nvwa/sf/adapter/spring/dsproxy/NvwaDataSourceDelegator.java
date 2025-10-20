/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceDecorationStage;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceDecorator;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DecoratedDataSource;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DsProxyConfiguration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

@Component
public class NvwaDataSourceDelegator {
    @Autowired
    private DsProxyConfiguration configuration;
    @Autowired
    private ApplicationContext applicationContext;

    public DataSource doDelegate(DataSource dataSource) {
        if (!this.configuration.isEnabled()) {
            return dataSource;
        }
        DataSource decoratedDataSource = dataSource;
        LinkedHashMap decorators = new LinkedHashMap();
        this.applicationContext.getBeansOfType(DataSourceDecorator.class).entrySet().stream().sorted(Map.Entry.comparingByValue(AnnotationAwareOrderComparator.INSTANCE)).forEach(entry -> {
            DataSourceDecorator cfr_ignored_0 = (DataSourceDecorator)decorators.put(entry.getKey(), entry.getValue());
        });
        ArrayList<DataSourceDecorationStage> decoratedDataSourceChainEntries = new ArrayList<DataSourceDecorationStage>();
        for (Map.Entry decoratorEntry : decorators.entrySet()) {
            String decoratorBeanName = (String)decoratorEntry.getKey();
            DataSource dataSourceBeforeDecorating = decoratedDataSource;
            DataSourceDecorator decorator = (DataSourceDecorator)decoratorEntry.getValue();
            if (dataSourceBeforeDecorating == (decoratedDataSource = Objects.requireNonNull(decorator.decorate(decoratedDataSource), "DataSourceDecorator (" + decoratorBeanName + ", " + decorator + ") should not return null"))) continue;
            decoratedDataSourceChainEntries.add(0, new DataSourceDecorationStage(decoratorBeanName, decorator, decoratedDataSource));
        }
        if (dataSource != decoratedDataSource) {
            return new DecoratedDataSource(dataSource, decoratedDataSource, decoratedDataSourceChainEntries);
        }
        return dataSource;
    }
}

