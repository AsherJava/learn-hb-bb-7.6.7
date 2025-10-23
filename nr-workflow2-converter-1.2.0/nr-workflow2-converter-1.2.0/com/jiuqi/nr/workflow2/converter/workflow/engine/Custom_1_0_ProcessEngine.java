/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService
 *  com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration
 *  com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionProvider
 *  com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService
 */
package com.jiuqi.nr.workflow2.converter.workflow.engine;

import com.jiuqi.nr.workflow2.converter.workflow.engine.runtime.Custom_1_0_ProcessRuntimeService;
import com.jiuqi.nr.workflow2.engine.core.IProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.core.ProcessEngineRegisteration;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionProvider;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionService;
import org.springframework.stereotype.Component;

@Component
public class Custom_1_0_ProcessEngine
extends ProcessEngineRegisteration {
    public static final String NAME = "jiuqi.nr.customprocessengine";
    public static final String TITLE = "\u81ea\u5b9a\u4e49\u6d41\u7a0b";
    public static final short ORDER = 102;

    public Custom_1_0_ProcessEngine(Custom_1_0_ProcessRuntimeService custom_1_0_processRuntimeService) {
        super(NAME, TITLE);
        this.order((short)102);
        this.processDefinitionService((IProcessDefinitionService)new DefaultProcessDefinitionService(new DefaultProcessDefinitionProvider()));
        this.processRuntimeService(custom_1_0_processRuntimeService);
    }
}

