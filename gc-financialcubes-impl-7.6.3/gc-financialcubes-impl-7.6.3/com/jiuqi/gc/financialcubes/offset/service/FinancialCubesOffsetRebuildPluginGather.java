/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.offset.service;

import com.jiuqi.gc.financialcubes.offset.service.FinancialCubesOffsetRebuildPlugin;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinancialCubesOffsetRebuildPluginGather
implements InitializingBean {
    private final Map<String, FinancialCubesOffsetRebuildPlugin> codeToPluginMap = new ConcurrentHashMap<String, FinancialCubesOffsetRebuildPlugin>();
    @Autowired(required=false)
    private List<FinancialCubesOffsetRebuildPlugin> pluginList;

    @Override
    public void afterPropertiesSet() {
        if (this.pluginList == null || this.pluginList.size() == 0) {
            return;
        }
        this.codeToPluginMap.clear();
        for (FinancialCubesOffsetRebuildPlugin plugin : this.pluginList) {
            this.codeToPluginMap.put(plugin.rebuildScope().getCode(), plugin);
        }
    }

    public FinancialCubesOffsetRebuildPlugin getPluginByScope(String rebuildScope) {
        return this.codeToPluginMap.get(rebuildScope);
    }
}

