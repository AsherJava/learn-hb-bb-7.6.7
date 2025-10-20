/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime;

import com.jiuqi.bi.core.jobs.defaultlog.DefaultLogGenerator;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;

public class RealTimeLogManager {
    private ILogGenerator logGenerator = new DefaultLogGenerator();
    private static RealTimeLogManager instance = new RealTimeLogManager();

    private RealTimeLogManager() {
    }

    public static RealTimeLogManager getInstance() {
        return instance;
    }

    public ILogGenerator getLogGenerator() {
        return this.logGenerator;
    }
}

