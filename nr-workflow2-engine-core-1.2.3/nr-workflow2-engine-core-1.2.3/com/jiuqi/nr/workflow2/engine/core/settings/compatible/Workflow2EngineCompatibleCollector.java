/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.nr.workflow2.engine.core.settings.compatible;

import com.jiuqi.nr.workflow2.engine.core.exception.CompatibleExtendNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

public class Workflow2EngineCompatibleCollector {
    @Autowired
    private List<Workflow2EngineCompatibleExtend> extensions;
    private Map<String, Workflow2EngineCompatibleExtend> extensionMap;

    @PostConstruct
    public void buildExtensionMap() {
        this.extensionMap = this.extensions.stream().collect(Collectors.toMap(Workflow2EngineCompatibleExtend::getWorkflowEngine, Function.identity(), (v1, v2) -> v1));
    }

    public Workflow2EngineCompatibleExtend getExtensionByEngine(String workflowEngine) {
        Workflow2EngineCompatibleExtend engineCompatibleExtend = this.extensionMap.get(workflowEngine);
        if (engineCompatibleExtend == null) {
            throw new CompatibleExtendNotFoundException(workflowEngine);
        }
        return engineCompatibleExtend;
    }
}

