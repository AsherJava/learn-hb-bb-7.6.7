/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.StrategyModule
 */
package com.jiuqi.va.workflow.config;

import com.jiuqi.va.domain.workflow.StrategyModule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConfigurationProperties(prefix="strategy-module")
public class StrategyModuleConfig {
    private List<StrategyModule> modules;
    private Map<String, StrategyModule> defaults = new HashMap<String, StrategyModule>();

    public Map<String, StrategyModule> getDefaults() {
        return this.defaults;
    }

    public void setDefaults(Map<String, StrategyModule> defaults) {
        this.defaults = defaults;
    }

    public List<StrategyModule> getModules() {
        if (!CollectionUtils.isEmpty(this.modules)) {
            return this.modules;
        }
        if (!CollectionUtils.isEmpty(this.defaults)) {
            this.modules = new ArrayList<StrategyModule>(this.defaults.values());
        }
        return this.modules;
    }

    public void setModules(List<StrategyModule> modules) {
        this.modules = modules;
    }
}

