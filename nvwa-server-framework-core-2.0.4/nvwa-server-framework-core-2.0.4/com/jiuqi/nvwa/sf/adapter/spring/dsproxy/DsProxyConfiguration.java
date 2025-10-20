/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.dsproxy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DsProxyConfiguration {
    @Value(value="${jiuqi.nvwa.sqlmonitor.enabled:true}")
    private boolean enabled = true;
    @Value(value="${jiuqi.nvwa.sqlmonitor.beforeQuery:true}")
    private boolean beforeQuery = true;
    @Value(value="${jiuqi.nvwa.sqlmonitor.afterQuery:true}")
    private boolean afterQuery = true;

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isBeforeQuery() {
        return this.beforeQuery;
    }

    public void setBeforeQuery(boolean beforeQuery) {
        this.beforeQuery = beforeQuery;
    }

    public boolean isAfterQuery() {
        return this.afterQuery;
    }

    public void setAfterQuery(boolean afterQuery) {
        this.afterQuery = afterQuery;
    }
}

