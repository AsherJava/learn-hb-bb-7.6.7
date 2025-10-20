/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.queue;

import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorSystemOptionValueManager;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorPostServerRetryManager {
    private static final MonitorPostServerRetryManager instance = new MonitorPostServerRetryManager();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AtomicInteger retryCount = new AtomicInteger(0);
    private final AtomicInteger serverLevel = new AtomicInteger(0);
    private final AtomicLong nextRetryTime = new AtomicLong(0L);
    private static final long basicRetryInterval = 300L;
    private volatile MonitorSystemOptionValueManager properties;

    public static MonitorPostServerRetryManager getInstance() {
        return instance;
    }

    public boolean isServerAvailable() {
        return this.serverLevel.get() == 0 || System.currentTimeMillis() > this.nextRetryTime.get();
    }

    public void sendSuccess() {
        this.retryCount.set(0);
        this.serverLevel.set(0);
        this.nextRetryTime.set(0L);
    }

    public void sendFailed() {
        int currentRetry = this.retryCount.get();
        if (currentRetry < this.getConfig().getRetryCount()) {
            this.retryCount.incrementAndGet();
        } else {
            long newTime = System.currentTimeMillis() + 300L * (1L << currentRetry);
            this.nextRetryTime.set(newTime);
            this.serverLevel.incrementAndGet();
        }
        this.logger.error("SkyWalking\u53d1\u9001\u5931\u8d25\uff0c\u5f53\u524d\u91cd\u8bd5\u6b21\u6570\uff1a" + currentRetry + "\uff0c\u4e0b\u6b21\u53d1\u9001\u65f6\u95f4\uff1a" + this.nextRetryTime.get());
    }

    public boolean canSend() {
        int level = this.serverLevel.get();
        if (level == 0) {
            return true;
        }
        return System.currentTimeMillis() > this.nextRetryTime.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private MonitorSystemOptionValueManager getConfig() {
        if (this.properties == null) {
            MonitorPostServerRetryManager monitorPostServerRetryManager = this;
            synchronized (monitorPostServerRetryManager) {
                if (this.properties == null) {
                    this.properties = (MonitorSystemOptionValueManager)SpringBeanUtils.getBean(MonitorSystemOptionValueManager.class);
                }
            }
        }
        return this.properties;
    }
}

