/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.nr.workflow2.engine.core.IProcessEngine;
import java.util.List;

public interface IProcessEngineFactory {
    public IProcessEngine getProcessEngine(String var1);

    public List<IProcessEngine> getAllProcessEngines();
}

