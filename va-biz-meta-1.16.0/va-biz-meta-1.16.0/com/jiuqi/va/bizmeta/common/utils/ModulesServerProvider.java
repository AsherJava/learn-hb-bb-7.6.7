/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.common.utils;

import com.jiuqi.va.bizmeta.domain.multimodule.ModuleProxy;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleProxyServer;
import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import com.jiuqi.va.bizmeta.domain.multimodule.Modules;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class ModulesServerProvider {
    public static ModuleServer getModuleServer(String moduleName, String metaType) {
        if ("BILLLIST".equals(metaType.toUpperCase())) {
            metaType = "billList";
        }
        ModuleServer returnModuleServer = new ModuleServer();
        for (ModuleServer moduleServer : Modules.getModules()) {
            if (!moduleServer.getName().equals(moduleName)) continue;
            returnModuleServer.setName(moduleServer.getName());
            returnModuleServer.setTitle(moduleServer.getTitle());
            returnModuleServer.setServer(moduleServer.getServer());
            returnModuleServer.setPath(moduleServer.getPath());
            if (StringUtils.hasText(moduleServer.getPath())) {
                returnModuleServer.setRealPath(String.format("%s/%s", moduleServer.getPath(), metaType));
                continue;
            }
            returnModuleServer.setRealPath(String.format("%s", metaType));
        }
        Map<String, List<ModuleProxyServer>> proxyMapping = ModuleProxy.getProxyMapping();
        if (proxyMapping != null) {
            for (String moduleMetaType : proxyMapping.keySet()) {
                List<ModuleProxyServer> moduleProxyServer = proxyMapping.get(moduleMetaType);
                for (ModuleProxyServer proxyServer : moduleProxyServer) {
                    List<String> proxyModules = Arrays.asList(proxyServer.getModules());
                    if (!moduleMetaType.equals(metaType) || !proxyModules.contains("*") && !proxyModules.contains(moduleName)) continue;
                    returnModuleServer.setServer(proxyServer.getServer());
                    returnModuleServer.setPath(proxyServer.getPath());
                    if (StringUtils.hasText(proxyServer.getPath())) {
                        returnModuleServer.setRealPath(String.format("%s/%s", proxyServer.getPath(), metaType));
                        continue;
                    }
                    returnModuleServer.setRealPath(String.format("%s", metaType));
                }
            }
        }
        return returnModuleServer;
    }
}

