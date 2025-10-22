/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm;

import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import java.util.Optional;

public interface ProcessEngineProvider {
    @Deprecated
    public Optional<ProcessEngine> getProcessEngine(String var1);

    public Optional<ProcessEngine> getProcessEngine(ProcessType var1);

    @Deprecated
    public Optional<ProcessEngine> getProcessEngine();
}

