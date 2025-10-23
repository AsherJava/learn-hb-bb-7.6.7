/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.nr.graph.rwlock.executer;

import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class DatabaseLockHeartbeat
implements ModuleInitiator {
    public static final long DATABASE_LOCK_HEARTBEAT_CYCLE = 30000L;
    public static final long DATABASE_LOCK_TIMEOUT = 60000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseLockHeartbeat.class);
    @Autowired
    private DatabaseLock databaseLock;

    @Scheduled(fixedDelay=30000L)
    public void heartbeat() {
        try {
            LOGGER.debug("DB Lock \u5fc3\u8df3");
            this.databaseLock.heartbeat();
        }
        catch (Exception e) {
            LOGGER.error("DB Lock \u5fc3\u8df3\u5f02\u5e38", e);
        }
    }

    private void monitor() {
        try {
            LOGGER.debug("DB Lock \u5fc3\u8df3\u76d1\u63a7");
            this.databaseLock.deadlockReset(60000L);
        }
        catch (Exception e) {
            LOGGER.error("DB Lock \u5fc3\u8df3\u76d1\u63a7\u5f02\u5e38", e);
        }
    }

    public void init(ServletContext context) throws Exception {
        new Thread(() -> {
            try {
                Thread.sleep(60000L);
                this.monitor();
            }
            catch (InterruptedException e) {
                LOGGER.error("DB Lock \u5fc3\u8df3\u76d1\u63a7\u5f02\u5e38", e);
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void initWhenStarted(ServletContext context) throws Exception {
    }
}

