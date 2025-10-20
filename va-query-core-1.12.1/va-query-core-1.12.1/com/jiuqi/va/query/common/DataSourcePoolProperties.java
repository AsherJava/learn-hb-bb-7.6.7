/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value={"classpath:/config/DefinedQueryDataSource.properties"}, ignoreResourceNotFound=true)
public class DataSourcePoolProperties {
    @Value(value="${max.poolsize:50}")
    private int maxPoolSize;
    @Value(value="${min.idle:30}")
    private int minIdle;
    @Value(value="${max.lifetime:120000}")
    private long maxLifeTime;
    @Value(value="${idle.timeout:60000}")
    private long idleTimeout;
    @Value(value="${connection.timeout:30000}")
    private long connectionTimeout;

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getMinIdle() {
        return this.minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public long getMaxLifeTime() {
        return this.maxLifeTime;
    }

    public void setMaxLifeTime(long maxLifeTime) {
        this.maxLifeTime = maxLifeTime;
    }

    public long getIdleTimeout() {
        return this.idleTimeout;
    }

    public void setIdleTimeout(long idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}

