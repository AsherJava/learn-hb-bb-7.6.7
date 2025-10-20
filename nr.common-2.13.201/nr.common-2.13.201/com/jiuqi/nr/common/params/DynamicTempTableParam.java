/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService
 */
package com.jiuqi.nr.common.params;

import com.jiuqi.nr.dynamic.temptable.service.IDynamicTempTableUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DynamicTempTableParam {
    @Value(value="${jiuqi.nvwa.databaseLimitMode:false}")
    private String databaseLimitMode;
    @Value(value="${jiuqi.nr.dynamic-temp-table.retry-until-timeout: false}")
    private boolean retryUntilTimeout;
    @Value(value="${jiuqi.nr.dynamic-temp-table.retry-interval: 2000}")
    private int retryInterval;
    @Value(value="${jiuqi.nr.dynamic-temp-table.retry-timeout: 300000}")
    private int retryTimeout;
    @Autowired
    private IDynamicTempTableUseService dynamicTempTableUseService;

    public String getDatabaseLimitMode() {
        return this.databaseLimitMode;
    }

    public boolean isRetryUntilTimeout() {
        return this.retryUntilTimeout;
    }

    public int getRetryInterval() {
        return this.retryInterval;
    }

    public int getRetryTimeout() {
        return this.retryTimeout;
    }

    public IDynamicTempTableUseService getDynamicTempTableUseService() {
        return this.dynamicTempTableUseService;
    }
}

