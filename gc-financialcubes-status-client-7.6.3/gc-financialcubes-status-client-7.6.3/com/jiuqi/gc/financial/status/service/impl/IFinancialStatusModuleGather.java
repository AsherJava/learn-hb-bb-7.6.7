/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 */
package com.jiuqi.gc.financial.status.service.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gc.financial.status.intf.IFinancialStatusModulePlugin;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IFinancialStatusModuleGather
implements InitializingBean {
    private final Map<String, IFinancialStatusModulePlugin> codeToModulePluginMap = new ConcurrentHashMap<String, IFinancialStatusModulePlugin>();
    @Autowired(required=false)
    private List<IFinancialStatusModulePlugin> modulePluginList;

    @Override
    public void afterPropertiesSet() {
        if (CollectionUtils.isEmpty(this.modulePluginList)) {
            return;
        }
        this.codeToModulePluginMap.clear();
        for (IFinancialStatusModulePlugin plugin : this.modulePluginList) {
            this.codeToModulePluginMap.put(plugin.getModuleCode(), plugin);
        }
    }

    public IFinancialStatusModulePlugin getPluginByModuleCode(String moduleCode) {
        return this.codeToModulePluginMap.get(moduleCode);
    }

    public List<IFinancialStatusModulePlugin> list() {
        return this.modulePluginList;
    }
}

