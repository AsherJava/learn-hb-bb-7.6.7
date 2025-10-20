/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.multimodule;

import com.jiuqi.va.bizmeta.domain.multimodule.ModuleProxyServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConfigurationProperties(prefix="proxy")
@Lazy(value=false)
public class ModuleProxy {
    private static Map<String, List<ModuleProxyServer>> proxyMapping = new HashMap<String, List<ModuleProxyServer>>();
    private static Map<String, ModuleProxyServer> defaults = new HashMap<String, ModuleProxyServer>();

    public static Map<String, ModuleProxyServer> getDefaults() {
        return defaults;
    }

    public void setDefaults(Map<String, ModuleProxyServer> defaults) {
        ModuleProxy.defaults = defaults;
    }

    public static Map<String, List<ModuleProxyServer>> getProxyMapping() {
        if (!CollectionUtils.isEmpty(proxyMapping)) {
            return proxyMapping;
        }
        ArrayList<ModuleProxyServer> tpmodules = new ArrayList<ModuleProxyServer>();
        tpmodules.addAll(ModuleProxy.getDefaults().values());
        HashMap<String, List<ModuleProxyServer>> proxyMap = new HashMap<String, List<ModuleProxyServer>>();
        for (String moduleType : ModuleProxy.getDefaults().keySet()) {
            proxyMap.put(moduleType, tpmodules);
        }
        proxyMapping = proxyMap;
        return proxyMapping;
    }

    public void setProxyMapping(Map<String, List<ModuleProxyServer>> proxyMapping) {
        ModuleProxy.proxyMapping = proxyMapping;
    }
}

