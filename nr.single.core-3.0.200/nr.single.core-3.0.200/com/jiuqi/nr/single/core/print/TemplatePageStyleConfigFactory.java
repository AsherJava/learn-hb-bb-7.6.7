/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.print;

import com.jiuqi.nr.single.core.print.DefaultTemplatePageStyleConfig;
import com.jiuqi.nr.single.core.print.ITemplatePageStyleConfig;
import java.util.HashMap;
import java.util.Map;

public class TemplatePageStyleConfigFactory {
    public static final String DEF_CONFIG_NAME = "_defaultPageStyleConfig";
    private static Map<String, ITemplatePageStyleConfig> configurations = new HashMap<String, ITemplatePageStyleConfig>();

    public static ITemplatePageStyleConfig getConfig(String name) {
        ITemplatePageStyleConfig config = null;
        config = configurations.containsKey(name) ? configurations.get(name) : TemplatePageStyleConfigFactory.getConfig();
        return config;
    }

    public static ITemplatePageStyleConfig getConfig() {
        return configurations.get(DEF_CONFIG_NAME);
    }

    public static void registerConfig(ITemplatePageStyleConfig config) {
        configurations.put(config.getName(), config);
    }

    static {
        configurations.put(DEF_CONFIG_NAME, new DefaultTemplatePageStyleConfig());
    }
}

