/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dynamic.temptable.factory.DynamicTempTableServiceFactory
 *  com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager
 *  com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableManageService
 *  com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService
 */
package com.jiuqi.nr.common.config;

import com.jiuqi.nr.dynamic.temptable.factory.DynamicTempTableServiceFactory;
import com.jiuqi.nr.dynamic.temptable.framework.connection.ConnectionManager;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableManageService;
import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamicTempTableConfiguration {
    @Value(value="${jiuqi.nr.dynamic-temp-table.poolusecache: false}")
    private boolean isPoolUseCache;
    @Value(value="${jiuqi.nr.dynamic-temp-table.check-interval: 30000}")
    private int checkInterval;
    @Value(value="${jiuqi.nr.dynamic-temp-table.leck-detection-threshold: 600000}")
    private int leakDetectionThreshold;

    @Bean
    @ConditionalOnMissingBean
    public IDynamicTempTableManageService getDynamicTempTableManageService(ConnectionManager connectionManager) {
        return DynamicTempTableServiceFactory.getDynamicTempTableManageService((boolean)this.isPoolUseCache, (int)this.checkInterval, (int)this.leakDetectionThreshold, (ConnectionManager)connectionManager);
    }

    @Bean
    @ConditionalOnMissingBean
    public IDynamicTempTableUseService getDynamicTempTableUseService(IDynamicTempTableManageService dynamicTempTableManageService, ConnectionManager connectionManager) {
        return DynamicTempTableServiceFactory.getDynamicTempTableUseService((IDynamicTempTableManageService)dynamicTempTableManageService, (ConnectionManager)connectionManager);
    }
}

