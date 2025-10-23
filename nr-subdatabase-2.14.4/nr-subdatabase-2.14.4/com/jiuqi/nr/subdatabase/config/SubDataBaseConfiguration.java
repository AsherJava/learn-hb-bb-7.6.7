/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.subdatabase.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SubDataBaseConfiguration {
    private int insertSubDataBaseThreadPoolSize;
    private boolean querySameTitleSubDBByCode;

    @Value(value="${jiuqi.nr.subdatabase.insertThreadPoolSize:4}")
    public void setInsertSubDataBaseThreadSize(int insertSubDataBaseThreadPoolSize) {
        this.insertSubDataBaseThreadPoolSize = insertSubDataBaseThreadPoolSize;
    }

    @Value(value="${jiuqi.nr.subdatabase.querySameTitleSDBByCode:false}")
    public boolean isQuerySameTitleSubDBByCode() {
        return this.querySameTitleSubDBByCode;
    }

    public int getInsertSubDataBaseThreadSize() {
        return this.insertSubDataBaseThreadPoolSize;
    }

    public void setQuerySameTitleSubDBByCode(boolean querySameTitleSubDBByCode) {
        this.querySameTitleSubDBByCode = querySameTitleSubDBByCode;
    }
}

