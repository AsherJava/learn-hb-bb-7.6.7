/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule
 */
package com.jiuqi.va.workflow.config;

import com.jiuqi.va.domain.workflow.SubProcessBranchStrategyModule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConfigurationProperties(prefix="subprocess-branch-strategy-module")
public class SubProcessBranchStrategyModuleConfig {
    private Map<String, SubProcessBranchStrategyModule> defaults = new HashMap<String, SubProcessBranchStrategyModule>();
    private List<SubProcessBranchStrategyModule> modules = new ArrayList<SubProcessBranchStrategyModule>();

    public Map<String, SubProcessBranchStrategyModule> getDefaults() {
        return this.defaults;
    }

    public void setDefaults(Map<String, SubProcessBranchStrategyModule> defaults) {
        this.defaults = defaults;
    }

    public void setModules(List<SubProcessBranchStrategyModule> modules) {
        this.modules = modules;
    }

    public List<SubProcessBranchStrategyModule> getModules() {
        if (!CollectionUtils.isEmpty(this.modules)) {
            return this.modules;
        }
        this.modules = new ArrayList<SubProcessBranchStrategyModule>(this.defaults.values());
        return this.modules;
    }
}

