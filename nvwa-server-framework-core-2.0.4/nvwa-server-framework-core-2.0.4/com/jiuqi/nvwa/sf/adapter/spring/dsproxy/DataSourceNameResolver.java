/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.zaxxer.hikari.HikariDataSource
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DataSourceDecorationStage;
import com.jiuqi.nvwa.sf.adapter.spring.dsproxy.DecoratedDataSource;
import com.zaxxer.hikari.HikariDataSource;
import java.util.HashMap;
import java.util.Map;
import javax.sql.CommonDataSource;
import javax.sql.DataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ClassUtils;

public class DataSourceNameResolver {
    private static final boolean HIKARI_AVAILABLE = ClassUtils.isPresent("com.zaxxer.hikari.HikariDataSource", DataSourceNameResolver.class.getClassLoader());
    private final ApplicationContext applicationContext;
    private final Map<CommonDataSource, String> cachedNames = new HashMap<CommonDataSource, String>();

    public DataSourceNameResolver(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String resolveDataSourceName(CommonDataSource dataSource) {
        String dataSourceName = this.cachedNames.get(dataSource);
        if (dataSourceName == null) {
            Map<CommonDataSource, String> map = this.cachedNames;
            synchronized (map) {
                HikariDataSource hikariDataSource;
                if (HIKARI_AVAILABLE && dataSource instanceof HikariDataSource && (hikariDataSource = (HikariDataSource)dataSource).getPoolName() != null && !hikariDataSource.getPoolName().startsWith("HikariPool-")) {
                    return hikariDataSource.getPoolName();
                }
                Map<String, DataSource> dataSources = this.applicationContext.getBeansOfType(DataSource.class);
                dataSourceName = dataSources.entrySet().stream().filter(entry -> {
                    DataSource candidate = (DataSource)entry.getValue();
                    if (candidate instanceof DecoratedDataSource) {
                        return this.matchesDataSource((DecoratedDataSource)((Object)((Object)candidate)), dataSource);
                    }
                    return candidate == dataSource;
                }).findFirst().map(Map.Entry::getKey).orElse("dataSource");
                this.cachedNames.put(dataSource, dataSourceName);
            }
        }
        return dataSourceName;
    }

    private boolean matchesDataSource(DecoratedDataSource decoratedCandidate, CommonDataSource dataSource) {
        return decoratedCandidate.getRealDataSource() == dataSource || decoratedCandidate.getDecoratingChain().stream().map(DataSourceDecorationStage::getDataSource).anyMatch(candidate -> candidate == dataSource);
    }
}

