/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.IO.ProcessIOService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService
 *  com.jiuqi.nr.workflow2.engine.core.IProcessIOService
 *  com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration
 *  com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionProvider
 *  com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService
 */
package com.jiuqi.nr.workflow2.converter.workflow.engine;

import com.jiuqi.nr.bpm.IO.ProcessIOService;
import com.jiuqi.nr.workflow2.converter.workflow.engine.runtime.Default_1_0_ProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.IProcessIOService;
import com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionProvider;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import org.springframework.stereotype.Component;

@Component
public class Default_1_0_ProcessEngine
extends ProcessEngineRegisteration {
    public static final String NAME = "jiuqi.nr.default-1.0";
    public static final String TITLE = "\u9ed8\u8ba4\u6d41\u7a0b1.0";
    public static final short ORDER = 99;

    public Default_1_0_ProcessEngine(Default_1_0_ProcessRuntimeService default_1_0_processRuntimeService) {
        super(NAME, TITLE);
        this.order((short)99);
        this.processDefinitionService((IProcessDefinitionService)new DefaultProcessDefinitionService(new DefaultProcessDefinitionProvider()));
        this.processRuntimeService(default_1_0_processRuntimeService);
        this.processIOService((IProcessIOService)new ProcessIOService());
    }
}

