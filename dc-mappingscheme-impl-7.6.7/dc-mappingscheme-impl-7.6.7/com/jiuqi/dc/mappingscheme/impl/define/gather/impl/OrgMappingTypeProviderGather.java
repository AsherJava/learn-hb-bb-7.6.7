/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.dc.mappingscheme.impl.define.gather.impl;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.dc.mappingscheme.impl.define.IOrgMappingTypeProvider;
import com.jiuqi.dc.mappingscheme.impl.define.IPluginType;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IOrgMappingTypeProviderGather;
import com.jiuqi.dc.mappingscheme.impl.define.gather.IPluginTypeGather;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class OrgMappingTypeProviderGather
implements InitializingBean,
IOrgMappingTypeProviderGather {
    @Autowired(required=false)
    private List<IOrgMappingTypeProvider> providerList;
    @Autowired
    private IPluginTypeGather pluginTypeGather;
    private final Map<String, IOrgMappingTypeProvider> providerMap = new ConcurrentHashMap<String, IOrgMappingTypeProvider>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private void init() {
        if (CollectionUtils.isEmpty(this.providerList)) {
            this.providerMap.clear();
            return;
        }
        this.providerList.forEach(item -> {
            IPluginType pluginType = Optional.ofNullable(item.getPluginType()).orElse(this.pluginTypeGather.getPluginTypeByPackageName(item.getClass().getCanonicalName()));
            if (pluginType == null || StringUtils.isEmpty((String)pluginType.getSymbol())) {
                this.logger.warn("\u7ec4\u7ec7\u673a\u6784\u6620\u5c04\u7c7b\u578b\u63d2\u4ef6{}\u63d2\u4ef6\u7c7b\u578b\u4e3a\u7a7a\uff0c\u5df2\u8df3\u8fc7\u6ce8\u518c", (Object)item.getClass());
                return;
            }
            if (!this.providerMap.containsKey(pluginType.getSymbol()) || Objects.nonNull(item.getClass().getAnnotation(Primary.class))) {
                this.providerMap.put(pluginType.getSymbol(), (IOrgMappingTypeProvider)item);
            }
        });
    }

    @Override
    public IOrgMappingTypeProvider getProvider(IPluginType pluginType) {
        Assert.isNotNull((Object)pluginType);
        Assert.isNotEmpty((String)pluginType.getSymbol());
        return this.providerMap.get(pluginType.getSymbol());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init();
    }
}

