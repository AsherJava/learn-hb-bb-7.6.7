/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.domain.multimodule;

import com.jiuqi.va.bizmeta.domain.multimodule.ModuleServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConfigurationProperties(prefix="biz-module")
@Lazy(value=false)
public class Modules {
    private static List<ModuleServer> modules = new ArrayList<ModuleServer>();
    private static Map<String, ModuleServer> defaults = new HashMap<String, ModuleServer>();

    public static Map<String, ModuleServer> getDefaults() {
        return defaults;
    }

    public void setDefaults(Map<String, ModuleServer> defaults) {
        Modules.defaults = defaults;
    }

    public static List<ModuleServer> getModules() {
        if (!CollectionUtils.isEmpty(modules)) {
            return modules;
        }
        ArrayList<ModuleServer> tpmodules = new ArrayList<ModuleServer>();
        tpmodules.addAll(Modules.getDefaults().values());
        modules = tpmodules;
        return modules;
    }

    public void setModules(List<ModuleServer> modules) {
        Modules.modules = modules;
    }
}

