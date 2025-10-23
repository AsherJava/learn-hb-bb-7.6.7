/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.config;

import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.rwlock.DatabaseRWLockManager;
import com.jiuqi.nr.graph.rwlock.LocalRWLockManager;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLockHeartbeat;
import com.jiuqi.nr.graph.util.GraphUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NrGraphConfiguration {
    @Value(value="${spring.profiles.active:prod}")
    private String active;
    @Value(value="${jiuqi.np.cache.type:#{null}}")
    private String cacheType;
    @Value(value="${jiuqi.nr.difinition.lock-type:default}")
    private String lockType;

    @Bean
    public GraphUtils getGraphUtils() {
        return new GraphUtils("prod".equals(this.active));
    }

    @Bean
    public DatabaseLock getDatabaseLock() {
        return new DatabaseLock();
    }

    @Bean
    public DatabaseLockHeartbeat getDatabaseLockHeartbeat() {
        return new DatabaseLockHeartbeat();
    }

    @Bean
    public IRWLockExecuterManager getRWLockExecuterManager() {
        switch (this.lockType) {
            case "local": {
                return new LocalRWLockManager();
            }
            case "database": {
                return new DatabaseRWLockManager();
            }
        }
        if (!"redis".equalsIgnoreCase(this.cacheType) && !"rediscaffeine".equalsIgnoreCase(this.cacheType)) {
            return new LocalRWLockManager();
        }
        return new DatabaseRWLockManager();
    }
}

