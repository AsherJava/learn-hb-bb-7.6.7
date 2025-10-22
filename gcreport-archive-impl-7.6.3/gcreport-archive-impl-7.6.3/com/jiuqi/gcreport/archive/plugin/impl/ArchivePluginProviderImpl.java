/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.archive.plugin.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.archive.plugin.ArchivePlugin;
import com.jiuqi.gcreport.archive.plugin.ArchivePluginProvider;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy(value=false)
@Component
public class ArchivePluginProviderImpl
implements InitializingBean,
ArchivePluginProvider {
    @Autowired
    private List<ArchivePlugin> pluginList;
    private Map<String, ArchivePlugin> pluginCache = new ConcurrentHashMap<String, ArchivePlugin>(16);

    @Override
    public void afterPropertiesSet() {
        this.init();
    }

    private void init() {
        if (CollectionUtils.isEmpty(this.pluginList)) {
            this.pluginCache.clear();
            return;
        }
        this.pluginList.forEach(item -> {
            if (StringUtils.isEmpty((String)item.getPluginCode())) {
                return;
            }
            if (!this.pluginCache.containsKey(item.getPluginCode())) {
                this.pluginCache.put(item.getPluginCode(), (ArchivePlugin)item);
            }
        });
    }

    @Override
    public ArchivePlugin getArchivePluginByCode(String pluginCode) {
        return this.pluginCache.get(pluginCode);
    }

    @Override
    public List<ArchivePlugin> listArchivePlugin() {
        return this.pluginList;
    }
}

