/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime;

import com.jiuqi.bi.core.jobs.realtime.IRealTimePostProcessor;
import java.util.ArrayList;
import java.util.List;

public class RealTimePostProcessManager {
    private static RealTimePostProcessManager instance = new RealTimePostProcessManager();
    private List<IRealTimePostProcessor> processors = new ArrayList<IRealTimePostProcessor>();

    private RealTimePostProcessManager() {
    }

    public static RealTimePostProcessManager getInstance() {
        return instance;
    }

    public void registerProcessor(IRealTimePostProcessor postProcessor) {
        this.processors.add(postProcessor);
    }

    public List<IRealTimePostProcessor> getProcessors() {
        return this.processors;
    }
}

