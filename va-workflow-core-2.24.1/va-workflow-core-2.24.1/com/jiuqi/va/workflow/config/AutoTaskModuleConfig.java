/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.AutoTaskModule
 */
package com.jiuqi.va.workflow.config;

import com.jiuqi.va.domain.workflow.AutoTaskModule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConfigurationProperties(prefix="autotask-module")
public class AutoTaskModuleConfig {
    private Map<String, AutoTaskModule> defaults = new HashMap<String, AutoTaskModule>();
    private List<AutoTaskModule> modules = new ArrayList<AutoTaskModule>();

    public Map<String, AutoTaskModule> getDefaults() {
        return this.defaults;
    }

    public void setDefaults(Map<String, AutoTaskModule> defaults) {
        this.defaults = defaults;
    }

    public void setModules(List<AutoTaskModule> modules) {
        this.modules = modules;
    }

    public List<AutoTaskModule> getModules() {
        if (!CollectionUtils.isEmpty(this.modules)) {
            return this.modules;
        }
        this.modules = new ArrayList<AutoTaskModule>(this.defaults.values());
        return this.modules;
    }
}

