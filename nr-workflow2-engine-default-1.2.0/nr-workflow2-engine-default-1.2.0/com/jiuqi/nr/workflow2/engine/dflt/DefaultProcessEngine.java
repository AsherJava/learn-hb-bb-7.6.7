/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration
 */
package com.jiuqi.nr.workflow2.engine.dflt;

import com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.io.DefaultEngineProcessIOService;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.DefaultProcessRuntimeService;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessEngine
extends ProcessEngineRegisteration {
    public static final String NAME = "jiuqi.nr.default";
    public static final String TITLE = "\u9ed8\u8ba4\u6d41\u7a0b2.0";
    public static final short ORDER = 100;

    public DefaultProcessEngine(DefaultProcessDefinitionService definitionService, DefaultProcessRuntimeService runtimeService, DefaultEngineProcessIOService processIOService) {
        super(NAME, TITLE);
        this.order((short)100);
        this.processDefinitionService(definitionService);
        this.processRuntimeService(runtimeService);
        this.processIOService(processIOService);
    }
}

