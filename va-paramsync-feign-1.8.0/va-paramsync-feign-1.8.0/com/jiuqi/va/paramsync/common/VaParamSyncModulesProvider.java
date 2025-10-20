/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.feign.util.FeignUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.paramsync.common;

import com.jiuqi.va.feign.util.FeignUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import com.jiuqi.va.paramsync.domain.VaParamSyncModuleServer;
import com.jiuqi.va.paramsync.domain.VaParamSyncModules;
import com.jiuqi.va.paramsync.feign.client.VaParamSyncFeignClient;
import java.util.List;

public class VaParamSyncModulesProvider {
    public static VaParamSyncModuleServer getModuleServer(String moduleName) {
        List<VaParamSyncModuleServer> modules = VaParamSyncModules.getModules();
        for (VaParamSyncModuleServer module : modules) {
            if (!module.getName().equalsIgnoreCase(moduleName)) continue;
            return module;
        }
        return VaParamSyncModules.getDefaults().get(moduleName.toLowerCase());
    }

    public static VaParamSyncFeignClient getClient(String moduleName) {
        VaParamSyncModuleServer modeulServer = VaParamSyncModulesProvider.getModuleServer(moduleName);
        if (modeulServer != null) {
            return VaParamSyncModulesProvider.getClient(modeulServer);
        }
        return null;
    }

    public static VaParamSyncFeignClient getClient(VaParamSyncModuleServer moduleServer) {
        VaParamSyncFeignClient feignClient = null;
        Object bean = ApplicationContextRegister.getBean((String)("VaParamSync#" + moduleServer.getName()));
        feignClient = bean != null ? (VaParamSyncFeignClient)bean : (VaParamSyncFeignClient)FeignUtil.getDynamicClient(VaParamSyncFeignClient.class, (String)moduleServer.getServer(), (String)moduleServer.getPath());
        return feignClient;
    }
}

