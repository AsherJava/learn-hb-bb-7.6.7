/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.definition;

import com.jiuqi.nr.workflow2.engine.common.definition.ProcessDefinitionService;
import com.jiuqi.nr.workflow2.engine.dflt.process.definition.DefaultProcessDefinitionProvider;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessDefinitionService
extends ProcessDefinitionService {
    public DefaultProcessDefinitionService(DefaultProcessDefinitionProvider provider) {
        super(provider);
    }
}

