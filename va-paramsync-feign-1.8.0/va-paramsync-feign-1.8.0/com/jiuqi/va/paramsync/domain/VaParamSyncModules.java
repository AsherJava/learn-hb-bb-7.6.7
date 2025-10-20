/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.paramsync.domain;

import com.jiuqi.va.paramsync.domain.VaParamSyncModuleServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
@ConfigurationProperties(prefix="paramsync")
public class VaParamSyncModules {
    private static Map<String, VaParamSyncModuleServer> defaults = new HashMap<String, VaParamSyncModuleServer>();
    private static List<VaParamSyncModuleServer> modules = new ArrayList<VaParamSyncModuleServer>();

    public static Map<String, VaParamSyncModuleServer> getDefaults() {
        return defaults;
    }

    public void setDefaults(Map<String, VaParamSyncModuleServer> defaults) {
        VaParamSyncModules.defaults = defaults;
    }

    public static List<VaParamSyncModuleServer> getModules() {
        return modules;
    }

    public void setModules(List<VaParamSyncModuleServer> modules) {
        VaParamSyncModules.modules = modules;
    }
}

