/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core;

import com.jiuqi.nr.workflow2.engine.core.ILanguagePacket;
import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.IProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.IProcessStatsService;

public interface IProcessEngine {
    public String getName();

    public String getTitle();

    public IProcessDefinitionService getProcessDefinitionService();

    public IProcessRuntimeService getProcessRuntimeService();

    public IProcessStatsService getProcessStatsService();

    public ILanguagePacket getLanguagePacket();

    public IProcessIOService getProcessIOService();
}

