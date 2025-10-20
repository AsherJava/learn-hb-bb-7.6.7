/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.local;

import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.jobs.realtime.local.LocalScheduler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LocalSchedulerManager {
    private Map<Integer, LocalScheduler> schedulerMap = new HashMap<Integer, LocalScheduler>();
    private static LocalSchedulerManager manager = new LocalSchedulerManager();

    public static LocalSchedulerManager getInstance() {
        return manager;
    }

    public LocalScheduler getScheduler() {
        return this.getScheduler(0);
    }

    public LocalScheduler getSubScheduler() {
        return this.getScheduler(1);
    }

    public LocalScheduler getScheduler(int level) {
        int threadNumber = ConfigManager.getInstance().getMaxJobExecuteCount();
        LocalScheduler localScheduler = this.schedulerMap.computeIfAbsent(level, integer -> new LocalScheduler(level, threadNumber));
        localScheduler.setThreadNumber(threadNumber);
        return localScheduler;
    }

    public void restartAll() {
        ArrayList<Integer> schedulerLevels = new ArrayList<Integer>();
        for (Map.Entry<Integer, LocalScheduler> entry : this.schedulerMap.entrySet()) {
            schedulerLevels.add(entry.getKey());
            entry.getValue().shutdown();
        }
        this.schedulerMap = new HashMap<Integer, LocalScheduler>();
        for (Integer schedulerLevel : schedulerLevels) {
            this.getScheduler(schedulerLevel);
        }
    }
}

